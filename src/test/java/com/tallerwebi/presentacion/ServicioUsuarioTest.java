package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuarioImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServicioUsuarioTest {

    private ServicioUsuarioImpl servicio;

    @BeforeEach
    public void setup() {
        servicio = new ServicioUsuarioImpl();
    }

    @Test
    public void SePuedeRegistrarUnUsuarioNuevo() {
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setEmail("test@mail.com");
        usuario.setPassword("1234");

        servicio.registrarUsuario(usuario);

        UsuarioDTO encontrado = servicio.getUsuario("test@mail.com");
        assertNotNull(encontrado);
        assertEquals("test@mail.com", encontrado.getEmail());
    }

    @Test
    public void RegistrarUsuarioConEmailRepetidoLanzaUnaExcepcion() {
        UsuarioDTO usuario1 = new UsuarioDTO();
        usuario1.setEmail("test@mail.com");
        usuario1.setPassword("1234");

        UsuarioDTO usuario2 = new UsuarioDTO();
        usuario2.setEmail("test@mail.com");
        usuario2.setPassword("4321");

        servicio.registrarUsuario(usuario1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            servicio.registrarUsuario(usuario2);
        });
        assertEquals("Email ya registrado", exception.getMessage());
    }

    @Test
    public void ObtenerUsuariosDevuelveUnaListaConLosUsuarios() {
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setEmail("test@mail.com");
        usuario.setPassword("pass");

        servicio.registrarUsuario(usuario);

        List<UsuarioDTO> usuarios = servicio.getUsuarios();
        assertEquals(1, usuarios.size());
        assertEquals("test@mail.com", usuarios.get(0).getEmail());
    }

    @Test
    public void ObtenerUsuarioPorEmailMeDevuelveElUsuario() {
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setEmail("test@mail.com");
        usuario.setPassword("pass");

        servicio.registrarUsuario(usuario);

        UsuarioDTO encontrado = servicio.getUsuario("test@mail.com");
        assertNotNull(encontrado);
        assertEquals("test@mail.com", encontrado.getEmail());
    }

    @Test
    public void ObtenerUnUsuarioQueNoExisteMeDevuelveUnUsuarioNull() {
        UsuarioDTO encontrado = servicio.getUsuario("test@mail.com");
        assertNull(encontrado);
    }

    @Test
    public void ValidarUsuarioCorrectoDevuelveElUsuario() {
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setEmail("validar@mail.com");
        usuario.setPassword("pass123");

        servicio.registrarUsuario(usuario);

        UsuarioDTO validado = servicio.validarUsuario("validar@mail.com", "pass123");
        assertNotNull(validado);
        assertEquals("validar@mail.com", validado.getEmail());
    }

    @Test
    public void ValidarUsuarioIncorrectoDevuelveNull() {
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setEmail("validar@mail.com");
        usuario.setPassword("pass123");

        servicio.registrarUsuario(usuario);

        UsuarioDTO validado = servicio.validarUsuario("validar@mail.com", "passIncorrecta");
        assertNull(validado);
    }
}
