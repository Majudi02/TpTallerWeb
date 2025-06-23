package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PedidoService;
import com.tallerwebi.dominio.ServicioPedidoRestaurante;
import com.tallerwebi.dominio.ServicioRestaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorPedidoRestaurante {


    private ServicioPedidoRestaurante servicioPedidoRestaurante;




    @Autowired
    public ControladorPedidoRestaurante(ServicioPedidoRestaurante servicioPedidoRestaurante) {
        this.servicioPedidoRestaurante =servicioPedidoRestaurante;
    }



    @PostMapping("/pedidos/restaurante")
    public ModelAndView mostrarPedidosDelRestaurante(@RequestParam Long id) {
        ModelMap modelo = new ModelMap();



        Long idRestaurante = servicioPedidoRestaurante.obtenerIdDelRestaurate(id);
        List<PedidoDto> todosLosPedidos =servicioPedidoRestaurante.traerPedidosDelRestaurante(idRestaurante);

        modelo.put("idRestaurante", idRestaurante);
        modelo.put("idUsuario", id);
        modelo.put("pedidos", todosLosPedidos);


        return new ModelAndView("restaurante-pedido", modelo);
    }

}
