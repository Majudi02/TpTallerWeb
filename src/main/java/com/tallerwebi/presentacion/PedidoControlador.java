package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PedidoService;
import com.tallerwebi.dominio.Plato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
    public ModelAndView listarPlatos(@RequestParam(required = false) String ordenar,
                                     @RequestParam(required = false) String tipo) {
        ModelMap modeloMap = new ModelMap();
        List<Plato> platosMostrados;

        if (tipo != null && !tipo.isEmpty()) {
            platosMostrados = pedidoService.buscarPlatosPorTipoComida(tipo);
        } else {
            platosMostrados = pedidoService.traerTodosLosPlatos();
        }

        if (ordenar != null && !ordenar.isEmpty()) {
            platosMostrados = pedidoService.ordenarPlatos(platosMostrados, ordenar);
        }

        modeloMap.addAttribute("platos", platosMostrados);
        return new ModelAndView("hacer-pedido-platos", modeloMap);
    }
}