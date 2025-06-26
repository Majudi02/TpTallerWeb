package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPedidoRestaurante;
import com.tallerwebi.dominio.entidades.EstadoPedido;
import com.tallerwebi.dominio.entidades.EstadoPlato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorPedidoRestaurante {
    private ServicioPedidoRestaurante servicioPedidoRestaurante;

    @Autowired
    public ControladorPedidoRestaurante(ServicioPedidoRestaurante servicioPedidoRestaurante) {
        this.servicioPedidoRestaurante = servicioPedidoRestaurante;
    }

    @RequestMapping(value = "/pedidos/restaurante", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView mostrarPedidosDelRestaurante(@RequestParam Long id) {
        ModelMap modelo = new ModelMap();

        Long idRestaurante = servicioPedidoRestaurante.obtenerIdDelRestaurate(id);
        List<PedidoDto> todosLosPedidos = servicioPedidoRestaurante.traerPedidosDelRestaurante(idRestaurante);

        List<PedidoDto> pedidosEnPreparacion = new ArrayList<>();
        List<PedidoDto> pedidosListosParaEnviar = new ArrayList<>();

        for (PedidoDto pedido : todosLosPedidos) {
            List<PedidoPlatoDto> filtrados = pedido.getPedidoPlatosDelRestaurante(idRestaurante);
            pedido.setPedidoPlatos(filtrados);

            if (pedido.getEstadoPedido() == EstadoPedido.LISTO_PARA_ENVIAR) {
                pedidosListosParaEnviar.add(pedido);
            } else {
                pedidosEnPreparacion.add(pedido);
            }
        }

        modelo.put("pedidosEnPreparacion", pedidosEnPreparacion);
        modelo.put("pedidosListosParaEnviar", pedidosListosParaEnviar);

        return new ModelAndView("restaurante-pedido", modelo);
    }


    @GetMapping("/restaurante/pedidos-listos-json")
    @ResponseBody
    public List<PedidoDto> obtenerPedidosListosJson(@RequestParam Long id) {
        Long idRestaurante = servicioPedidoRestaurante.obtenerIdDelRestaurate(id);
        return servicioPedidoRestaurante.traerPedidosDelRestaurante(idRestaurante);
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
        servicioPedidoRestaurante.confirmarPedidoListoParaEnviar(pedidoId);
        return ResponseEntity.ok().build();
    }
}
