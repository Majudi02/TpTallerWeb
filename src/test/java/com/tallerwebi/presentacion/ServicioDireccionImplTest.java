package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.RepositorioDireccion;
import com.tallerwebi.dominio.ServicioDireccionImpl;
import com.tallerwebi.dominio.entidades.Direccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ServicioDireccionImplTest {

    private RepositorioDireccion repositorioMock;
    private ServicioDireccionImpl servicioDireccion;

    @BeforeEach
    void setUp() {
        repositorioMock = mock(RepositorioDireccion.class);
        servicioDireccion = new ServicioDireccionImpl(repositorioMock);
    }

    @Test
    void guardarDireccionEnElServicioDeberiaDevolverLaDireccionGuardadaEnElRepositorio() {
        Direccion direccion = new Direccion();
        when(repositorioMock.guardarDireccion(direccion)).thenReturn(direccion);

        Direccion resultado = servicioDireccion.guardarDireccion(direccion);

        verify(repositorioMock).guardarDireccion(direccion);
        assertEquals(direccion, resultado);
    }

    @Test
    void obtenerDireccionesPorIdClienteDebeDevolverListaDeDirecciones() {
        Long clienteId = 1L;
        List<Direccion> direccionesMock = List.of(new Direccion(), new Direccion());
        when(repositorioMock.buscarDireccionesCliente(clienteId)).thenReturn(direccionesMock);

        List<Direccion> resultado = servicioDireccion.obtenerDireccionesPorCliente(clienteId);

        verify(repositorioMock).buscarDireccionesCliente(clienteId);
        assertEquals(direccionesMock, resultado);
    }

    @Test
    void eliminarDireccionPorIdDeberiaLlamarYBorrarEnElRepositorio() {
        Long id = 10L;

        servicioDireccion.eliminarDireccionPorId(id);

        verify(repositorioMock).eliminarDireccionPorId(id);
    }

    @Test
    void actualizarDireccionDeberiaActualizarLosCamposYGuardar() {
        Direccion direccionOriginal = new Direccion();
        direccionOriginal.setId(5L);
        direccionOriginal.setCalle("Calle Nombre");
        direccionOriginal.setNumero(100);
        direccionOriginal.setLocalidad("Localidad Nombre");

        Direccion direccionUpdate = new Direccion();
        direccionUpdate.setId(5L);
        direccionUpdate.setCalle("Calle Nueva");
        direccionUpdate.setNumero(1234);
        direccionUpdate.setLocalidad("Localidad Nueva");

        when(repositorioMock.buscarPorId(5L)).thenReturn(direccionOriginal);
        when(repositorioMock.guardarDireccion(direccionOriginal)).thenReturn(direccionOriginal);

        Direccion resultado = servicioDireccion.actualizarDireccion(direccionUpdate);

        verify(repositorioMock).buscarPorId(5L);
        verify(repositorioMock).guardarDireccion(direccionOriginal);

        assertEquals("Calle Nueva", resultado.getCalle());
        assertEquals(1234, resultado.getNumero());
        assertEquals("Localidad Nueva", resultado.getLocalidad());
    }
}
