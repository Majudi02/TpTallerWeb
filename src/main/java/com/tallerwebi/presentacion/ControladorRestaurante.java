package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.dominio.ServicioResena;
import com.tallerwebi.dominio.ServicioRestaurante;
import com.tallerwebi.dominio.ServicioRestauranteImpl;
import com.tallerwebi.dominio.entidades.Resena;
import com.tallerwebi.dominio.entidades.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorRestaurante {


    private final ServicioResena servicioResena;
    private ServicioRestaurante servicioRestaurante;


    @Autowired
    public ControladorRestaurante(ServicioRestaurante servicioRestaurante, ServicioResena servicioResena) {
        this.servicioRestaurante = servicioRestaurante;
        this.servicioResena = servicioResena;
    }

    @GetMapping("/restaurantes")
    public String listarRestaurantes(@RequestParam(required = false) String zona,
                                     @RequestParam(required = false) String tipo,
                                     Model model) {
        servicioRestaurante.inicializarDatos();
        List<Restaurante> restaurantes = servicioRestaurante.buscarPorTipoComidaYZona(zona, tipo);
        model.addAttribute("restaurantes", restaurantes);
        model.addAttribute("zona", zona);  // para mantener filtro en la vista
        model.addAttribute("tipo", tipo);  // para mantener filtro en la vista
        return "restaurantes";
    }

    @GetMapping("/restaurantes/{id}")
    public String verDetalleRestaurante(@PathVariable Long id, Model model, HttpServletRequest request) {
        Restaurante restaurante = servicioRestaurante.obtenerRestaurantePorId(id);
        if (restaurante == null) {
            return "redirect:/restaurantes";
        }

        List<PlatoDto> todosLosPlatos = servicioRestaurante.traerTodosLosPlatos();
        List<PlatoDto> platosDelRestaurante = new java.util.ArrayList<>();

        for (PlatoDto plato : todosLosPlatos) {
            if (plato.getIdRestaurante() != null && plato.getIdRestaurante().equals(id)) {
                platosDelRestaurante.add(plato);
            }
        }

        List<Resena> resenas = servicioResena.obtenerUltimasResenas(id, 3);
        model.addAttribute("restaurante", restaurante);
        model.addAttribute("platos", platosDelRestaurante);
        model.addAttribute("resenas", resenas);

        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("tipoUsuario", usuario.getTipoUsuario());
        }

        return "restaurante-detalle";
    }

    @PostMapping("/restaurantes/{id}/resena")
    public String agregarResena(@PathVariable Long id,
                                @RequestParam String comentario,
                                HttpServletRequest request) {
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");

        if (usuario == null || !"cliente".equalsIgnoreCase(usuario.getTipoUsuario())) {
            return "redirect:/nutriya-login";
        }

        servicioResena.guardarResena(id, usuario.getId(), comentario);
        return "redirect:/restaurantes/" + id;
    }

    @GetMapping("/hacer-pedido-platos")
    public ModelAndView mostrarPedidoPlatos() {
        ModelMap modelo = new ModelMap();

        List<PlatoDto> platos = servicioRestaurante.traerTodosLosPlatos();
        modelo.put("platos", platos);

        return new ModelAndView("hacer-pedido-platos", modelo);
    }
}
