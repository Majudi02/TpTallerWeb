package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
public class RepositorioPedidoImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioPedidoImpl repositorioPedido;


    @BeforeEach
    public void setUp() {
        repositorioPedido = new RepositorioPedidoImpl(sessionFactory);
    }


    @Test
    @Rollback
    public void quePuedaDevolverUnPedidoCuandoElUsuarioLoTieneActivo() {
        Cliente usuario = new Cliente();
        sessionFactory.getCurrentSession().save(usuario);

        Pedido pedido = new Pedido();
        pedido.setFinalizo(false);
        pedido.setUsuario(usuario);
        sessionFactory.getCurrentSession().save(pedido);

        Pedido pedidoActivoEncontrado = repositorioPedido.buscarPedidoActivoPorUsuario(usuario.getId());

        assertNotNull(pedidoActivoEncontrado);
        assertEquals(usuario.getId(), pedidoActivoEncontrado.getUsuario().getId());
    }

    @Test
    @Rollback
    public void queSePuedaGuardarUnPlatoEnUnPedidoExistente() {

        Cliente usuario = new Cliente();
        sessionFactory.getCurrentSession().save(usuario);

        Plato plato = new Plato();
        plato.setId(1);
        plato.setNombre("Milanesa");
        plato.setPrecio(100.0);
        sessionFactory.getCurrentSession().save(plato);


        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setPedidoPlatos(new ArrayList<>());
        pedido.setFinalizo(false);
        pedido.setPrecio(0.0);
        sessionFactory.getCurrentSession().save(pedido);

        repositorioPedido.agregarPlatoAlPedido(plato, usuario.getId());

        Pedido pedidoActualizado = sessionFactory.getCurrentSession().get(Pedido.class, pedido.getId());


        assertEquals(1, pedidoActualizado.getPedidoPlatos().size());

    }

    @Test
    @Rollback
    public void queSePuedaFinalizarUnPedido() {
        Cliente usuario = new Cliente();
        sessionFactory.getCurrentSession().save(usuario);

        Plato plato = new Plato();
        plato.setNombre("Milanesa");
        plato.setPrecio(100.0);
        sessionFactory.getCurrentSession().save(plato);

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setPedidoPlatos(new ArrayList<>());
        pedido.setFinalizo(false);
        pedido.setPrecio(100.0);
        sessionFactory.getCurrentSession().save(pedido);

        PedidoPlato pedidoPlato = new PedidoPlato();
        pedidoPlato.setPedido(pedido);
        pedidoPlato.setPlato(plato);
        pedidoPlato.setEstadoPlato(EstadoPlato.FINALIZADO);
        sessionFactory.getCurrentSession().save(pedidoPlato);


        pedido.getPedidoPlatos().add(pedidoPlato);
        sessionFactory.getCurrentSession().saveOrUpdate(pedido);

        repositorioPedido.finalizarPedido(usuario.getId());

        Pedido pedidoActualizado = sessionFactory.getCurrentSession().get(Pedido.class, pedido.getId());

        assertEquals(EstadoPedido.FINALIZADO, pedidoActualizado.getEstadoPedido());
    }

    @Test
    @Rollback
    public void queSePuedaEntregarUnPedido() {
        Cliente usuario = new Cliente();
        sessionFactory.getCurrentSession().save(usuario);

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setPedidoPlatos(new ArrayList<>());
        pedido.setFinalizo(false);
        pedido.setPrecio(100.0);
        pedido.setEstadoPedido(EstadoPedido.FINALIZADO); // ya fue finalizado por cocina
        sessionFactory.getCurrentSession().save(pedido);

        repositorioPedido.entregarPedido(pedido.getId());

        Pedido pedidoEntregado = sessionFactory.getCurrentSession().get(Pedido.class, pedido.getId());

        assertTrue(pedidoEntregado.isFinalizo()); // se marcó como entregado
        assertEquals(EstadoPedido.FINALIZADO, pedidoEntregado.getEstadoPedido()); // sigue siendo finalizado
    }


    @Test
    @Rollback
    public void queSePuedaObtenerUnPedidoPorSuId(){
        Cliente usuario = new Cliente();
        sessionFactory.getCurrentSession().save(usuario);

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setPedidoPlatos(new ArrayList<>());
        pedido.setFinalizo(false);
        pedido.setPrecio(100.0);
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
        sessionFactory.getCurrentSession().save(pedido);

        Pedido pedidoBuscado=repositorioPedido.buscarPorId(pedido.getId());

        assertEquals(usuario.getId(), pedidoBuscado.getUsuario().getId());

    }

    @Test
    @Rollback
    public void queSeListanPedidosDelUsuarioOrdenadosPorFecha() {
        Cliente usuario = new Cliente();
        sessionFactory.getCurrentSession().save(usuario);

        Pedido pedido1 = new Pedido();
        pedido1.setUsuario(usuario);
        pedido1.setFecha("2025-06-01");
        sessionFactory.getCurrentSession().save(pedido1);

        Pedido pedido2 = new Pedido();
        pedido2.setUsuario(usuario);
        pedido2.setFecha("2025-06-10");
        sessionFactory.getCurrentSession().save(pedido2);

        var pedidos = repositorioPedido.listarPedidosPorUsuario(usuario.getId());

        assertEquals(2, pedidos.size());
        assertTrue(pedidos.get(0).getFecha().compareTo(pedidos.get(1).getFecha()) > 0); // orden descendente
    }

}


