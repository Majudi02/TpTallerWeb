package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.  RepositorioPedidoPlato;
import com.tallerwebi.dominio.entidades.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioPedidoPlatoImpl implements RepositorioPedidoPlato {

    @Autowired
    private SessionFactory sessionFactory;

    public RepositorioPedidoPlatoImpl(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}


    @Override
    public PedidoPlato buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(PedidoPlato.class, id);
    }

    @Override
    public void guardar(PedidoPlato pedidoPlato) {
        sessionFactory.getCurrentSession().saveOrUpdate(pedidoPlato);
    }

    @Override
    public void finalizarPedido(Long id) {
        PedidoPlato pedidoPlato = sessionFactory.getCurrentSession().get(PedidoPlato.class, id);
        pedidoPlato.setEstadoPlato(EstadoPlato.FINALIZADO);

        Pedido pedido = pedidoPlato.getPedido();
        if (pedido.todosLosPlatosFinalizados()) {
            pedido.setEstadoPedido(EstadoPedido.LISTO_PARA_ENVIAR);
            pedido.setFinalizo(true);
        }
        sessionFactory.getCurrentSession().saveOrUpdate(pedidoPlato);
    }

    @Override
    public Double obtenerPromedioCalificacionPorPlato(Integer id) {
        String hql = "SELECT AVG(pp.calificacion) FROM PedidoPlato pp " +
                "WHERE pp.plato.id = :id AND pp.calificacion IS NOT NULL";

        Double promedio = (Double) sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("id", id)
                .uniqueResult();

        return promedio != null ? promedio : 0.0;
    }

    @Override
    public List<PedidoPlato> obtenerPlatosPorRestaurante(Long idRestaurante) {
        String hql = "SELECT pp FROM PedidoPlato pp " +
                "JOIN FETCH pp.plato pl " +
                "WHERE pl.restaurante.id = :idRestaurante";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, PedidoPlato.class)
                .setParameter("idRestaurante", idRestaurante)
                .getResultList();
    }

    @Override
    public List<Plato> traerLos3PlatosMenosPedidos(Long idRestaurante) {
        String sql = "SELECT p.* " +
                "FROM Plato p " +
                "LEFT JOIN Pedido_Plato pp ON pp.plato_id = p.id " +
                "LEFT JOIN Pedido pe ON pp.pedido_id = pe.id AND pe.restaurante_id = :idRestaurante " +
                "WHERE p.restaurante_id = :idRestaurante " +
                "GROUP BY p.id " +
                "ORDER BY COUNT(pp.id) ASC " +
                "LIMIT 3";

        return sessionFactory.getCurrentSession()
                .createNativeQuery(sql, Plato.class)
                .setParameter("idRestaurante", idRestaurante)
                .getResultList();
    }
}