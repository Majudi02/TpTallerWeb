package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioPedidoRestaurante;
import com.tallerwebi.dominio.entidades.EstadoPedido;
import com.tallerwebi.dominio.entidades.EstadoPlato;
import com.tallerwebi.dominio.entidades.Pedido;
import com.tallerwebi.dominio.entidades.PedidoPlato;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("repositorioPedidoRestaurante")
public class RepostitorioPedidoRestauranteImpl implements RepositorioPedidoRestaurante {


    private SessionFactory sessionFactory;

    @Autowired
    public RepostitorioPedidoRestauranteImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Pedido> traerTodosLosPedidos() {
        String hql = "SELECT DISTINCT p FROM Pedido p " +
                "JOIN FETCH p.pedidoPlatos pp " +
                "JOIN FETCH pp.plato pl " +
                "JOIN FETCH pl.restaurante";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, Pedido.class)
                .getResultList();
    }


    @Override
    public Long obtenerIdDelRestaurate(Long id) {
        String hql = "SELECT ur.restaurante.id FROM UsuarioRestaurante ur WHERE ur.id = :id";
        return sessionFactory.getCurrentSession().createQuery(hql, Long.class)
                .setParameter("id", id)
                .uniqueResult();
    }

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
            boolean todosFinalizados = true;

            for (PedidoPlato pp : pedido.getPedidoPlatos()) {
                if (pp.getEstadoPlato() != EstadoPlato.FINALIZADO) {
                    todosFinalizados = false;
                    break;
                }
            }

            if (todosFinalizados) {
                pedidosListos.add(pedido);
            }
        }

        return pedidosListos;
    }

    @Override
    public void entregarPedido(Integer idPedido) {
        Pedido pedido = sessionFactory.getCurrentSession().get(Pedido.class, idPedido);
        if (pedido != null) {
            pedido.setEstadoPedido(EstadoPedido.ENTREGADO);
            pedido.setFinalizo(true);
            sessionFactory.getCurrentSession().saveOrUpdate(pedido);
        }
    }

    @Override
    public Pedido buscarPorId(int id) {
        return sessionFactory.getCurrentSession().get(Pedido.class, id);
    }

    @Override
    public void guardar(Pedido pedido) {
        sessionFactory.getCurrentSession().saveOrUpdate(pedido);
    }
}
