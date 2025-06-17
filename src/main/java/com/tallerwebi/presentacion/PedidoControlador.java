package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PedidoService;
import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.dominio.ServicioRestaurante;
import com.tallerwebi.dominio.entidades.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PedidoControlador {

    private PedidoService pedidoService;
    private ServicioRestaurante servicioRestaurante;


    @Autowired
    public PedidoControlador(PedidoService pedidoService,ServicioRestaurante servicioRestaurante) {
        this.pedidoService = pedidoService;
        this.servicioRestaurante =servicioRestaurante;
    }

    @GetMapping("/pedido")
    public ModelAndView irAPedido() {
        ModelMap modeloMap = new ModelMap();
        modeloMap.put("restaurantes", servicioRestaurante.traerRestaurantesDestacados());
        modeloMap.put("platos", pedidoService.traerPlatosDestacados());

        return new ModelAndView("pedido",modeloMap);
    }

    @GetMapping("/pedido/platos")
    public ModelAndView listarPlatos(@RequestParam(required = false) String ordenar,
                                     @RequestParam(required = false) String tipo,HttpServletRequest request) {
        ModelMap modeloMap = new ModelMap();
        List<PlatoDto> platosMostrados;
        System.out.println("Tipo recibido: " + tipo);


        if (tipo != null && !tipo.isEmpty()) {
            platosMostrados = pedidoService.buscarPlatosPorTipoComida(tipo);
        } else {
            platosMostrados = pedidoService.traerTodosLosPlatos();
        }

        if (ordenar != null && !ordenar.isEmpty()) {
            platosMostrados = pedidoService.ordenarPlatos(platosMostrados, ordenar);
        }


        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");

        List<PlatoDto> pedidoActual = pedidoService.mostrarPlatosDelPedidoActual(usuario.getId());
        modeloMap.addAttribute("pedidoActual", pedidoActual);


        Double precioTotalDelPedido = pedidoService.mostrarPrecioTotalDelPedidoActual(usuario.getId());
        modeloMap.addAttribute("precioTotal", precioTotalDelPedido);

        modeloMap.addAttribute("platos", platosMostrados);
        return new ModelAndView("hacer-pedido-platos", modeloMap);
    }



    @PostMapping("/pedido/confirmar")
    public String confirmarPedidoPorGet(HttpServletRequest request){
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");
        this.pedidoService.finalizarPedido(usuario.getId());
        return "redirect:/pedido/platos";
    }

    @PostMapping("/pedido/agregar")
    public String agregarPlatoAlPedido(@RequestParam("platoId")Integer platoId,
                                       HttpServletRequest request){

        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");

        PlatoDto platoBuscado= servicioRestaurante.obtenerPlatoPorId(platoId);
        System.out.println(platoBuscado.getNombre());
        System.out.println(platoBuscado.getDescripcion());
        pedidoService.agregarPlatoAlPedido(platoBuscado,usuario);

        return "redirect:/pedido/platos";
    }




}