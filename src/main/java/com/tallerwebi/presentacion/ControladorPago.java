package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorPago {

    @GetMapping("/pago-exitoso")
    public String pagoExitoso() {
        return "pago-exitoso";
    }

    @GetMapping("/pago-fallido")
    public String pagoFallido () {
        return "pago-fallido";
    }

    @GetMapping("/pago-pendiente")
    public String pagoPendiente() {
        return "pago-pendiente";
    }
}
