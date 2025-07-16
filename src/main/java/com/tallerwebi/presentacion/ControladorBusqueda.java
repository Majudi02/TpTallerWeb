package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.BusquedaResultadoDto;
import com.tallerwebi.dominio.ServicioBusqueda;
import com.tallerwebi.dominio.SugerenciaDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorBusqueda {

    private ServicioBusqueda servicioBusqueda;

    public ControladorBusqueda(ServicioBusqueda servicioBusqueda) {
        this.servicioBusqueda = servicioBusqueda;
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam(name = "busqueda", required = false) String texto, HttpServletRequest request, Model model) {
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");

        if (texto == null || texto.trim().isEmpty()) {
            model.addAttribute("mensaje", "Por favor ingresa un texto para buscar.");
            model.addAttribute("resultado", null);
        } else {
            BusquedaResultadoDto resultado = servicioBusqueda.buscarRestaurantesYPlatos(texto);
            model.addAttribute("resultado", resultado);
            model.addAttribute("textoBusqueda", texto);
        }

        model.addAttribute("usuario", usuario);

        return "resultado-busqueda";
    }


    @GetMapping("/sugerencias")
    @ResponseBody
    public List<SugerenciaDto> sugerencias(@RequestParam String texto) {
        return servicioBusqueda.obtenerSugerencias(texto);
    }

}
