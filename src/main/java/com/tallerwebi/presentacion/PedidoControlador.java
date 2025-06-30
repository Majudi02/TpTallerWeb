package com.tallerwebi.presentacion;

import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.MercadoPagoServiceImpl;
import com.tallerwebi.dominio.PedidoService;
import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.dominio.ServicioRestaurante;
import com.tallerwebi.dominio.entidades.Pedido;
import com.tallerwebi.dominio.entidades.PedidoPlato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PedidoControlador {

    private PedidoService pedidoService;
    private ServicioRestaurante servicioRestaurante;

    @Autowired
    private MercadoPagoServiceImpl mercadoPagoService;


    @Autowired
    public PedidoControlador(PedidoService pedidoService, ServicioRestaurante servicioRestaurante) {
        this.pedidoService = pedidoService;
        this.servicioRestaurante = servicioRestaurante;
    }

    @GetMapping("/pedido")
    public ModelAndView irAPedido() {
        ModelMap modeloMap = new ModelMap();
        modeloMap.put("restaurantes", servicioRestaurante.traerRestaurantesDestacados());
        modeloMap.put("platos", pedidoService.traerPlatosDestacados());

        return new ModelAndView("pedido", modeloMap);
    }

    @GetMapping("/pedido/platos")
    public ModelAndView listarPlatos(@RequestParam(required = false) String ordenar,
                                     @RequestParam(required = false) String tipo, HttpServletRequest request) {
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
        modeloMap.addAttribute("usuario", usuario);
        if (usuario != null) {
            List<PedidoPlatoDto> pedidoActual = pedidoService.mostrarPlatosDelPedidoActual(usuario.getId());
            modeloMap.addAttribute("pedidoActual", pedidoActual);

            Double precioTotalDelPedido = pedidoService.mostrarPrecioTotalDelPedidoActual(usuario.getId());
            modeloMap.addAttribute("precioTotal", precioTotalDelPedido);
        }


        modeloMap.addAttribute("platos", platosMostrados);
        return new ModelAndView("hacer-pedido-platos", modeloMap);
    }

/*
    @PostMapping("/pedido/confirmar")
    public String confirmarPedido(HttpServletRequest request) {
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");
        pedidoService.confirmarPedido(usuario.getId());
        return "redirect:/pedido/platos";
    }
 */

    @PostMapping("/pedido/confirmar")
    public RedirectView confirmarPedido(HttpServletRequest request) {
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");
        try{
            List<PedidoPlatoDto> platosAPagar = pedidoService.mostrarPlatosDelPedidoActual(usuario.getId());

            Preference preference = mercadoPagoService.crearPreferencia(platosAPagar, usuario.getId());

            return new RedirectView(preference.getInitPoint());

        }catch (Exception e){
            return new RedirectView("/pedido?error=pago");
        }
    }

    @GetMapping("/pedido/carrito")
    @ResponseBody
    public List<PedidoPlatoDto> mostrarCarrito(HttpServletRequest request) {
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");
        if (usuario == null) {
            return new java.util.ArrayList<>();
        }
        return pedidoService.mostrarPlatosDelPedidoActual(usuario.getId());
    }

    @PostMapping("/pedido/agregar")
    @ResponseBody
    public void agregarPlatoAlPedido(@RequestParam("platoId") Integer platoId, HttpServletRequest request) {
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");

        PlatoDto platoBuscado = servicioRestaurante.obtenerPlatoPorId(platoId);
        System.out.println("Agregando plato al pedido. Usuario ID: " + usuario.getId() + ", Plato ID: " + platoBuscado.getId());

        pedidoService.agregarPlatoAlPedido(platoBuscado, usuario);
    }

    @GetMapping("/mis-pedidos")
    public String verMisPedidos(HttpServletRequest req, Model model) {
        UsuarioDTO user = (UsuarioDTO) req.getSession().getAttribute("usuario");

        if (user != null) {
            List<PedidoDto> pedidosActivos = pedidoService.listarPedidosActivosPorUsuario(user.getId());
            List<PedidoDto> pedidosEntregados = pedidoService.listarPedidosEntregadosPorUsuario(user.getId());

            model.addAttribute("pedidosActivos", pedidosActivos);
            model.addAttribute("pedidosEntregados", pedidosEntregados);
        } else {
            model.addAttribute("pedidosActivos", new ArrayList<PedidoDto>());
            model.addAttribute("pedidosEntregados", new ArrayList<PedidoDto>());
        }

        return "mis-pedidos";
    }


}