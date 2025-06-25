package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioPedido;
import com.tallerwebi.dominio.RepositorioPedidoRestaurante;
import com.tallerwebi.dominio.entidades.EstadoPedido;
import com.tallerwebi.dominio.entidades.Pedido;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioPedidoRestaurante")
public class RepostitorioPedidoRestauranteImpl implements RepositorioPedidoRestaurante {


    private SessionFactory sessionFactory;

    @Autowired
    public RepostitorioPedidoRestauranteImpl(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }


    @Override
    public List<Pedido> traerTodosLosPedidos() {
        String hql = "FROM Pedido WHERE estadoPedido = :estado";
       return sessionFactory.getCurrentSession()
                .createQuery(hql, Pedido.class)
               .setParameter("estado", EstadoPedido.EN_PROCESO)
                .getResultList();
    }

    @Override
    public Long obtenerIdDelRestaurate(Long id) {
        String hql = "SELECT ur.restaurante.id FROM UsuarioRestaurante ur WHERE ur.id = :id";
        return sessionFactory.getCurrentSession().createQuery(hql,Long.class)
                .setParameter("id",id)
                .uniqueResult();
    }
}
