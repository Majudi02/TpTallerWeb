package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.entidades.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;

import javax.servlet.http.HttpSession;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControladorEdicionUsuarioTest {

    private ServicioUsuario servicioUsuarioMock;
    private ControladorEdicionUsuario controlador;
    private Model modeloMock;
    private MockHttpServletRequest request;

    @BeforeEach
    public void setup() {
        servicioUsuarioMock = mock(ServicioUsuario.class);
        controlador = new ControladorEdicionUsuario(servicioUsuarioMock);
        modeloMock = mock(Model.class);
        request = new MockHttpServletRequest();
        request.setSession(new MockHttpServletRequest().getSession());
    }

    @Test
    public void redirigeAlLoginSiNoHayUsuarioEnLaSesion() {
        String vista = controlador.mostrarFormularioEdicion(request, modeloMock);
        assertEquals("redirect:/nutriya-login", vista);
    }

    @Test
    public void muestraFormularioConDatosDelUsuario() {
        Cliente cliente = new Cliente();
        cliente.setEmail("cliente@mail.com");

        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail("cliente@mail.com");
        dto.setTipoUsuario("cliente");

        HttpSession session = request.getSession();
        session.setAttribute("usuario", dto);

        // Simula que devuelve el mismo usuariodto
        when(servicioUsuarioMock.getUsuario("cliente@mail.com")).thenReturn(dto);

        String vista = controlador.mostrarFormularioEdicion(request, modeloMock);

        assertEquals("editar-usuario", vista);

        // Verifica si se llamó el metodo de añadir atributo al modelo
        verify(modeloMock).addAttribute("usuarioDTO", dto);
    }

    @Test
    public void redirigeSiElUsuarioEditadoNoCoincideConElDeSesion() {
        UsuarioDTO usuarioSesion = new UsuarioDTO();
        usuarioSesion.setId(1L);

        UsuarioDTO usuarioEditado = new UsuarioDTO();
        usuarioEditado.setId(2L); // diferente

        request.getSession().setAttribute("usuario", usuarioSesion);

        String vista = controlador.procesarEdicion(usuarioEditado, null, request, modeloMock);
        assertEquals("redirect:/nutriya", vista);
    }

    @Test
    public void editarUnClienteRedirigeAlPerfilCliente() {
        UsuarioDTO usuarioSesion = new UsuarioDTO();
        usuarioSesion.setId(1L);
        usuarioSesion.setEmail("cliente@mail.com");
        usuarioSesion.setTipoUsuario("cliente");

        UsuarioDTO usuarioEditado = new UsuarioDTO();
        usuarioEditado.setId(1L);
        usuarioEditado.setEmail("cliente@mail.com");
        usuarioEditado.setTipoUsuario("cliente");

        request.getSession().setAttribute("usuario", usuarioSesion);
        when(servicioUsuarioMock.getUsuario("cliente@mail.com")).thenReturn(usuarioEditado);

        String vista = controlador.procesarEdicion(usuarioEditado, null, request, modeloMock);

        verify(servicioUsuarioMock).editarUsuario(usuarioEditado);
        verify(servicioUsuarioMock).getUsuario("cliente@mail.com");
        assertEquals("redirect:/perfil-cliente", vista);
    }

    @Test
    public void PuedoEditarUnRestauranteConImagenNueva() throws Exception {
        // Simulo al usuario logueado en sesiom
        UsuarioDTO usuarioSesion = new UsuarioDTO();
        usuarioSesion.setId(1L);
        usuarioSesion.setEmail("restaurante@mail.com");
        usuarioSesion.setTipoUsuario("restaurante");
        usuarioSesion.setImagen("/img/previa.png");

        // Simulo los datos enviados por el form
        UsuarioDTO usuarioEditado = new UsuarioDTO();
        usuarioEditado.setId(1L);
        usuarioEditado.setEmail("restaurante@mail.com");
        usuarioEditado.setTipoUsuario("restaurante");

        // Guardo el usuario logueado en la sesion del request
        request.getSession().setAttribute("usuario", usuarioSesion);

        // Simulo que el servicio devuelve al usuario editado
        when(servicioUsuarioMock.getUsuario("restaurante@mail.com")).thenReturn(usuarioEditado);

        // Simulo una imagen enviada desde el formulario
        MockMultipartFile imagen = new MockMultipartFile(
                "imagenRestaurante",
                "foto.png",
                "image/png",
                "contenido de imagen".getBytes(StandardCharsets.UTF_8)
        );

        // Llamo al metodo que procesa la edicion y guarda
        String vista = controlador.procesarEdicion(usuarioEditado, imagen, request, modeloMock);

        // Verifico que se haya llamado al servicio para editar el usuario
        verify(servicioUsuarioMock).editarUsuario(any());

        // Verifico que se redirige al perfil del restaurantre
        assertEquals("redirect:/perfil-restaurante", vista);
    }

    @Test
    public void editarUnClienteConDireccionesDeberiaLlamarAlServicioUsuario() {
        // Simulo usuario en sesion
        UsuarioDTO usuarioSesion = new UsuarioDTO();
        usuarioSesion.setId(1L);
        usuarioSesion.setEmail("damian@test.com");
        usuarioSesion.setTipoUsuario("cliente");

        // Simular edic de direcc
        UsuarioDTO usuarioEditado = new UsuarioDTO();
        usuarioEditado.setId(1L);
        usuarioEditado.setEmail("damian@test.com");
        usuarioEditado.setTipoUsuario("cliente");

        // Agregamos una direccion nueva
        DireccionDto direccion1 = new DireccionDto();
        direccion1.setId(null);
        direccion1.setCalle("Calle Nombre");
        direccion1.setNumero(123);
        direccion1.setLocalidad("Localidad Nombre");

        usuarioEditado.setDirecciones(List.of(direccion1));

        request.getSession().setAttribute("usuario", usuarioSesion);

        when(servicioUsuarioMock.getUsuario("damian@test.com")).thenReturn(usuarioEditado);

        String vista = controlador.procesarEdicion(usuarioEditado, null, request, modeloMock);

        verify(servicioUsuarioMock).editarUsuario(usuarioEditado);
        assertEquals("redirect:/cliente/perfil", vista);
    }

}
