package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
public class RepositorioPedidoPlatoImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioPedidoPlatoImpl repositorio;

    @BeforeEach
    public void setUp(){
        repositorio=new RepositorioPedidoPlatoImpl(sessionFactory);
    }

    @Test
    public void dadoQuteTengoUnPedidoPlatoGuardadoQueLoPuedaBuscarPorSuId(){
        Plato plato = new Plato();
        plato.setId(1);
        plato.setNombre("Ensalada César");
        plato.setPrecio(10.0);
        sessionFactory.getCurrentSession().save(plato);

        Cliente usuario = new Cliente();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        sessionFactory.getCurrentSession().save(usuario);

        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setUsuario(usuario);
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
        pedido.setFinalizo(false);
        sessionFactory.getCurrentSession().save(pedido);

        PedidoPlato pedidoPlato = new PedidoPlato();
        pedidoPlato.setId(100L);
        pedidoPlato.setPlato(plato);
        pedidoPlato.setPedido(pedido);
        pedidoPlato.setEstadoPlato(EstadoPlato.PENDIENTE);
        pedido.setPedidoPlatos(List.of(pedidoPlato));

        sessionFactory.getCurrentSession().save(pedidoPlato);

        PedidoPlato pedidoPlatoBuscado = repositorio.buscarPorId(pedidoPlato.getId());

        assertEquals(pedidoPlato.getId(), pedidoPlatoBuscado.getId());
    }


    @Test
    public void queSePuedaGuardarUnPedidoPlatoEnLaBaseDeDatos(){
        Plato plato = new Plato();
        plato.setId(1);
        plato.setNombre("Ensalada César");
        plato.setPrecio(10.0);
        sessionFactory.getCurrentSession().save(plato);

        Cliente usuario = new Cliente();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        sessionFactory.getCurrentSession().save(usuario);

        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setUsuario(usuario);
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
        pedido.setFinalizo(false);
        sessionFactory.getCurrentSession().save(pedido);

        PedidoPlato pedidoPlato = new PedidoPlato();
        pedidoPlato.setId(100L);
        pedidoPlato.setPlato(plato);
        pedidoPlato.setPedido(pedido);
        pedidoPlato.setEstadoPlato(EstadoPlato.PENDIENTE);
        pedido.setPedidoPlatos(List.of(pedidoPlato));

        repositorio.guardar(pedidoPlato);

        PedidoPlato pedidoPlatoBuscado = repositorio.buscarPorId(pedidoPlato.getId());


        assertEquals(pedidoPlato.getId(), pedidoPlatoBuscado.getId());
    }

    @Test
    public void queSePuedaFinalizarUnPedidoPlatoPorId(){
        Plato plato = new Plato();
        plato.setId(1);
        plato.setNombre("Ensalada César");
        plato.setPrecio(10.0);
        sessionFactory.getCurrentSession().save(plato);

        Cliente usuario = new Cliente();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        sessionFactory.getCurrentSession().save(usuario);

        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setUsuario(usuario);
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
        pedido.setFinalizo(false);
        sessionFactory.getCurrentSession().save(pedido);

        PedidoPlato pedidoPlato = new PedidoPlato();
        pedidoPlato.setId(100L);
        pedidoPlato.setPlato(plato);
        pedidoPlato.setPedido(pedido);
        pedidoPlato.setEstadoPlato(EstadoPlato.PENDIENTE);
        pedido.setPedidoPlatos(List.of(pedidoPlato));

        repositorio.guardar(pedidoPlato);

        repositorio.finalizarPedido(pedidoPlato.getId());

        PedidoPlato pedidoActualizado = repositorio.buscarPorId(pedidoPlato.getId());

        assertEquals(EstadoPlato.FINALIZADO, pedidoActualizado.getEstadoPlato());
    }

    @Test
    public void queSePuedaObtenerElPromedioDeCalificaionDeUnPlato(){
            Plato plato = new Plato();
            plato.setNombre("Pizza Margherita");
            sessionFactory.getCurrentSession().save(plato);

            Cliente usuario = new Cliente();
            usuario.setNombre("Pedro");
            sessionFactory.getCurrentSession().save(usuario);

            Pedido pedido = new Pedido();
            pedido.setUsuario(usuario);
            pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
            pedido.setFinalizo(false);
            sessionFactory.getCurrentSession().save(pedido);

            PedidoPlato pedidoPlato1 = new PedidoPlato();
            pedidoPlato1.setPedido(pedido);
            pedidoPlato1.setPlato(plato);
            pedidoPlato1.setCalificacion(4);
            sessionFactory.getCurrentSession().save(pedidoPlato1);

            PedidoPlato pedidoPlato2 = new PedidoPlato();
            pedidoPlato2.setPedido(pedido);
            pedidoPlato2.setPlato(plato);
            pedidoPlato2.setCalificacion(5);
            sessionFactory.getCurrentSession().save(pedidoPlato2);

            Double promedio = repositorio.obtenerPromedioCalificacionPorPlato(plato.getId());

            assertEquals(4.5, promedio);
        }


}
