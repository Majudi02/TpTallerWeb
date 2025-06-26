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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public void quePuedaCrearPedido() {
        Cliente cliente = new Cliente();
        sessionFactory.getCurrentSession().save(cliente);

        Pedido pedido = new Pedido();
        pedido.setUsuario(cliente);
        pedido.setFinalizo(false);
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
        pedido.setFecha(String.valueOf(LocalDateTime.now()));

        repositorioPedido.crearPedido(pedido);

        Pedido pedidoGuardado = sessionFactory.getCurrentSession().get(Pedido.class, pedido.getId());
        assertNotNull(pedidoGuardado);
        assertEquals(cliente.getId(), pedidoGuardado.getUsuario().getId());
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
    public void queAgregarUnPlatoAlPedidoCreaPedidoSiNoHayUnoActivo() {
        Cliente usuario = new Cliente();
        sessionFactory.getCurrentSession().save(usuario);

        Plato plato = new Plato();
        plato.setPrecio(150.0);
        sessionFactory.getCurrentSession().save(plato);

        repositorioPedido.agregarPlatoAlPedido(plato, usuario.getId());

        Pedido pedido = repositorioPedido.buscarPedidoActivoPorUsuario(usuario.getId());
        assertNotNull(pedido);
        assertFalse(pedido.isFinalizo());
        assertEquals(1, pedido.getPedidoPlatos().size());
        assertEquals(plato.getPrecio(), pedido.getPrecio());
    }

    @Test
    @Rollback
    public void queMostrarPlatosDelPedidoActualDevuelveTodosLosPlatos() {
        Cliente cliente = new Cliente();
        sessionFactory.getCurrentSession().save(cliente);

        Plato plato = new Plato();
        sessionFactory.getCurrentSession().save(plato);

        Pedido pedido = new Pedido();
        pedido.setUsuario(cliente);
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
        pedido.setFinalizo(false);
        pedido.setPedidoPlatos(new ArrayList<>());
        sessionFactory.getCurrentSession().save(pedido);

        PedidoPlato pedidoPlatoPendiente = new PedidoPlato();
        pedidoPlatoPendiente.setPedido(pedido);
        pedidoPlatoPendiente.setPlato(plato);
        pedidoPlatoPendiente.setEstadoPlato(EstadoPlato.PENDIENTE);
        sessionFactory.getCurrentSession().save(pedidoPlatoPendiente);

        PedidoPlato pedidoPlatoFinalizado = new PedidoPlato();
        pedidoPlatoFinalizado.setPedido(pedido);
        pedidoPlatoFinalizado.setPlato(plato);
        pedidoPlatoFinalizado.setEstadoPlato(EstadoPlato.FINALIZADO);
        sessionFactory.getCurrentSession().save(pedidoPlatoFinalizado);

        pedido.getPedidoPlatos().add(pedidoPlatoPendiente);
        pedido.getPedidoPlatos().add(pedidoPlatoFinalizado);
        sessionFactory.getCurrentSession().saveOrUpdate(pedido);

        List<PedidoPlato> platos = repositorioPedido.mostrarPlatosDelPedidoActual(cliente.getId());

        assertEquals(2, platos.size());

        boolean tienePendiente = false;
        boolean tieneFinalizado = false;

        for (PedidoPlato pp : platos) {
            if (pp.getEstadoPlato() == EstadoPlato.PENDIENTE) {
                tienePendiente = true;
            }
            if (pp.getEstadoPlato() == EstadoPlato.FINALIZADO) {
                tieneFinalizado = true;
            }
        }

        assertTrue(tienePendiente, "Debe contener al menos un plato pendiente");
        assertTrue(tieneFinalizado, "Debe contener al menos un plato finalizado");
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

        repositorioPedido.confirmarPedido(usuario.getId());

        Pedido pedidoActualizado = sessionFactory.getCurrentSession().get(Pedido.class, pedido.getId());

        assertEquals(EstadoPedido.EN_PROCESO, pedidoActualizado.getEstadoPedido());
        assertFalse(pedidoActualizado.isFinalizo());
    }

    @Test
    @Rollback
    public void queSePuedaObtenerUnPedidoPorSuId() {
        Cliente usuario = new Cliente();
        sessionFactory.getCurrentSession().save(usuario);

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setPedidoPlatos(new ArrayList<>());
        pedido.setFinalizo(false);
        pedido.setPrecio(100.0);
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
        sessionFactory.getCurrentSession().save(pedido);

        Pedido pedidoBuscado = repositorioPedido.buscarPorId(pedido.getId());

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


