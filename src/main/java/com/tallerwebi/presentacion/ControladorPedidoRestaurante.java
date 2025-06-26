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

        // id aquí es el id del usuario-restaurante (no del restaurante)
        System.out.println("Id usuario-restaurante recibido: " + id);

        Long idRestaurante = servicioPedidoRestaurante.obtenerIdDelRestaurate(id);
        System.out.println("Id restaurante obtenido a partir del usuario: " + idRestaurante);

        List<PedidoDto> todosLosPedidos = servicioPedidoRestaurante.traerPedidosDelRestaurante(idRestaurante);
        System.out.println("Cantidad de pedidos obtenidos para el restaurante: " + todosLosPedidos.size());

        List<PedidoDto> pedidosEnPreparacion = new ArrayList<>();
        List<PedidoDto> pedidosListosParaEnviar = new ArrayList<>();

        for (PedidoDto pedido : todosLosPedidos) {
            // Filtrar los platos del pedido para que sean sólo del restaurante
            List<PedidoPlatoDto> filtrados = pedido.getPedidoPlatosDelRestaurante(idRestaurante);
            System.out.println("Pedido id " + pedido.getId() + " tiene " + filtrados.size() + " platos del restaurante");
            pedido.setPedidoPlatos(filtrados);

            // Clasificar pedidos por estado
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


    @PostMapping("/restaurante/finalizar-plato-pedido")
    @ResponseBody
    public ResponseEntity<Void> finalizarPlato(@RequestParam Long pedidoPlatoId) {
        servicioPedidoRestaurante.finalizarPlatoPedido(pedidoPlatoId);
        return ResponseEntity.ok().build();
    }
}
