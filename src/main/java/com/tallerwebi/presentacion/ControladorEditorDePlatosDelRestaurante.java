package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ControladorEditorDePlatosDelRestaurante {

    private ServicioRestaurante servicioRestaurante;
    private EtiquetaService etiquetaSevice;
    private ServicioPedidoPlato servicioPedidoPlato;


    @Autowired
    public ControladorEditorDePlatosDelRestaurante(ServicioRestaurante servicioRestaurante, EtiquetaService etiquetaSevice,ServicioPedidoPlato servicioPedidoPlato) {
        this.servicioPedidoPlato=servicioPedidoPlato;
        this.etiquetaSevice=etiquetaSevice;
        this.servicioRestaurante=servicioRestaurante;
    }

    @GetMapping("/crear-plato")
    public ModelAndView crearPlato(){
        ModelMap modelMap = new ModelMap();
        modelMap.put("etiquetasDisponibles", etiquetaSevice.traerTodasLasEtiquetas());
        return new ModelAndView("crearPlato",modelMap);
    }

    @PostMapping("/crear-plato/guardar")
    public ModelAndView guardarPlato(@ModelAttribute PlatoDto platoDto,
                                     @RequestParam("imagen") MultipartFile imagen,
                                     HttpServletRequest request) {
        UsuarioDTO usuarioLogueado = (UsuarioDTO) request.getSession().getAttribute("usuario");
        if (usuarioLogueado == null || !usuarioLogueado.getTipoUsuario().equals("restaurante")) {
            return new ModelAndView("redirect:/nutriya-login");
        }

        platoDto.setIdRestaurante(usuarioLogueado.getId());

        if (platoDto.getEtiquetasIds() != null) {
            List<EtiquetaDto> etiquetas = new ArrayList<>();
            for (Integer idEtiqueta : platoDto.getEtiquetasIds()) {
                EtiquetaDto etiqueta = etiquetaSevice.buscarEtiquetaPorId(idEtiqueta);
                if (etiqueta != null) {
                    etiquetas.add(etiqueta);
                } else {
                    System.out.println("Etiqueta con ID " + idEtiqueta + " no encontrada.");
                }
            }
            platoDto.setEtiquetas(etiquetas);
        }

        servicioRestaurante.guardarImagen(platoDto, imagen);

        Boolean guardado = servicioRestaurante.guardarPlato(platoDto);

        System.out.println("Restaurante ID en el DTO: " + platoDto.getIdRestaurante());
        System.out.println(usuarioLogueado);
        return new ModelAndView(guardado ? "redirect:/editarPlatos" : "redirect:/perfil-home");
    }

    @GetMapping("/editarPlatos")
    public ModelAndView editarPlatosPantalla(HttpServletRequest request){
        UsuarioDTO usuarioLogueado = (UsuarioDTO) request.getSession().getAttribute("usuario");
        if (usuarioLogueado == null || !usuarioLogueado.getTipoUsuario().equals("restaurante")) {
            return new ModelAndView("redirect:/nutriya-login");
        }

        ModelMap modelo = new ModelMap();

        Restaurante restaurante = servicioRestaurante.obtenerRestaurantePorUsuarioId(usuarioLogueado.getId());

        List<PlatoDto> platos = new ArrayList<>();
        if(restaurante != null) {
            platos = servicioRestaurante.obtenerPlatosDelRestaurante(restaurante.getId());
        }

        for (PlatoDto plato : platos) {
            Double promedio = servicioPedidoPlato.obtenerPromedioCalificacionPorPlato(plato.getId());
            promedio = Math.round(promedio * 10.0) / 10.0;
            plato.setCalificacionPromedio(promedio);
        }

        modelo.put("platos", platos);

        return new ModelAndView("platos-del-restautrante", modelo);
    }


    @GetMapping("/plato/{id}")
    public ModelAndView administrarPlatos(@PathVariable Integer id) {
        ModelMap modelo = new ModelMap();

        PlatoDto platoObtenido= servicioRestaurante.obtenerPlatoPorId(id);
        modelo.put("etiquetasDisponibles", etiquetaSevice.traerTodasLasEtiquetas());
        modelo.put("plato", platoObtenido);

        return new ModelAndView("plato-gestion", modelo);
    }

    @PostMapping("/plato/{id}/actualizar")
    public ModelAndView actualizarPlato(@ModelAttribute PlatoDto platoDto,
                                        @RequestParam("imagen") MultipartFile imagen,
                                        HttpServletRequest request) {

        if (platoDto.getEtiquetasIds() != null) {
            List<EtiquetaDto> etiquetas = new ArrayList<>();
            for (Integer idEtiqueta : platoDto.getEtiquetasIds()) {
                EtiquetaDto etiqueta = etiquetaSevice.buscarEtiquetaPorId(idEtiqueta);
                if (etiqueta != null) {
                    etiquetas.add(etiqueta);
                } else {
                    System.out.println("Etiqueta con ID " + idEtiqueta + " no encontrada.");
                }
            }
            platoDto.setEtiquetas(etiquetas);
        }


        servicioRestaurante.guardarImagen(platoDto,imagen);

        Boolean actualizado = servicioRestaurante.actualizarPlato(platoDto);

        if (actualizado) {
            return new ModelAndView("redirect:/editarPlatos");
        } else {
            return new ModelAndView("redirect:/perfil-home");
        }
    }
}
