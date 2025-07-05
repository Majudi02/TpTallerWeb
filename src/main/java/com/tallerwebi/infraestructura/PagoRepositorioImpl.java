package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.PagoRepositorio;
import com.tallerwebi.dominio.entidades.Pago;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository("pagoRepositorio")
@Transactional
public class PagoRepositorioImpl implements PagoRepositorio {

    private final SessionFactory sessionFactory;

    @Autowired
    public PagoRepositorioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Pago pago) {
        sessionFactory.getCurrentSession().save(pago);
    }

    @Override
    public Pago buscarPorIdPagoMercadoPago(Long idPagoMP) {
        String hql = "FROM Pago p WHERE p.idPagoMercadoPago = :idPagoMP";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Pago.class)
                .setParameter("idPagoMP", idPagoMP)
                .uniqueResult();
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