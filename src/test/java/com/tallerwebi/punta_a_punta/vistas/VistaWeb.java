package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class VistaWeb {
    protected Page page;
    private String baseUrl = "http://localhost:8080/";

    public VistaWeb(Page page) {
        this.page = page;
    }

    public URL obtenerURLActual() throws MalformedURLException {
        URL url = new URL(page.url());
        return url;
    }

    public String obtenerTextoDelElemento(String selectorCSS){
        return this.obtenerElemento(selectorCSS).textContent();
    }

    public void darClickEnElElemento(String selectorCSS){
        this.obtenerElemento(selectorCSS).click();
    }

    protected void escribirEnElElemento(String selectorCSS, String texto){
        this.obtenerElemento(selectorCSS).type(texto);
    }

    private Locator obtenerElemento(String selectorCSS){
        return page.locator(selectorCSS);
    }

    public void irA(String rutaRelativa) {
        String urlCompleta;
        if (rutaRelativa.startsWith("http://") || rutaRelativa.startsWith("https://")) {
            urlCompleta = rutaRelativa;
        } else {
            urlCompleta = baseUrl + rutaRelativa;
        }
        page.navigate(urlCompleta);
    }

    public void esperarElementoVisiblePorSelector(String selectorCSS) {
        this.obtenerElemento(selectorCSS).waitFor();
    }

}