package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.Cliente;
import com.tallerwebi.dominio.entidades.Repartidor;
import com.tallerwebi.dominio.entidades.UsuarioNutriya;
import com.tallerwebi.dominio.entidades.UsuarioRestaurante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioUsuarioImplTest {

    private ServicioUsuarioImpl servicioUsuario;
    private RepositorioUsuarioNutriya repositorioMock;

    @BeforeEach
    public void init() {
        repositorioMock = mock(RepositorioUsuarioNutriya.class);
        servicioUsuario = new ServicioUsuarioImpl(repositorioMock);
    }

    @Test
    public void puedoRegistrarUnCliente() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setTipoUsuario("cliente");
        dto.setEmail("cliente@mail.com");
        dto.setPassword("1234");
        dto.setNombre("Ana");
        dto.setEdad(25);
        dto.setPesoActual(60);
        dto.setPesoDeseado(55);
        dto.setAltura(1.65);
        dto.setObjetivo("bajar");

        // Simulo que el email no existe en la bd
        when(repositorioMock.buscarPorEmail("cliente@mail.com")).thenReturn(null);

        servicioUsuario.registrarUsuario(dto);

        verify(repositorioMock).guardar(any(Cliente.class));
    }

    @Test
    public void puedoRegistrarUnRestaurante() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setTipoUsuario("restaurante");
        dto.setEmail("restaurante@mail.com");
        dto.setPassword("1234");
        dto.setNombre("Pizza Place");
        dto.setDescripcion("Pizzas artesanales");
        dto.setCalle("Av. Siempre Viva");
        dto.setNumero(123);
        dto.setLocalidad("Springfield");
        dto.setZona("Centro");
        dto.setTipoComidas(List.of("Italiana", "Vegana"));

        when(repositorioMock.buscarPorEmail("restaurante@mail.com")).thenReturn(null);

        servicioUsuario.registrarUsuario(dto);

        verify(repositorioMock).guardar(any(UsuarioRestaurante.class));
    }

    @Test
    public void puedoRegistrarUnRepartidor() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setTipoUsuario("repartidor");
        dto.setEmail("repartidor@mail.com");
        dto.setPassword("1234");
        dto.setNombre("Carlos");

        when(repositorioMock.buscarPorEmail("repartidor@mail.com")).thenReturn(null);

        servicioUsuario.registrarUsuario(dto);

        verify(repositorioMock).guardar(any(Repartidor.class));
    }

    @Test
    public void noPuedoRegistrarUnClienteConEmailExistente() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setTipoUsuario("cliente");
        dto.setEmail("cliente@mail.com");

        when(repositorioMock.buscarPorEmail("cliente@mail.com")).thenReturn(new Cliente());

        assertThrows(IllegalArgumentException.class, () -> {
            servicioUsuario.registrarUsuario(dto);
        });
    }

    @Test
    public void noPermiteRegistrarUsuarioConTipoInvalido() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setTipoUsuario("extraterrestre");
        dto.setEmail("x@mail.com");

        when(repositorioMock.buscarPorEmail("x@mail.com")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            servicioUsuario.registrarUsuario(dto);
        });
    }

    @Test
    public void validarLoginConDatosCorrectosDevuelveUsuarioDTO() {
        Cliente cliente = new Cliente();
        cliente.setEmail("cliente@mail.com");
        cliente.setPassword("1234");
        cliente.setNombre("Ana");
        cliente.setEdad(25);
        cliente.setPesoActual(60);
        cliente.setPesoDeseado(55);
        cliente.setAltura(1.65);
        cliente.setObjetivo("bajar");

        when(repositorioMock.buscarPorEmailYPassword("cliente@mail.com", "1234"))
                .thenReturn(cliente);

        UsuarioDTO dto = servicioUsuario.validarUsuario("cliente@mail.com", "1234");

        assertNotNull(dto);
        assertEquals("cliente", dto.getTipoUsuario());
        assertEquals("Ana", dto.getNombre());
    }

    @Test
    public void validarLoginConDatosIncorrectosDevuelveNull() {
        when(repositorioMock.buscarPorEmailYPassword("noexiste@mail.com", "clave")).thenReturn(null);

        UsuarioDTO dto = servicioUsuario.validarUsuario("noexiste@mail.com", "clave");

        assertNull(dto);
    }

    @Test
    public void obtenerUsuarioExistenteDevuelveDTO() {
        Cliente cliente = new Cliente();
        cliente.setEmail("cliente@mail.com");
        cliente.setNombre("Ana");
        cliente.setEdad(25);
        cliente.setPesoActual(60);
        cliente.setPesoDeseado(55);
        cliente.setAltura(1.65);
        cliente.setObjetivo("bajar");

        when(repositorioMock.buscarPorEmail("cliente@mail.com")).thenReturn(cliente);

        UsuarioDTO dto = servicioUsuario.getUsuario("cliente@mail.com");

        assertNotNull(dto);
        assertEquals("cliente", dto.getTipoUsuario());
        assertEquals("Ana", dto.getNombre());
    }

    @Test
    public void obtenerUsuarioInexistenteDevuelveNull() {
        when(repositorioMock.buscarPorEmail("noexiste@mail.com")).thenReturn(null);

        UsuarioDTO dto = servicioUsuario.getUsuario("noexiste@mail.com");

        assertNull(dto);
    }


    @Test
    public void queSePuedaTraerUnUsuarioConUnToken(){

        String token = UUID.randomUUID().toString();
        UsuarioNutriya usuarioMock = new Cliente();
        usuarioMock.setEmail("test@mail.com");
        usuarioMock.setConfirmado(false);
        usuarioMock.setTokenConfirmacion(token);

        when(repositorioMock.buscarPorTokenConfirmacion(token)).thenReturn(usuarioMock);

        UsuarioDTO resultado = servicioUsuario.buscarPorTokenConfirmacion(token);

        assertEquals("test@mail.com", resultado.getEmail());
        assertFalse(resultado.getConfirmado());
    }

    @Test
    public void queSePuedaConfirmarUnUsuarioConUnToken(){
        String token = UUID.randomUUID().toString();
        UsuarioNutriya usuarioMock = new Cliente();
        usuarioMock.setEmail("test@mail.com");
        usuarioMock.setConfirmado(false);
        usuarioMock.setTokenConfirmacion(token);

        when(repositorioMock.buscarPorTokenConfirmacion(token)).thenReturn(usuarioMock);

        Boolean resultado = servicioUsuario.confirmarUsuarioPorToken(token);

        assertTrue(resultado);
        assertTrue(usuarioMock.getConfirmado());
        assertNull(usuarioMock.getTokenConfirmacion());

        verify(repositorioMock).guardar(usuarioMock);
    }


}
