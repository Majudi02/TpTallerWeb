package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaLogin;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;


public class PruebaLoginNutriya {


    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaLogin vistaLogin;

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
}

