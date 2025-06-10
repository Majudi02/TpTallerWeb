package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.RepositorioPlato;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioPlato")
public class RepositorioPlatoImpl implements RepositorioPlato {

    private SessionFactory sessionFactory;

    public RepositorioPlatoImpl(SessionFactory sessionFactory){this.sessionFactory = sessionFactory;}

    @Override
    public Boolean crearPlato(com.tallerwebi.dominio.entidades.Plato plato) {
        this.sessionFactory.getCurrentSession().save(plato);
        return true;
    }

    @Override
    public com.tallerwebi.dominio.entidades.Plato buscarPlatoPorId(Integer id) {

        return sessionFactory.getCurrentSession().get(com.tallerwebi.dominio.entidades.Plato.class,id);
    }

    @Override
    public boolean eliminarPlato(Integer id) {
        return false;
    }

    @Override
    public List<com.tallerwebi.dominio.entidades.Plato> buscarPlatosPorTipoComida(String tipoComida) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT p FROM Plato p JOIN p.etiquetas e WHERE e.nombre = :nombreEtiqueta", com.tallerwebi.dominio.entidades.Plato.class)
                .setParameter("nombreEtiqueta", tipoComida)
                .getResultList();
    }

    @Override
    public List<com.tallerwebi.dominio.entidades.Plato> traerTodosLosPlatos() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Plato", com.tallerwebi.dominio.entidades.Plato.class)
                .getResultList();
    }

    @Override
    public Boolean editarEtiquetas(com.tallerwebi.dominio.entidades.Plato plato) {
        int actualizados = sessionFactory.getCurrentSession()
                .createQuery("UPDATE Plato p SET p.etiquetas = :etiquetas WHERE p.id = :id")
                .setParameter("etiquetas", plato.getEtiquetas())
                .setParameter("id", plato.getId())
                .executeUpdate();
        return actualizados > 0;
    }

    @Override
    public Boolean actualizarPlato(com.tallerwebi.dominio.entidades.Plato plato) {
        this.sessionFactory.getCurrentSession().update(plato);
        return true;
    }

}
