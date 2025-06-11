package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.dominio.ServicioRestaurante;
import com.tallerwebi.dominio.ServicioRestauranteImpl;
import com.tallerwebi.dominio.entidades.Restaurante;
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


    private ServicioRestaurante servicioRestaurante;


    @Autowired
    public ControladorRestaurante(ServicioRestaurante servicioRestaurante){
        this.servicioRestaurante=servicioRestaurante;
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

    }



