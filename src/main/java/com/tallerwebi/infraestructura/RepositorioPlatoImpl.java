package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Plato;
import com.tallerwebi.dominio.RepositorioPlato;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioPlato")
public class RepositorioPlatoImpl implements RepositorioPlato {

    private SessionFactory sessionFactory;

    public RepositorioPlatoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Boolean crearPlato(Plato plato) {
        this.sessionFactory.getCurrentSession().save(plato);
        return true;
    }

    @Override
    public Plato buscarPlatoPorId(Integer id) {

        return sessionFactory.getCurrentSession().get(Plato.class, id);
    }

    @Override
    public boolean eliminarPlato(Integer id) {
        return false;
    }

    @Override
    public List<Plato> buscarPlatosPorTipoComida(String tipoComida) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT p FROM Plato p JOIN p.etiquetas e WHERE e.nombre = :nombreEtiqueta", Plato.class)
                .setParameter("nombreEtiqueta", tipoComida)
                .getResultList();
    }

    @Override
    public List<Plato> traerTodosLosPlatos() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Plato", Plato.class)
                .getResultList();
    }

    @Override
    public Boolean editarEtiquetas(Plato plato) {
        int actualizados = sessionFactory.getCurrentSession()
                .createQuery("UPDATE Plato p SET p.etiquetas = :etiquetas WHERE p.id = :id")
                .setParameter("etiquetas", plato.getEtiquetas())
                .setParameter("id", plato.getId())
                .executeUpdate();
        return actualizados > 0;
    }

}