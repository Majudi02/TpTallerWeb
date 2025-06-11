package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuarioNutriya;
import com.tallerwebi.dominio.entidades.Cliente;
import com.tallerwebi.dominio.entidades.Repartidor;
import com.tallerwebi.dominio.entidades.UsuarioNutriya;
import com.tallerwebi.dominio.entidades.UsuarioRestaurante;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RepositorioUsuarioNutriyaImplTest {
    private SessionFactory sessionFactoryMock;
    private Session sessionMock;
    private RepositorioUsuarioNutriyaImpl repositorio;

    @BeforeEach
    public void setUp() {
        sessionFactoryMock = mock(SessionFactory.class);
        sessionMock = mock(Session.class);
        when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);

        repositorio = new RepositorioUsuarioNutriyaImpl(sessionFactoryMock);
    }

    @Test
    public void puedoGuardarYBuscarClientePorEmail() {
        Cliente cliente = new Cliente();
        cliente.setEmail("cliente@mail.com");
        cliente.setPassword("1234");

        // Guardar (simplemente verifica que se llama a session.save)
        repositorio.guardar(cliente);
        verify(sessionMock).save(cliente);

        // Mockear Query y resultado para buscarPorEmail
        Query<UsuarioNutriya> queryMock = mock(Query.class);
        when(sessionMock.createQuery(anyString(), eq(UsuarioNutriya.class))).thenReturn(queryMock);
        when(queryMock.setParameter(eq("email"), eq("cliente@mail.com"))).thenReturn(queryMock);
        when(queryMock.uniqueResult()).thenReturn(cliente);

        UsuarioNutriya usuarioEncontrado = repositorio.buscarPorEmail("cliente@mail.com");

        assertNotNull(usuarioEncontrado);
        assertEquals("cliente@mail.com", usuarioEncontrado.getEmail());
        assertInstanceOf(Cliente.class, usuarioEncontrado);
    }

    @Test
    public void puedoGuardayYBuscarRestaurantePorEmail(){
        UsuarioRestaurante usuarioRestaurante = new UsuarioRestaurante();
        usuarioRestaurante.setEmail("restaurante@prueba.com");
        usuarioRestaurante.setPassword("1234");

        repositorio.guardar(usuarioRestaurante);
        verify(sessionMock).save(usuarioRestaurante);

        Query<UsuarioNutriya> queryMock = mock(Query.class);
        when(sessionMock.createQuery(anyString(), eq(UsuarioNutriya.class))).thenReturn(queryMock);
        when(queryMock.setParameter(eq("email"), eq("restaurante@prueba.com"))).thenReturn(queryMock);
        when(queryMock.uniqueResult()).thenReturn(usuarioRestaurante);

        UsuarioNutriya usuarioEncontrado = repositorio.buscarPorEmail("restaurante@prueba.com");

        assertNotNull(usuarioEncontrado);
        assertEquals("restaurante@prueba.com", usuarioEncontrado.getEmail());
        assertInstanceOf(UsuarioRestaurante.class, usuarioEncontrado);
    }

    @Test
    public void puedoGuardayYBuscarRepartidorPorEmail(){
        Repartidor repartidor = new Repartidor();
        repartidor.setEmail("repartidor@prueba.com");
        repartidor.setPassword("1234");

        repositorio.guardar(repartidor);
        verify(sessionMock).save(repartidor);

        Query<UsuarioNutriya> queryMock = mock(Query.class);
        when(sessionMock.createQuery(anyString(), eq(UsuarioNutriya.class))).thenReturn(queryMock);
        when(queryMock.setParameter(eq("email"), eq("repartidor@prueba.com"))).thenReturn(queryMock);
        when(queryMock.uniqueResult()).thenReturn(repartidor);

        UsuarioNutriya usuarioEncontrado = repositorio.buscarPorEmail("repartidor@prueba.com");

        assertNotNull(usuarioEncontrado);
        assertEquals("repartidor@prueba.com", usuarioEncontrado.getEmail());
        assertInstanceOf(Repartidor.class, usuarioEncontrado);
    }

    @Test
    public void puedoGuardarYBuscarClientePorEmailYContrasenia(){
        Cliente cliente = new Cliente();
        cliente.setEmail("cliente@mail.com");
        cliente.setPassword("1234");

        repositorio.guardar(cliente);
        verify(sessionMock).save(cliente);

        // Mockear Query y resultado
        Query<UsuarioNutriya> queryMock = mock(Query.class);
        when(sessionMock.createQuery(anyString(), eq(UsuarioNutriya.class))).thenReturn(queryMock);
        when(queryMock.setParameter(eq("email"), eq("cliente@mail.com"))).thenReturn(queryMock);
        when(queryMock.setParameter(eq("password"), eq("1234"))).thenReturn(queryMock);
        when(queryMock.uniqueResult()).thenReturn(cliente);

        UsuarioNutriya usuarioEncontrado = repositorio.buscarPorEmailYPassword("cliente@mail.com", "1234");

        assertNotNull(usuarioEncontrado);
        assertEquals("cliente@mail.com", usuarioEncontrado.getEmail());
        assertInstanceOf(Cliente.class, usuarioEncontrado);
    }

    @Test
    public void puedoGuardarYModificarCliente() {
        Cliente cliente = new Cliente();
        cliente.setEmail("cliente@mail.com");
        cliente.setPassword("1234");

        repositorio.guardar(cliente);
        verify(sessionMock).save(cliente);

        cliente.setPassword("nuevoPass");

        repositorio.modificar(cliente);
        verify(sessionMock).update(cliente);
    }

    @Test
    public void queSePuedaObtenerUnUsuasroConUnToken(){
        String token = UUID.randomUUID().toString();
        UsuarioNutriya usuarioMock = mock(UsuarioNutriya.class);

        RepositorioUsuarioNutriya repositorioMock = mock(RepositorioUsuarioNutriya.class);
        when(repositorioMock.buscarPorTokenConfirmacion(token)).thenReturn(usuarioMock);


        UsuarioNutriya resultado = repositorioMock.buscarPorTokenConfirmacion(token);

        assertNotNull(resultado);
        assertEquals(usuarioMock, resultado);

    }



}
