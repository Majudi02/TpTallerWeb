package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuarioRestaurante;
import com.tallerwebi.dominio.Restaurante;
import com.tallerwebi.dominio.UsuarioRestaurante;
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
    public UsuarioRestaurante buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(UsuarioRestaurante.class, id);
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