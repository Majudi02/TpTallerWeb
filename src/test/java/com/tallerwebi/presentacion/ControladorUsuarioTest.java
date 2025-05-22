package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.presentacion.ControladorUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ControladorUsuarioTest {

    private ControladorUsuario controlador;
    private ServicioUsuario servicioUsuario;
    private ServicioRestaurante servicioRestaurante;

    @BeforeEach
    public void init() {
        servicioUsuario = new ServicioUsuarioImpl();
        servicioRestaurante = new ServicioRestauranteImpl();
        controlador = new ControladorUsuario(servicioUsuario, servicioRestaurante);

    }

    @Test
    public void DadoQueTengoUnControladorUsuaruioMeMuestraElFormularioDeRegistroConUnUsuarioDTOVacio() {
        Model model = new ConcurrentModel();

        String vista = controlador.mostrarFormularioRegistro(model);

        assertEquals("nutriya-register", vista);
        assertTrue(model.containsAttribute("registroUsuarioDTO"));
    }

    @Test
    public void DadoQueTengoUnControladorUsuaruioPuedoRegistrarUnUsuarioTipoCliente() {
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

        RedirectAttributesModelMap redirect = new RedirectAttributesModelMap();

        String resultado = controlador.registrarUsuario(cliente, redirect);

        assertEquals("redirect:/resultado-registro", resultado);
        assertNotNull(servicioUsuario.getUsuario("ana@mail.com"));
    }

    @Test
    public void DadoQueTengoUnControladorUsuaruioNoPuedoRegistrarUnUsuarioSiFaltanCampos() {
        UsuarioDTO cliente = new UsuarioDTO();
        cliente.setTipoUsuario("cliente");
        cliente.setEmail("faltanombre@mail.com");

        RedirectAttributesModelMap redirect = new RedirectAttributesModelMap();

        String resultado = controlador.registrarUsuario(cliente, redirect);

        assertEquals("nutriya-register", resultado);
    }

    @Test
    public void DadoQueTengoUnControladorUsuarioMuestraLaVistaLogin() {
        Model model = new ConcurrentModel();

        String vista = controlador.mostrarFormularioLogin(model);

        assertEquals("nutriya-login", vista);
        assertTrue(model.containsAttribute("loginDTO"));
    }

    @Test
    public void DadoQueTengoUnControladorUsuarioPuedoLogearmeConUnClienteRegistrado() {
        UsuarioDTO cliente = new UsuarioDTO();
        cliente.setTipoUsuario("cliente");
        cliente.setEmail("login@mail.com");
        cliente.setPassword("clave");

        servicioUsuario.registrarUsuario(cliente);

        Model model = new ConcurrentModel();

        UsuarioDTO intentoLogin = new UsuarioDTO();
        intentoLogin.setEmail("login@mail.com");
        intentoLogin.setPassword("clave");

        String vista = controlador.procesarLogin(intentoLogin, model);

        assertEquals("perfil-cliente", vista);
        assertTrue(model.containsAttribute("usuario"));
    }

    @Test
    public void DadoQueTengoUnControladorUsuarioAlIntentarLogearmeConDatosInvalidosDevuelveError() {
        UsuarioDTO cliente = new UsuarioDTO();
        cliente.setTipoUsuario("cliente");
        cliente.setEmail("login@mail.com");
        cliente.setPassword("clave");

        servicioUsuario.registrarUsuario(cliente);

        UsuarioDTO intentoLogin = new UsuarioDTO();
        intentoLogin.setEmail("login@mail.com");
        intentoLogin.setPassword("claveIncorrecta");

        Model model = new ConcurrentModel();

        String vista = controlador.procesarLogin(intentoLogin, model);

        assertEquals("nutriya-login", vista);
        assertTrue(model.containsAttribute("errorLogin"));
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

        controlador.registrarUsuario(restauranteDTO, redirect);

        Restaurante restaurante = servicioRestaurante.obtenerRestaurante("Restaurante Test");

        assertNotNull(restaurante, "El restaurante debería existir en el servicio");
        assertEquals("Restaurante Test", restaurante.getNombre());
    }

}
