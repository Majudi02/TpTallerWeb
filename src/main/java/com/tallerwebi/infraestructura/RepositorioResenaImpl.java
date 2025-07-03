package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioResena;
import com.tallerwebi.dominio.entidades.Resena;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioResena")
public class RepositorioResenaImpl implements RepositorioResena {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioResenaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Resena resena) {
        sessionFactory.getCurrentSession().save(resena);
    }

    @Override
    public List<Resena> obtenerUltimasPorRestaurante(Long restauranteId, int cantidad) {
        return sessionFactory.getCurrentSession().createQuery(
                        "FROM Resena r WHERE r.restaurante.id = :id ORDER BY r.fecha DESC", Resena.class)
                .setParameter("id", restauranteId)
                .setMaxResults(cantidad)
                .list();
    }

    @Override
    public List<Resena> obtenerResenasPorRestaurante(Long restauranteId) {
        String hql = "FROM Resena r WHERE r.restaurante.id = :restauranteId ORDER BY r.fecha DESC";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Resena.class)
                .setParameter("restauranteId", restauranteId)
                .getResultList();
    }

}

