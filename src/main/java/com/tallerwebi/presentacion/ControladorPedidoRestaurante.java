package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPedidoRestaurante;
import com.tallerwebi.dominio.entidades.EstadoPlato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@Controller
public class ControladorPedidoRestaurante {


    private ServicioPedidoRestaurante servicioPedidoRestaurante;



    @Autowired
    public ControladorPedidoRestaurante(ServicioPedidoRestaurante servicioPedidoRestaurante) {
        this.servicioPedidoRestaurante =servicioPedidoRestaurante;
    }


    @RequestMapping(value = "/pedidos/restaurante", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mostrarPedidosDelRestaurante(@RequestParam Long id) {
        ModelMap modelo = new ModelMap();

        Long idRestaurante = servicioPedidoRestaurante.obtenerIdDelRestaurate(id);
        List<PedidoDto> todosLosPedidos = servicioPedidoRestaurante.traerPedidosDelRestaurante(idRestaurante);

        for (PedidoDto pedido : todosLosPedidos) {
            List<PedidoPlatoDto> filtrados = pedido.getPedidoPlatosDelRestaurante(idRestaurante);
            pedido.setPedidoPlatos(filtrados);

            boolean todosFinalizados = filtrados.stream()
                    .allMatch(pp -> pp.getEstadoPlato().equals(EstadoPlato.FINALIZADO));
            pedido.setPlatosFinalizados(todosFinalizados);
        }

        modelo.put("pedidos", todosLosPedidos);

        return new ModelAndView("restaurante-pedido", modelo);
    }

    @PostMapping("/restaurante/finalizar-plato-pedido")
    @ResponseBody
    public ResponseEntity<Void> finalizarPlato(@RequestParam Long pedidoPlatoId) {
        servicioPedidoRestaurante.finalizarPlatoPedido(pedidoPlatoId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/restaurante/finalizar-pedido-completo")
    @ResponseBody
    public ResponseEntity<Void> finalizarPedidoCompleto(@RequestParam Integer pedidoId) {
        servicioPedidoRestaurante.finalizarPedidoCompleto(pedidoId);
        return ResponseEntity.ok().build();
    }

}
