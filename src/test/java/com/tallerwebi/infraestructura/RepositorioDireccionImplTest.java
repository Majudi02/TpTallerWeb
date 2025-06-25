package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Direccion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RepositorioDireccionImplTest {

    private SessionFactory sessionFactoryMock;
    private Session sessionMock;
    private RepositorioDireccionImpl repositorioDireccion;

    @BeforeEach
    void setUp() {
        sessionFactoryMock = mock(SessionFactory.class);
        sessionMock = mock(Session.class);
        when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);
        repositorioDireccion = new RepositorioDireccionImpl(sessionFactoryMock);
    }

    @Test
    void guardarDireccionDeberiaLlamarSaveOrUpdate() {
        Direccion direccion = new Direccion();
        Direccion resultado = repositorioDireccion.guardarDireccion(direccion);
        verify(sessionMock).saveOrUpdate(direccion);
        assertEquals(direccion, resultado);
    }

    @Test
    void buscarPorIdMeDeberiaRetornarDireccionCorrectamente() {
        Long id = 1L;
        Direccion direccion = new Direccion();
        when(sessionMock.get(Direccion.class, id)).thenReturn(direccion);

        Direccion resultado = repositorioDireccion.buscarPorId(id);

        verify(sessionMock).get(Direccion.class, id);
        assertEquals(direccion, resultado);
    }

    @Test
    void eliminarDireccionPorIdDireccionExistenteDeberiaEliminarLaDireccion() {
        Long id = 1L;
        Direccion direccion = new Direccion();

        when(sessionMock.get(Direccion.class, id)).thenReturn(direccion);

        repositorioDireccion.eliminarDireccionPorId(id);

        verify(sessionMock).get(Direccion.class, id);
        verify(sessionMock).delete(direccion);
    }

    @Test
    void buscarDireccionesClienteDeberiaRetornarListaDeDireccionesDelCliente() {
        Long clienteId = 5L;
        String hql = "FROM Direccion d WHERE d.cliente.id = :clienteId";

        Query<Direccion> queryMock = mock(Query.class);
        List<Direccion> direccionesMock = List.of(new Direccion(), new Direccion());

        when(sessionMock.createQuery(hql, Direccion.class)).thenReturn(queryMock);
        when(queryMock.setParameter("clienteId", clienteId)).thenReturn(queryMock);
        when(queryMock.list()).thenReturn(direccionesMock);

        List<Direccion> resultado = repositorioDireccion.buscarDireccionesCliente(clienteId);

        verify(sessionMock).createQuery(hql, Direccion.class);
        verify(queryMock).setParameter("clienteId", clienteId);
        verify(queryMock).list();

        assertEquals(direccionesMock, resultado);
    }
}
