package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioPedido;
import com.tallerwebi.dominio.RepositorioPedidoPlato;
import com.tallerwebi.dominio.entidades.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository("repositorioPedido")
public class RepositorioPedidoImpl implements RepositorioPedido {

    private SessionFactory sessionFactory;
    @Autowired
    private RepositorioPedidoPlato repositorioPedidoPlato;


    @Autowired
    public RepositorioPedidoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void crearPedido(Pedido pedido) {
        sessionFactory.getCurrentSession().save(pedido);
    }

    @Override
    public void agregarPlatoAlPedido(Plato plato, Long idUsuario) {
        Pedido pedidoBuscado = this.buscarPedidoActivoPorUsuario(idUsuario);

        if (pedidoBuscado == null) {
            pedidoBuscado = new Pedido();
            pedidoBuscado.setUsuario(sessionFactory.getCurrentSession().get(UsuarioNutriya.class, idUsuario));
            pedidoBuscado.setPedidoPlatos(new ArrayList<>());
            pedidoBuscado.setFinalizo(false);
            pedidoBuscado.setFecha(String.valueOf(LocalDateTime.now()));
            pedidoBuscado.setPrecio(0.0);
            pedidoBuscado.setEstadoPedido(EstadoPedido.PENDIENTE);
        }

        PedidoPlato nuevoPedidoPlato = new PedidoPlato();
        nuevoPedidoPlato.setPedido(pedidoBuscado);
        nuevoPedidoPlato.setPlato(plato);
        nuevoPedidoPlato.setEstadoPlato(EstadoPlato.PENDIENTE);

        pedidoBuscado.getPedidoPlatos().add(nuevoPedidoPlato);

        // Actualizá el precio sumando el precio del plato
        pedidoBuscado.setPrecio(pedidoBuscado.getPrecio() + plato.getPrecio());

        sessionFactory.getCurrentSession().saveOrUpdate(pedidoBuscado);
    }


    @Override
    public List<PedidoPlato> mostrarPlatosDelPedidoActual(Long idUsuario) {
        String hql = "SELECT pp FROM PedidoPlato pp " +
                "JOIN FETCH pp.plato " +
                "JOIN pp.pedido p " +
                "WHERE p.usuario.id = :usuarioId AND p.estadoPedido = :estado AND p.finalizo = false";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, PedidoPlato.class)
                .setParameter("usuarioId", idUsuario)
                .setParameter("estado", EstadoPedido.PENDIENTE)
                .getResultList();
    }


    @Override
    public Double mostrarPrecioTotalDelPedidoActual(Long idUsuario) {
        Pedido pedido = this.buscarPedidoActivoPorUsuario(idUsuario);
        return (pedido != null && pedido.getPrecio() != null) ? pedido.getPrecio() : 0.0;
    }


    @Override
    public Pedido buscarPedidoActivoPorUsuario(Long idUsuario) {
        UsuarioNutriya usuario = sessionFactory.getCurrentSession().get(UsuarioNutriya.class, idUsuario);

        String hql = "FROM Pedido p WHERE p.usuario = :usuario AND p.finalizo = false";
        List<Pedido> pedidos = sessionFactory.getCurrentSession()
                .createQuery(hql, Pedido.class)
                .setParameter("usuario", usuario)
                .getResultList();

        if (pedidos.size() > 1) {
            System.out.println("⚠️ Usuario con múltiples pedidos activos: " + idUsuario);
        }

        return pedidos.isEmpty() ? null : pedidos.get(0);
    }


    @Override
    public void agregarPlatoAlPedido(Long idUsuario, Plato plato) {
        Pedido pedidoBuscado = this.buscarPedidoActivoPorUsuario(idUsuario);
        if (pedidoBuscado != null) {
            PedidoPlato pedidoPlato = new PedidoPlato();
            pedidoPlato.setPedido(pedidoBuscado);
            pedidoPlato.setPlato(plato);
            pedidoPlato.setEstadoPlato(EstadoPlato.PENDIENTE);

            pedidoBuscado.getPedidoPlatos().add(pedidoPlato);

            pedidoBuscado.setPrecio(pedidoBuscado.getPrecio() + plato.getPrecio());

            sessionFactory.getCurrentSession().saveOrUpdate(pedidoBuscado);
        }
    }


    @Override
    public Pedido finalizarPedido(Long idUsuario) {
        Pedido pedido = this.buscarPedidoActivoPorUsuario(idUsuario);

        if (pedido != null) {
            pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
            pedido.setFinalizo(false);
            sessionFactory.getCurrentSession().saveOrUpdate(pedido);
        }

        return pedido;
    }

    /*
    @Override
    public List<Pedido> traerPedidosListosParaRetirar() {
        String hql = "FROM Pedido p " +
                "JOIN FETCH p.pedidoPlatos pp " +
                "JOIN FETCH pp.plato pl " +
                "JOIN FETCH pl.restaurante " +
                "WHERE p.finalizo = false";

        List<Pedido> pedidos = sessionFactory.getCurrentSession()
                .createQuery(hql, Pedido.class)
                .getResultList();

        List<Pedido> pedidosListos = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            if (pedido.todosLosPlatosFinalizados()) {
                pedidosListos.add(pedido);
            }
        }

        return pedidosListos;
    }

     */


    @Override
    public Pedido buscarPorId(Integer idPedido) {
        String hql = "FROM Pedido p WHERE p.id = :idPedido";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Pedido.class)
                .setParameter("idPedido", idPedido)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Pedido> listarPedidosPorUsuario(Long usuarioId) {
        String hql = "FROM Pedido p " +
                "WHERE p.usuario.id = :uid " +
                "ORDER BY p.fecha DESC";
        return sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("uid", usuarioId)
                .list();
    }

    @Override
    public void confirmarPedido(Long idUsuario) {
        Pedido pedido = buscarPedidoActivoPorUsuario(idUsuario);
        if (pedido != null) {
            pedido.setEstadoPedido(EstadoPedido.EN_PROCESO);
            pedido.setFinalizo(false);
            sessionFactory.getCurrentSession().saveOrUpdate(pedido);
        }
    }

}


