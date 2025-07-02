package com.tallerwebi.presentacion;

import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.MercadoPagoServiceImpl;
import com.tallerwebi.dominio.PedidoService;
import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.dominio.ServicioRestaurante;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ModelAndView irAPedido(HttpServletRequest request, ModelMap modelMap) {
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");
        ModelMap modeloMap = new ModelMap();

        modeloMap.put("usuario", usuario);
        modeloMap.put("restaurantes", servicioRestaurante.traerRestaurantesDestacados());
        if (usuario != null) {
            modeloMap.put("platos", pedidoService.traerPlatosDestacadosPorLaEtiquetaDelCliente(usuario.getId()));
        }

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
        try {
            List<PedidoPlatoDto> platosAPagar = pedidoService.mostrarPlatosDelPedidoActual(usuario.getId());

            Preference preference = mercadoPagoService.crearPreferencia(platosAPagar, usuario.getId());

            return new RedirectView(preference.getInitPoint());

        } catch (Exception e) {
            return new RedirectView("/pedido?error=pago");
        }
    }

    @GetMapping("/pedido/carrito")
    @ResponseBody
    public Map<String, Object> mostrarCarrito(HttpServletRequest request) {
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");
        Map<String, Object> resultado = new HashMap<>();

        if (usuario == null) {
            resultado.put("platos", new ArrayList<>());
            resultado.put("totales", Map.of(
                    "calorias", 0,
                    "proteinas", 0,
                    "carbohidratos", 0,
                    "grasas", 0
            ));
            return resultado;
        }

        List<PedidoPlatoDto> platos = pedidoService.mostrarPlatosDelPedidoActual(usuario.getId());
        resultado.put("platos", platos);

        double totalCalorias = 0;
        double totalProteinas = 0;
        double totalCarbohidratos = 0;
        double totalGrasas = 0;

        for (PedidoPlatoDto pp : platos) {
            totalCalorias += pp.getPlato().getCalorias();
            totalProteinas += pp.getPlato().getProteinas();
            totalCarbohidratos += pp.getPlato().getCarbohidratos();
            totalGrasas += pp.getPlato().getGrasas();
        }

        resultado.put("totales", Map.of(
                "calorias", totalCalorias,
                "proteinas", totalProteinas,
                "carbohidratos", totalCarbohidratos,
                "grasas", totalGrasas
        ));

        return resultado;
    }


    @PostMapping("/pedido/agregar")
    @ResponseBody
    public void agregarPlatoAlPedido(@RequestParam("platoId") Integer platoId, HttpServletRequest request) {
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");

        PlatoDto platoBuscado = servicioRestaurante.obtenerPlatoPorId(platoId);
        System.out.println("Agregando plato al pedido. Usuario ID: " + usuario.getId() + ", Plato ID: " + platoBuscado.getId());

        pedidoService.agregarPlatoAlPedido(platoBuscado, usuario);
    }

    @PostMapping("/pedido/eliminar")
    @ResponseBody
    public void eliminarPlatoDelCarrito(@RequestParam Integer platoId, HttpServletRequest request) {
        UsuarioDTO usuarioLogueado = (UsuarioDTO) request.getSession().getAttribute("usuario");

        if (usuarioLogueado != null) {
            pedidoService.eliminarPlatoDelCarrito(usuarioLogueado.getId(), platoId);
        }
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

    @PostMapping("/calificar")
    @ResponseBody
    public ResponseEntity<String> calificarPlatoAjax(@RequestParam("pedidoPlatoId") Integer pedidoPlatoId,
                                                     @RequestParam("calificacion") Integer calificacion,
                                                     HttpServletRequest request) {

        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");

        pedidoService.guardarCalificacion(pedidoPlatoId, calificacion, usuario.getId());

        return ResponseEntity.ok("Calificación enviada");

    }
    @GetMapping("/pedido/plato")
    public ModelAndView verDetallePlato(@RequestParam("id") Integer platoId,
                                        HttpServletRequest request) {

        PlatoDto plato = servicioRestaurante.obtenerPlatoPorId(platoId);

        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");

        ModelMap model = new ModelMap();
        model.addAttribute("plato", plato);
        model.addAttribute("usuario", usuario);

        return new ModelAndView("detalle-plato", model);
    }

}