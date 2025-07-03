package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuarioRestaurante;
import com.tallerwebi.dominio.entidades.Restaurante;
import com.tallerwebi.dominio.entidades.UsuarioRestaurante;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RepositorioUsuarioRestauranteImpl implements RepositorioUsuarioRestaurante {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioRestauranteImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public UsuarioRestaurante buscarPorUsuarioId(Long usuarioId) {
        String hql = "FROM UsuarioRestaurante ur WHERE ur.id = :usuarioId";
        return (UsuarioRestaurante) sessionFactory.getCurrentSession()
                .createQuery(hql, UsuarioRestaurante.class)
                .setParameter("usuarioId", usuarioId)
                .uniqueResult();
    }

    @Override
    public Restaurante buscarRestaurantePorId(Long restauranteId) {
        String hql = "SELECT ur.restaurante FROM UsuarioRestaurante ur WHERE ur.restaurante.id = :restauranteId";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Restaurante.class)
                .setParameter("restauranteId", restauranteId)
                .uniqueResult();
    }

    @Override
    public List<Restaurante> traerRestaurantesDestacados() {
        String hql = "FROM UsuarioRestaurante ur ORDER BY rand()";
        List<UsuarioRestaurante> usuarios = sessionFactory.getCurrentSession()
                .createQuery(hql, UsuarioRestaurante.class)
                .setMaxResults(5)
                .list();
        return usuarios.stream()
                .map(UsuarioRestaurante::getRestaurante)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioRestaurante> buscarTodos() {
        String hql = "FROM UsuarioRestaurante";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, UsuarioRestaurante.class)
                .list();
    }

    @Override
    public List<Restaurante> buscarTodosLosRestaurantes() {
        return buscarTodos().stream()
                .map(UsuarioRestaurante::getRestaurante)
                .collect(Collectors.toList());
    }

    @Override
    public void guardar(UsuarioRestaurante usuarioRestaurante) {
        sessionFactory.getCurrentSession().save(usuarioRestaurante);
    }

    @Override
    public void modificar(UsuarioRestaurante usuarioRestaurante) {
        sessionFactory.getCurrentSession().update(usuarioRestaurante);
    }
}