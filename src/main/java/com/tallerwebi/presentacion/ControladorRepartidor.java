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
        return "vista-pedidos-retirar"; // el nombre del archivo Thymeleaf o JSP
    }

    @GetMapping("/pedido/entregar/{id}")
    public String mostrarDetallePedido(@PathVariable("id") Integer id, Model model) {
        // Obtener pedido con todos los datos para mostrar (podés usar un método en servicio)
        PedidoVistaDto pedido = servicioPedidoRestaurante.traerDetallePedidoPorId(id);

        model.addAttribute("pedido", pedido);
        return "detalle-pedido-entrega"; // nombre de la nueva vista
    }

    @PostMapping("/pedido/entregar/{id}")
    public String finalizarEntregaPedido(@PathVariable("id") Integer id) {
        servicioPedidoRestaurante.entregarPedido(id);  // Cambia el estado a ENTREGADO
        return "redirect:/pedidos-retirar";  // Redirige a la lista de pedidos
    }


}
