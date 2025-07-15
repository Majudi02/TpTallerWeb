package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioMensaje;
import com.tallerwebi.dominio.entidades.Mensaje;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class RepositorioMensajeImpl implements RepositorioMensaje {


    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioMensajeImpl(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}


    @Override
    public List<Mensaje> traerMensajesPorPedido(Long pedidoId) {
        String hql = "FROM Mensaje m WHERE m.pedidoId = :pedidoId ORDER BY m.fecha ASC";
        return sessionFactory.getCurrentSession()
                            .createQuery(hql,Mensaje.class)
                            .setParameter("pedidoId",pedidoId)
                            .getResultList();
    }

    @Override
    public void guardar(Mensaje mensaje) {
        sessionFactory.getCurrentSession().save(mensaje);
    }
}
