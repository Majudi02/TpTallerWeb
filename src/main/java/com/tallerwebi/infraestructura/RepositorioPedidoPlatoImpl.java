package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioPedidoPlato;
import com.tallerwebi.dominio.entidades.EstadoPedido;
import com.tallerwebi.dominio.entidades.EstadoPlato;
import com.tallerwebi.dominio.entidades.Pedido;
import com.tallerwebi.dominio.entidades.PedidoPlato;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

}