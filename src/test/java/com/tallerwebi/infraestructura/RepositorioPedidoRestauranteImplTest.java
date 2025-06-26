package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
public class RepositorioPedidoRestauranteImplTest {


    @Autowired
    private SessionFactory sessionFactory;
    private RepostitorioPedidoRestauranteImpl repositorio;

    @BeforeEach
    public void setUp() {
        repositorio = new RepostitorioPedidoRestauranteImpl(sessionFactory);
    }


    @Test
    @Rollback
    public void queSePuedanTraerTodosLosPedidosConPlatosYRestauranteRelacionados() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("Restaurante Test");
        sessionFactory.getCurrentSession().save(restaurante);

        Plato plato = new Plato();
        plato.setNombre("Plato Test");
        plato.setRestaurante(restaurante);
        sessionFactory.getCurrentSession().save(plato);

        Pedido pedido1 = new Pedido();
        pedido1.setEstadoPedido(EstadoPedido.EN_PROCESO);
        sessionFactory.getCurrentSession().save(pedido1);

        PedidoPlato pp1 = new PedidoPlato();
        pp1.setPedido(pedido1);
        pp1.setPlato(plato);
        sessionFactory.getCurrentSession().save(pp1);

        Pedido pedido2 = new Pedido();
        pedido2.setEstadoPedido(EstadoPedido.EN_PROCESO);
        sessionFactory.getCurrentSession().save(pedido2);

        PedidoPlato pp2 = new PedidoPlato();
        pp2.setPedido(pedido2);
        pp2.setPlato(plato);
        sessionFactory.getCurrentSession().save(pp2);

        List<Pedido> pedidosTotales = this.repositorio.traerTodosLosPedidos();

        assertNotNull(pedidosTotales);
        assertEquals(2, pedidosTotales.size());
    }


    @Test
    @Rollback
    public void queSePuedaObtenerElIdDelRestauranteConElIdDelUsuarioQueLoRealizo() {
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre("Mi Restaurante");
        sessionFactory.getCurrentSession().save(restaurante);

        UsuarioRestaurante usuarioRestaurante = new UsuarioRestaurante();
        usuarioRestaurante.setEmail("aa@aa.com");
        usuarioRestaurante.setPassword("1234");
        usuarioRestaurante.setRestaurante(restaurante);
        sessionFactory.getCurrentSession().save(usuarioRestaurante);


        Long idObtenido = repositorio.obtenerIdDelRestaurate(usuarioRestaurante.getId());


        assertNotNull(idObtenido);
        assertEquals(restaurante.getId(), idObtenido);
    }

}
