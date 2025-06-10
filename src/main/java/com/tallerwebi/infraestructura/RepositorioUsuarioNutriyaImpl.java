package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuarioNutriya;
import com.tallerwebi.dominio.entidades.UsuarioNutriya;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;


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
    @Transactional
    public void guardar(UsuarioNutriya usuario) {
        sessionFactory.getCurrentSession().save(usuario);
    }

    @Override
    @Transactional
    public UsuarioNutriya buscarPorEmail(String email) {
        System.out.println("Buscando usuario con email: " + email);
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
