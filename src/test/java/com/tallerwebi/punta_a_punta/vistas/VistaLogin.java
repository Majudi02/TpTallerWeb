package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;


public class VistaLogin extends VistaWeb {

    public VistaLogin(Page page) {
        super(page);
        page.navigate("http://localhost:8080/nutriya-login");
    }

    public void escribirEMAIL(String email) {
        this.escribirEnElElemento("#inputEmail", email);
    }

    public void escribirClave(String clave) {
        this.escribirEnElElemento("#inputPassword", clave);
    }

    public void darClickEnIniciarSesion() {
        this.darClickEnElElemento("button.btn-green");
    }

    public void esperarRedireccionAPerfilCliente() {
        this.page.waitForURL("**/cliente/perfil", new Page.WaitForURLOptions().setTimeout(10000));
    }

    public void esperarRedireccion(String pathParcial) {
        page.waitForURL(urlString -> urlString.contains(pathParcial));
    }

    public String obtenerTextoDeLaBarraDeNavegacion() {
        return this.obtenerTextoDelElemento("nav a.navbar-brand");
    }

    public String obtenerTextoPorId(String selectorCSS) {
        return this.obtenerTextoDelElemento(selectorCSS);
    }

    public String obtenerMensajeDeError() {
        return this.obtenerTextoDelElemento("p.alert.alert-danger, div.alert.alert-danger");
    }


}