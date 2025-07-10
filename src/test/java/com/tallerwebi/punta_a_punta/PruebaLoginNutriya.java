package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaLogin;
import com.tallerwebi.punta_a_punta.vistas.VistaWeb;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PruebaLoginNutriya {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaLogin vistaLogin;
    VistaWeb vistaWeb;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(300)
        );
    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        context = browser.newContext();
        Page page = context.newPage();
        vistaLogin = new VistaLogin(page);
        vistaWeb = new VistaWeb(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void deberiaIniciarSesionConEmailYPasswordYRedirigirAlPerfil() {
        vistaLogin.escribirEMAIL("cliente@nutriya.com");
        vistaLogin.escribirClave("$2a$10$T1U4R6S.z0O2z5Q8.2Y.J.Z0J9L.J.J.Z0J9L");
        vistaLogin.darClickEnIniciarSesion();

        vistaLogin.esperarRedireccionAPerfilCliente();

        String textoNav = vistaLogin.obtenerTextoDeLaBarraDeNavegacion();
        assertThat(textoNav, CoreMatchers.containsStringIgnoringCase("NutriYa"));
    }

    @Test
    void deberiaMostrarErrorConCredencialesIncorrectas() {
        vistaLogin.escribirEMAIL("cliente@nutriya.com");
        vistaLogin.escribirClave("clave-invalida");
        vistaLogin.darClickEnIniciarSesion();

        String mensajeError = vistaLogin.obtenerMensajeDeError();
        assertThat(mensajeError, CoreMatchers.containsStringIgnoringCase("incorrectos"));
    }

    @Test
    void deberiaIniciarSesionComoAdminYRedirigirAlDashboard() {
        vistaLogin.escribirEMAIL("admin@nutriya.com");
        vistaLogin.escribirClave("admin");
        vistaLogin.darClickEnIniciarSesion();

        vistaLogin.esperarRedireccion("/admin/dashboard");

        String titulo = vistaLogin.obtenerTextoPorId("#tituloDashboardAdmin");
        assertThat(titulo, CoreMatchers.containsStringIgnoringCase("Dashboard Admin"));
    }

    @Test
    void noDeberiaPermitirAccesoAlDashboardSinLogin() throws MalformedURLException {
        vistaLogin.irA("/admin/dashboard");
        String urlActual = vistaLogin.obtenerURLActual().toString();
        assertThat(urlActual, CoreMatchers.containsString("/nutriya-login"));
    }

    @Test
    void deberiaMostrarBotonCerrarSesionYFuncion() {
        vistaLogin.escribirEMAIL("admin@nutriya.com");
        vistaLogin.escribirClave("admin");
        vistaLogin.darClickEnIniciarSesion();
        vistaLogin.esperarRedireccion("/admin/dashboard");

        assertThat(vistaLogin.obtenerTextoDelElemento("#botonCerrarSesion"), CoreMatchers.containsStringIgnoringCase("Cerrar sesión"));

        vistaLogin.darClickEnElElemento("#botonCerrarSesion");
        vistaLogin.esperarRedireccion("/nutriya-login");
    }

    @Test
    public void queElClientePuedaIniciarSesionYVerMisPedidos() throws MalformedURLException {
        vistaLogin.escribirEMAIL("cliente@nutriya.com");
        vistaLogin.escribirClave("$2a$10$T1U4R6S.z0O2z5Q8.2Y.J.Z0J9L.J.J.Z0J9L");
        vistaLogin.darClickEnIniciarSesion();
        vistaLogin.esperarRedireccionAPerfilCliente();

        vistaWeb.darClickEnElElemento("button[data-bs-toggle='offcanvas']");

        vistaWeb.esperarElementoVisiblePorSelector("a.nav-link[href='/mis-pedidos']");

        vistaWeb.darClickEnElElemento("a.nav-link[href='/mis-pedidos']");

        URL url = vistaWeb.obtenerURLActual();
        assertTrue(url.getPath().contains("/mis-pedidos"));
    }
}