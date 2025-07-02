package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PedidoVistaDto;
import com.tallerwebi.dominio.ServicioPedidoRestaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ControladorRepartidor {

    private final ServicioPedidoRestaurante servicioPedidoRestaurante;

    @Autowired
    public ControladorRepartidor(ServicioPedidoRestaurante servicioPedidoRestaurante) {
        this.servicioPedidoRestaurante = servicioPedidoRestaurante;
    }

    @GetMapping("/pedidos-retirar")
    public String mostrarPedidosListos(Model model) {
        List<PedidoVistaDto> pedidos = servicioPedidoRestaurante.traerPedidosListosParaVista();
        model.addAttribute("pedidos", pedidos);
        return "vista-pedidos-retirar";
    }

    @GetMapping("/pedido/entregar/{id}")
    public String mostrarDetallePedido(@PathVariable("id") Integer id, Model model) {

        PedidoVistaDto pedido = servicioPedidoRestaurante.traerDetallePedidoPorId(id);

        model.addAttribute("pedido", pedido);
        return "detalle-pedido-entrega";
    }

    @PostMapping("/pedido/entregar/{id}")
    public String finalizarEntregaPedido(@PathVariable("id") Integer id) {
        servicioPedidoRestaurante.entregarPedido(id);
        return "redirect:/pedidos-retirar";
    }


}
