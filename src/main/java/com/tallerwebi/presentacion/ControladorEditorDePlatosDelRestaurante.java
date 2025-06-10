package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EtiquetaDto;
import com.tallerwebi.dominio.EtiquetaSevice;
import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.dominio.ServicioRestaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class ControladorEditorDePlatosDelRestaurante {

    private ServicioRestaurante servicioRestaurante;
    private EtiquetaSevice etiquetaSevice;


    @Autowired
    public ControladorEditorDePlatosDelRestaurante(ServicioRestaurante servicioRestaurante, EtiquetaSevice etiquetaSevice){
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

        Boolean guardado = servicioRestaurante.guardarPlato(platoDto);

        return new ModelAndView(guardado ? "redirect:/hacer-pedido-platos" : "redirect:/perfil-home");
    }

    @GetMapping("/editarPlatos")
    public ModelAndView editarPlatosPantalla(){
        ModelMap modelo= new ModelMap();
        List<PlatoDto> platos= servicioRestaurante.traerTodosLosPlatos();

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
