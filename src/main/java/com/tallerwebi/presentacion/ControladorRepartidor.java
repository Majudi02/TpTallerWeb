package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PedidoService;
import com.tallerwebi.dominio.PedidoVistaDto;
import com.tallerwebi.dominio.ServicioPedidoRestaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorRepartidor {

    private final ServicioPedidoRestaurante servicioPedidoRestaurante;
    private final PedidoService pedidoService;

    @Autowired
    public ControladorRepartidor(ServicioPedidoRestaurante servicioPedidoRestaurante,PedidoService pedidoService) {
        this.servicioPedidoRestaurante = servicioPedidoRestaurante;
        this.pedidoService=pedidoService;
    }

    @GetMapping("/pedidos-retirar")
    public String mostrarPedidosListos(Model model) {
        List<PedidoVistaDto> pedidos = servicioPedidoRestaurante.traerPedidosListosParaVista();
        model.addAttribute("pedidos", pedidos);
        return "vista-pedidos-retirar";
    }

    @GetMapping("/pedido/entregar/{id}")
    public String mostrarDetallePedido(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
        PedidoVistaDto pedido = servicioPedidoRestaurante.traerDetallePedidoPorId(id);
        model.addAttribute("pedido", pedido);

        UsuarioDTO repartidor = (UsuarioDTO) request.getSession().getAttribute("usuario");

        if (repartidor != null && repartidor.getTipoUsuario().equals("repartidor")) {
            pedidoService.asignarRepartidorAPedido(id, repartidor.getId());
            model.addAttribute("repartidorId", repartidor.getId());
            model.addAttribute("clienteId", pedido.getClienteId());
            model.addAttribute("pedidoId", pedido.getPedidoId());
        } else {
            model.addAttribute("repartidorId", null);
        }

        model.addAttribute("clienteId", pedido.getClienteId());

        return "detalle-pedido-entrega";
    }
    @PostMapping("/pedido/entregar/{id}")
    public String finalizarEntregaPedido(@PathVariable("id") Integer id) {
        servicioPedidoRestaurante.entregarPedido(id);
        return "redirect:/pedidos-retirar";
    }


}
