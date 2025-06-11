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
import org.springframework.web.bind.annotation.PathVariable;
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
            List<Restaurante> restaurantes = servicioRestaurante.buscarPorTipoComidaYZona(zona, tipo);
            model.addAttribute("restaurantes", restaurantes);
            model.addAttribute("zona", zona);  // para mantener filtro en la vista
            model.addAttribute("tipo", tipo);  // para mantener filtro en la vista
            return "restaurantes";
        }

        @GetMapping("/restaurantes/{nombre}")
        public String verDetalleRestaurante(@PathVariable String nombre, Model model) {
            Restaurante restaurante = servicioRestaurante.obtenerRestaurante(nombre);
            if (restaurante == null) {
                return "redirect:/restaurantes";
            }

            // Obtener todos los platos y filtrar los que corresponden a este restaurante
            // Como no hay una relación directa entre platos y restaurantes,
            // asumimos que los platos tienen alguna relación con el restaurante por nombre o descripción
            List<PlatoDto> todosLosPlatos = servicioRestaurante.traerTodosLosPlatos();
            List<PlatoDto> platosDelRestaurante = new java.util.ArrayList<>();

            // Filtrar platos que podrían pertenecer a este restaurante
            // Esta es una implementación temporal hasta que se establezca una relación formal
            for (PlatoDto plato : todosLosPlatos) {
                // Verificar si el plato podría pertenecer a este restaurante
                // basado en alguna coincidencia en el nombre o descripción
                if (plato.getNombre().toLowerCase().contains(restaurante.getNombre().toLowerCase()) ||
                    (plato.getDescripcion() != null && plato.getDescripcion().toLowerCase().contains(restaurante.getNombre().toLowerCase()))) {
                    platosDelRestaurante.add(plato);
                }
            }

            model.addAttribute("restaurante", restaurante);
            model.addAttribute("platos", platosDelRestaurante);

            return "restaurante-detalle";
        }

    }
