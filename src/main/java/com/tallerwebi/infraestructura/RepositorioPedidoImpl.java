package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.dominio.RepositorioPedido;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.entidades.Cliente;
import com.tallerwebi.dominio.entidades.Pedido;
import com.tallerwebi.dominio.entidades.Plato;
import com.tallerwebi.dominio.entidades.UsuarioNutriya;
import com.tallerwebi.presentacion.UsuarioDTO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository("repositorioPedido")
public class RepositorioPedidoImpl implements RepositorioPedido {

    private SessionFactory sessionFactory;


    @Autowired
    public RepositorioPedidoImpl(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }



    @Override
    public Pedido buscarPedidoActivoPorUsuario() {
        String hql = "FROM Pedido p WHERE p.finalizo = false";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Pedido.class)
                .uniqueResult();
    }

    @Override
    public void crearPedido(Pedido pedido) {
        sessionFactory.getCurrentSession().save(pedido);
    }

    @Override
    public void agregarPlatoAlPedido(Plato plato){
        Pedido pedidoBuscado = this.buscarPedidoActivoPorUsuario();

        if (pedidoBuscado == null) {
            pedidoBuscado = new Pedido();
            pedidoBuscado.setPlatos(new ArrayList<>());
            pedidoBuscado.setFinalizo(false);
            pedidoBuscado.setFecha(String.valueOf(LocalDateTime.now()));
            pedidoBuscado.setPrecio(plato.getPrecio());
            crearPedido(pedidoBuscado);
        }

        pedidoBuscado.getPlatos().add(plato);
        sessionFactory.getCurrentSession().saveOrUpdate(pedidoBuscado);
    }


/*
    @Override
    public Pedido buscarPedidoActivoPorUsuario(Cliente usuario) {
        String hql = "FROM Pedido p WHERE p.usuario = :usuario AND p.finalizo = false";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Pedido.class)
                .setParameter("usuario", usuario)
                .uniqueResult();
    }

    @Override
    public void crearPedido(Pedido pedido) {
        sessionFactory.getCurrentSession().save(pedido);
    }

    @Override
    public void agregarPlatoAlPedido(Cliente usuario,Plato plato){
        Pedido pedidoBuscado= this.buscarPedidoActivoPorUsuario(usuario);
        if (pedidoBuscado!=null){
            pedidoBuscado.getPlatos().add(plato);
            sessionFactory.getCurrentSession().saveOrUpdate(pedidoBuscado);
        }
    }

 */
}
