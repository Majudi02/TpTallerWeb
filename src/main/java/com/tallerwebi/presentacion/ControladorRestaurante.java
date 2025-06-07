package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.dominio.entidades.Restaurante;
import com.tallerwebi.dominio.ServicioRestaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorRestaurante {

    @Autowired
    private ServicioRestaurante servicioRestaurante;

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

    @GetMapping("/administrarPlatos")
    public ModelAndView administrarPlatos() {
        ModelMap modelo = new ModelMap();

        PlatoDto platoObtenido = servicioRestaurante.obtenerPlatoPorId(5);

        modelo.put("plato", platoObtenido);

        return new ModelAndView("plato-gestion", modelo);
    }
}



