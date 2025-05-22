package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PedidoControlador {

    private PedidoService pedidoService;

    @Autowired
    public PedidoControlador(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/pedido")
    public ModelAndView irAPedido() {
        ModelMap modeloMap = new ModelMap();
        modeloMap.put("restaurantes", pedidoService.traerRestaurantesDestacados());
        modeloMap.put("platos", pedidoService.traerPlatosDestacados());
        return new ModelAndView("pedido",modeloMap);
    }

    @GetMapping("/pedido/platos")
    public ModelAndView irAHacerUnPedidoPlatos() {
        ModelMap modeloMap = new ModelMap();
        modeloMap.put("platos", pedidoService.traerTodosLosPlatos());
        return new ModelAndView("hacer-pedido-platos",modeloMap);
    }
}