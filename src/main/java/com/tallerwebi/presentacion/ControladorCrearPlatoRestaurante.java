package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.PedidoService;
import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.dominio.ServicioRestaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class ControladorCrearPlatoRestaurante {

    private ServicioRestaurante servicioRestaurante;

    @Autowired
    public ControladorCrearPlatoRestaurante(ServicioRestaurante servicioRestaurante) {
        this.servicioRestaurante = servicioRestaurante;
    }


    @GetMapping("/crear-plato")
    public ModelAndView crearPlato(){
        ModelMap modelMap = new ModelMap();
        return new ModelAndView("crearPlato",modelMap);
    }

    @RequestMapping(value = "/guardarPlato", method = RequestMethod.POST)
    public ModelAndView guardarPlato(@ModelAttribute PlatoDto platoDto,
                                     @RequestParam("imagen") MultipartFile imagen,
                                     HttpServletRequest request) {
        if (!imagen.isEmpty()) {
            try {
                String rutaBase = request.getServletContext().getRealPath("/") + "assets/imagenesPlatos/";
                Files.createDirectories(Paths.get(rutaBase)); // Crea la carpeta si no existe



                String extension = imagen.getOriginalFilename().substring(imagen.getOriginalFilename().lastIndexOf("."));
                String nombreArchivo = UUID.randomUUID() + extension;
                Path rutaDestino = Paths.get(rutaBase, nombreArchivo);

                // Copiamos el archivo
                Files.copy(imagen.getInputStream(), rutaDestino);

                // Guardamos la ruta en el DTO
                platoDto.setImagen("/assets/imagenesPlatos/" + nombreArchivo);

            } catch (Exception e) {
            }
        }

        Boolean guardado = servicioRestaurante.guardarPlato(platoDto);
        System.out.println("Nombre: " + platoDto.getNombre());
        System.out.println("Imagen vacía? " + imagen.isEmpty());
        System.out.println("Resultado guardado: " + guardado);

        return new ModelAndView(guardado ? "redirect:/hacer-pedido-platos" : "redirect:/perfil-home");
    }
}



