package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Etiqueta;
import com.tallerwebi.dominio.RepositorioEtiqueta;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repostitorioEtiqueta")
public class RepositorioEtiquetaImpl implements RepositorioEtiqueta {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioEtiquetaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Etiqueta> traerTodasLasEtiquetas() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Etiqueta", Etiqueta.class)
                .getResultList();
    }

    @Override
    public Etiqueta buscarEtiquetaPorId(Integer id) {
        return sessionFactory.getCurrentSession().get(Etiqueta.class, id);
    }
}
