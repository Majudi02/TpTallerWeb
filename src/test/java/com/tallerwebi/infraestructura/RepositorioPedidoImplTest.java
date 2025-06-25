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

        assertTrue(pedidoActualizado.isFinalizo());
    }
}


