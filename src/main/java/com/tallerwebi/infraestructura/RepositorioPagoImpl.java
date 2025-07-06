package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioPago;
import com.tallerwebi.dominio.entidades.Pago;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository("pagoRepositorio")
@Transactional
public class RepositorioPagoImpl implements RepositorioPago {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioPagoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Pago pago) {
        sessionFactory.getCurrentSession().save(pago);
    }



    @Override
    public Pago buscarPorPedidoId(Integer idPedido) {
        String hql = "FROM Pago p WHERE p.pedido.id = :idPedido";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Pago.class)
                .setParameter("idPedido", idPedido)
                .uniqueResult();
    }
}