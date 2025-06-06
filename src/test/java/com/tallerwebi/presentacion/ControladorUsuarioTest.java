package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.presentacion.ControladorUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class ControladorUsuarioTest {

    private ControladorUsuario controlador;
    private ServicioUsuario servicioUsuario;
    private ServicioRestaurante servicioRestaurante;
    private RepositorioUsuarioNutriya repositorioUsuario;

    @BeforeEach
    public void init() {
        repositorioUsuario = mock(RepositorioUsuarioNutriya.class); // <-- simulamos el repo
        servicioUsuario = new ServicioUsuarioImpl(repositorioUsuario);
        servicioRestaurante = mock(ServicioRestaurante.class);
        controlador = new ControladorUsuario(servicioUsuario, servicioRestaurante);
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

        // Simulamos que no existe previamente
        when(repositorioUsuario.buscarPorEmail("ana@mail.com")).thenReturn(null);

        RedirectAttributesModelMap redirect = new RedirectAttributesModelMap();
        String resultado = controlador.registrarUsuario(cliente, null, redirect);

        // Verificamos que se haya guardado
        verify(repositorioUsuario).guardar(any(Cliente.class));

        assertEquals("redirect:/resultado-registro", resultado);
    }


    @Test
    public void DadoQueTengoUnControladorUsuaruioMeMuestraElFormularioDeRegistroConUnUsuarioDTOVacio() {
        Model model = new ConcurrentModel();

        String vista = controlador.mostrarFormularioRegistro(model);

        assertEquals("nutriya-register", vista);
        assertTrue(model.containsAttribute("registroUsuarioDTO"));
    }

    @Test
    public void DadoQueTengoUnControladorUsuaruioNoPuedoRegistrarUnUsuarioSiFaltanCampos() {
        UsuarioDTO cliente = new UsuarioDTO();
        cliente.setTipoUsuario("cliente");
        cliente.setEmail("faltanombre@mail.com");

        RedirectAttributesModelMap redirect = new RedirectAttributesModelMap();

        String resultado = controlador.registrarUsuario(cliente, null, redirect);

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
        cliente.setNombre("Ana");
        cliente.setEdad(30);
        cliente.setPesoActual(70);
        cliente.setPesoDeseado(65);
        cliente.setAltura(1.70);
        cliente.setObjetivo("mantener peso");

        // Simulamos que el usuario ya existe cuando se busca por email y password
        Cliente usuarioMock = new Cliente();
        usuarioMock.setEmail("login@mail.com");
        usuarioMock.setPassword("clave");
        usuarioMock.setNombre("Ana");
        usuarioMock.setEdad(30);
        usuarioMock.setPesoActual(70);
        usuarioMock.setPesoDeseado(65);
        usuarioMock.setAltura(1.70);
        usuarioMock.setObjetivo("mantener peso");

        when(repositorioUsuario.buscarPorEmailYPassword("login@mail.com", "clave"))
                .thenReturn(usuarioMock);

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

        controlador.registrarUsuario(restauranteDTO, null, redirect);

        Restaurante restauranteMock = new Restaurante();
        restauranteMock.setNombre("Restaurante Test");

        when(servicioRestaurante.obtenerRestaurante("Restaurante Test")).thenReturn(restauranteMock);

        Restaurante restaurante = servicioRestaurante.obtenerRestaurante("Restaurante Test");

        assertNotNull(restaurante, "El restaurante debería existir en el servicio");
        assertEquals("Restaurante Test", restaurante.getNombre());
    }

}
