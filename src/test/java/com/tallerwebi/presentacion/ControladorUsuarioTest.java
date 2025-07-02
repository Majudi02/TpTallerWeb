package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.Cliente;
import com.tallerwebi.dominio.entidades.Direccion;
import com.tallerwebi.dominio.entidades.Restaurante;
import com.tallerwebi.dominio.entidades.UsuarioNutriya;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;


public class ControladorUsuarioTest {

    private ControladorUsuario controlador;
    private ServicioUsuario servicioUsuario;
    private ServicioRestaurante servicioRestaurante;
    private ServicioEmail servicioEmail;
    private RepositorioUsuarioNutriya repositorioUsuario;
    private HttpServletRequest httpServletRequest;
    private RepositorioDireccion repositorioDireccion;
    private RepositorioEtiqueta repositorioEtiqueta;

    @BeforeEach
    public void init() {
        repositorioUsuario = mock(RepositorioUsuarioNutriya.class);
        repositorioDireccion = mock(RepositorioDireccion.class);
        servicioUsuario = new ServicioUsuarioImpl(repositorioUsuario, repositorioDireccion, repositorioEtiqueta);
        servicioRestaurante = mock(ServicioRestaurante.class);
        servicioEmail = mock(ServicioEmail.class);
        controlador = new ControladorUsuario(servicioUsuario, servicioRestaurante, servicioEmail);

        httpServletRequest = mock(HttpServletRequest.class);
        HttpSession httpSession = mock(HttpSession.class);
        when(httpServletRequest.getSession()).thenReturn(httpSession);
    }

    @Test
    public void DadoQueTengoUnControladorUsuarioPuedoRegistrarUnUsuarioTipoCliente() {
        UsuarioDTO cliente = new UsuarioDTO();
        cliente.setTipoUsuario("cliente");
        cliente.setNombre("Ana");
        cliente.setEmail("ana@mail.com");
        cliente.setPassword("1234");
        cliente.setEdad(25);
        cliente.setPesoActual(60);
        cliente.setPesoDeseado(55);
        cliente.setAltura(1.65);
        cliente.setObjetivo("bajar de peso");
        cliente.setCalle("Calle Nombre");
        cliente.setNumero(123);
        cliente.setLocalidad("Localidad Nombre");

        when(repositorioUsuario.buscarPorEmail("ana@mail.com")).thenReturn(null);

        RedirectAttributesModelMap redirect = new RedirectAttributesModelMap();
        ModelAndView resultado = controlador.registrarUsuario(cliente, null, redirect);

        verify(repositorioUsuario).guardar(any(UsuarioNutriya.class));
        verify(repositorioDireccion).guardarDireccion(any());

        assertEquals("confirmacion", resultado.getViewName());
        assertEquals("ana@mail.com", resultado.getModel().get("emailEnviadoA"));
    }


    @Test
    public void DadoQueTengoUnControladorUsuarioMeMuestraElFormularioDeRegistroConUnUsuarioDTOVacio() {
        ModelAndView vista = controlador.mostrarFormularioRegistro();

        assertEquals("nutriya-register", vista.getViewName());
        assertTrue(vista.getModel().containsKey("registroUsuarioDTO"));
    }

    @Test
    public void DadoQueTengoUnControladorUsuarioNoPuedoRegistrarUnUsuarioSiFaltanCampos() {
        UsuarioDTO cliente = new UsuarioDTO();
        cliente.setTipoUsuario("cliente");
        cliente.setEmail("faltanombre@mail.com");

        RedirectAttributesModelMap redirect = new RedirectAttributesModelMap();

        ModelAndView resultado = controlador.registrarUsuario(cliente, null, redirect);

        assertEquals("nutriya-register", resultado.getViewName());
    }

    @Test
    public void DadoQueTengoUnControladorUsuarioMuestraLaVistaLogin() {
        ModelAndView vista = controlador.mostrarFormularioLogin(httpServletRequest);

        assertEquals("nutriya-login", vista.getViewName());
        assertTrue(vista.getModel().containsKey("loginDTO"));
    }


    /*
    @Test
    public void DadoQueTengoUnControladorUsuarioPuedoLogearmeConUnClienteRegistrado() {
        UsuarioDTO cliente = new UsuarioDTO();
        cliente.setEmail("login@mail.com");
        cliente.setPassword("clave");

        Cliente usuarioMock = new Cliente();
        usuarioMock.setEmail("login@mail.com");
        usuarioMock.setPassword("clave");
        usuarioMock.setNombre("Ana");
        usuarioMock.setEdad(30);
        usuarioMock.setPesoActual(70);
        usuarioMock.setPesoDeseado(65);
        usuarioMock.setAltura(1.70);
        usuarioMock.setObjetivo("mantener peso");
        usuarioMock.setConfirmado(true);

        when(repositorioUsuario.buscarPorEmailYPassword("login@mail.com", "clave"))
                .thenReturn(usuarioMock);

        ModelMap model = new ModelMap();
        ModelAndView modelAndView = controlador.procesarLogin(cliente, new RedirectAttributesModelMap(), model, httpServletRequest);

        assertEquals("perfil-cliente", modelAndView.getViewName());
        assertTrue(modelAndView.getModel().containsKey("usuario"));
    }

     */


    @Test
    public void DadoQueTengoUnControladorUsuarioAlIntentarLogearmeConDatosInvalidosDevuelveError() {
        when(repositorioUsuario.buscarPorEmailYPassword("login@mail.com", "claveIncorrecta")).thenReturn(null);

        UsuarioDTO intentoLogin = new UsuarioDTO();
        intentoLogin.setEmail("login@mail.com");
        intentoLogin.setPassword("claveIncorrecta");

        ModelMap model = new ModelMap();
        ModelAndView vista = controlador.procesarLogin(intentoLogin, new RedirectAttributesModelMap(), model, httpServletRequest);

        assertEquals("nutriya-login", vista.getViewName());
        assertTrue(vista.getModel().containsKey("errorLogin"));
    }

    @Test
    public void dadoQueRegistroUnUsuarioRestauranteSeAgregaElRestauranteALaLista() {
        UsuarioDTO restauranteDTO = new UsuarioDTO();
        restauranteDTO.setTipoUsuario("restaurante");
        restauranteDTO.setNombre("Restaurante Test");
        restauranteDTO.setEmail("restaurante@mail.com");
        restauranteDTO.setPassword("1234");
        restauranteDTO.setDescripcion("Muy bueno");
        restauranteDTO.setCalle("Calle Falsa");
        restauranteDTO.setNumero(123);
        restauranteDTO.setLocalidad("Ciudad");
        restauranteDTO.setZona("Zona Centro");
        restauranteDTO.setTipoComidas(List.of("Vegana", "Sin TACC"));

        RedirectAttributesModelMap redirect = new RedirectAttributesModelMap();

        controlador.registrarUsuario(restauranteDTO, null, redirect);

        Restaurante restauranteMock = new Restaurante();
        restauranteMock.setNombre("Restaurante Test");

        when(servicioRestaurante.obtenerRestaurante("Restaurante Test")).thenReturn(restauranteMock);

        Restaurante restaurante = servicioRestaurante.obtenerRestaurante("Restaurante Test");

        assertNotNull(restaurante, "El restaurante debería existir en el servicio");
        assertEquals("Restaurante Test", restaurante.getNombre());
    }

    @Test
    public void DadoQueRegistroClienteConUnaDireccionSeGuardaCorrectamente() {
        UsuarioDTO cliente = new UsuarioDTO();
        cliente.setTipoUsuario("cliente");
        cliente.setNombre("Ana");
        cliente.setEmail("ana@mail.com");
        cliente.setPassword("1234");
        cliente.setEdad(25);
        cliente.setPesoActual(60);
        cliente.setPesoDeseado(55);
        cliente.setAltura(1.65);
        cliente.setObjetivo("bajar de peso");

        cliente.setCalle("Dante Alighieri");
        cliente.setNumero(123);
        cliente.setLocalidad("La Matanza");

        when(repositorioUsuario.buscarPorEmail("ana@mail.com")).thenReturn(null);

        RedirectAttributesModelMap redirect = new RedirectAttributesModelMap();
        ModelAndView resultado = controlador.registrarUsuario(cliente, null, redirect);

        assertEquals("confirmacion", resultado.getViewName());
        assertEquals("ana@mail.com", resultado.getModel().get("emailEnviadoA"));

        verify(repositorioUsuario, atLeastOnce()).guardar(any(Cliente.class));
    }


}


