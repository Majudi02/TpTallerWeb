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
    public void queSePuedaGuardarUnPlatoEnUnPedidoExistente() {
        Cliente usuario = new Cliente();
        usuario.setId(1L);

        Plato plato = new Plato();
        plato.setId(1);
        plato.setNombre("Milanesa");
        plato.setPrecio(100.0);

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setPlatos(new ArrayList<>());
        pedido.setFinalizo(false);
        pedido.setPrecio(0.0);

        Query<Pedido> queryMock = mock(Query.class);

        when(sessionMock.createQuery(anyString(), eq(Pedido.class))).thenReturn(queryMock);
        when(queryMock.setParameter(eq("usuario"), any())).thenReturn(queryMock);
        when(queryMock.setParameter(eq("finalizo"), eq(false))).thenReturn(queryMock);
        when(queryMock.uniqueResult()).thenReturn(pedido);


        when(sessionMock.get(eq(UsuarioNutriya.class), eq(1L))).thenReturn(usuario);


        repositorio.agregarPlatoAlPedido(plato, usuario.getId());


        assertTrue(pedido.getPlatos().contains(plato));
        verify(sessionMock).saveOrUpdate(pedido);
    }


    @Test
    public void queSePuedaFinalizarUnPedido() {
        Long idUsuario = 1L;

        Cliente cliente = new Cliente();
        cliente.setId(idUsuario);

        Pedido pedido = new Pedido();
        pedido.setUsuario(cliente);
        pedido.setPlatos(new ArrayList<>());
        pedido.setFinalizo(false);
        pedido.setPrecio(0.0);

        when(sessionMock.get(UsuarioNutriya.class, idUsuario)).thenReturn(cliente);

        Query<Pedido> queryMock = mock(Query.class);
        when(sessionMock.createQuery(anyString(), eq(Pedido.class))).thenReturn(queryMock);
        when(queryMock.setParameter("usuario", cliente)).thenReturn(queryMock);
        when(queryMock.uniqueResult()).thenReturn(pedido);

        repositorio.finalizarPedido(idUsuario);

        assertTrue(pedido.isFinalizo());
        verify(sessionMock).saveOrUpdate(pedido);
    }





}
