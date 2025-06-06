package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuarioNutriya;
import com.tallerwebi.dominio.UsuarioNutriya;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioUsuarioNutriya")
public class RepositorioUsuarioNutriyaImpl implements RepositorioUsuarioNutriya {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioNutriyaImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public UsuarioNutriya buscarPorEmailYPassword(String email, String password) {
        String hql = "FROM UsuarioNutriya u WHERE u.email = :email AND u.password = :password";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, UsuarioNutriya.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .uniqueResult();
    }

    @Override
    public void guardar(UsuarioNutriya usuario) {
        sessionFactory.getCurrentSession().save(usuario);
    }

    @Override
    public UsuarioNutriya buscarPorEmail(String email) {
        String hql = "FROM UsuarioNutriya u WHERE u.email = :email";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, UsuarioNutriya.class)
                .setParameter("email", email)
                .uniqueResult();
    }


    @Override
    public void modificar(UsuarioNutriya usuario) {
        sessionFactory.getCurrentSession().update(usuario);
    }
}
