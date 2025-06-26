package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PedidosRestauranteDto;
import com.tallerwebi.dominio.ServicioPedidoRestaurante;
import com.tallerwebi.dominio.entidades.EstadoPedido;
import com.tallerwebi.dominio.entidades.EstadoPlato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorPedidoRestaurante {
    private ServicioPedidoRestaurante servicioPedidoRestaurante;

    @Autowired
    public ControladorPedidoRestaurante(ServicioPedidoRestaurante servicioPedidoRestaurante) {
        this.servicioPedidoRestaurante = servicioPedidoRestaurante;
    }

    @GetMapping("/pedidos/restaurante")
    public ModelAndView mostrarPedidosDelRestaurante(HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");

        if (usuario == null || !"restaurante".equals(usuario.getTipoUsuario())) {
            return new ModelAndView("redirect:/nutriya-login");
        }

        Long idRestaurante = servicioPedidoRestaurante.obtenerIdDelRestaurate(usuario.getId());
        PedidosRestauranteDto pedidos = servicioPedidoRestaurante.obtenerPedidosClasificados(idRestaurante);

        ModelMap modelo = new ModelMap();
        modelo.put("pedidosEnPreparacion", pedidos.getEnPreparacion());
        modelo.put("pedidosListosParaEnviar", pedidos.getListosParaEnviar());
        modelo.put("pedidosEntregados", pedidos.getEntregados());

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
