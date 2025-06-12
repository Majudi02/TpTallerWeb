package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.entidades.Cliente;
import com.tallerwebi.dominio.entidades.Pedido;
import com.tallerwebi.dominio.entidades.Plato;
import com.tallerwebi.dominio.entidades.UsuarioNutriya;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RepositorioPedidoImplTest {

    private SessionFactory sessionFactoryMock;
    private Session sessionMock;
    private RepositorioPedidoImpl repositorio;

    @BeforeEach
    public void setUp(){
        sessionFactoryMock = mock(SessionFactory.class);
        sessionMock= mock(Session.class);
        when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);

        repositorio = new RepositorioPedidoImpl(sessionFactoryMock);
    }


    @Test
    public void quePuedaDevolverUnPedidoCuandoElUsuarioLoTieneActivo(){
        Cliente usuario = new Cliente();
        usuario.setId(1L);

        Pedido pedido = new Pedido();
        pedido.setFinalizo(false);
     //   pedido.setUsuario(usuario);

        Query<Pedido> queryMock = mock(Query.class);
        when(sessionMock.createQuery(anyString(), eq(Pedido.class))).thenReturn(queryMock);
        when(queryMock.setParameter("usuario",usuario)).thenReturn(queryMock);
        when(queryMock.setParameter("finalizo",false)).thenReturn(queryMock);
        when(queryMock.uniqueResult()).thenReturn(pedido);

        Pedido pedidoActivoEncontrado = repositorio.buscarPedidoActivoPorUsuario();

        assertNotNull(pedidoActivoEncontrado);
        assertFalse(pedido.isFinalizo());

    }

    @Test
    public void queSePuedaGuardarUnPlatoEnUnPedidoExistente(){
        Cliente usuario = new Cliente();
        usuario.setId(1L);


        Plato plato = new Plato();
        plato.setId(1);
        plato.setNombre("Milanesa");

        Pedido pedido = new Pedido();
     //   pedido.setUsuario(usuario);
        pedido.setPlatos(new ArrayList<>());
        pedido.setFinalizo(false);

        Query<Pedido> queryMock = mock(Query.class);
        when(sessionMock.createQuery(anyString(), eq(Pedido.class))).thenReturn(queryMock);
        when(queryMock.setParameter(eq("usuario"),eq(usuario))).thenReturn(queryMock);


        when(queryMock.setParameter("finalizo",false)).thenReturn(queryMock);
        when(queryMock.uniqueResult()).thenReturn(pedido);
        when(queryMock.uniqueResult()).thenReturn(pedido);

        repositorio.agregarPlatoAlPedido(plato);

        assertTrue(pedido.getPlatos().contains(plato));
        verify(sessionMock).saveOrUpdate(pedido);

    }




}
