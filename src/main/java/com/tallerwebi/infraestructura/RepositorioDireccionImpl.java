package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioDireccion;
import com.tallerwebi.dominio.entidades.Direccion;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class RepositorioDireccionImpl implements RepositorioDireccion {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioDireccionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Direccion guardarDireccion(Direccion direccion) {
        sessionFactory.getCurrentSession().saveOrUpdate(direccion);
        return direccion;
    }

    @Override
    public List<Direccion> buscarDireccionesCliente(Long clienteId) {
        String hql = "FROM Direccion d WHERE d.cliente.id = :clienteId";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Direccion.class)
                .setParameter("clienteId", clienteId)
                .list();
    }

    @Override
    public void eliminarDireccionPorId(Long id) {
        Direccion direccion = sessionFactory.getCurrentSession().get(Direccion.class, id);
        if (direccion != null) {
            sessionFactory.getCurrentSession().delete(direccion);
        }
    }

    @Override
    public Direccion buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Direccion.class, id);
    }
}
