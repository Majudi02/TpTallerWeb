package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ControladorPago {

    private PedidoService pedidoService;

    @Autowired
    public ControladorPago(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/pago-exitoso")
    public String pagoExitoso(@RequestParam("idUsuario") Long idUsuario) {
        pedidoService.confirmarPedido(idUsuario);
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
