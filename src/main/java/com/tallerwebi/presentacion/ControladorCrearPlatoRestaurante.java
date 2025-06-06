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
    import java.io.*;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.UUID;

    @Controller
    public class ControladorCrearPlatoRestaurante {

        private ServicioRestaurante servicioRestaurante;
        private EtiquetaSevice etiquetaSevice;

        @Autowired
        public ControladorCrearPlatoRestaurante(ServicioRestaurante servicioRestaurante,EtiquetaSevice etiquetaSevice) {
            this.servicioRestaurante = servicioRestaurante;
            this.etiquetaSevice = etiquetaSevice;
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

            if (!imagen.isEmpty()) {
                try {
                    String rutaProyecto = System.getProperty("user.dir");
                    String rutaBase = rutaProyecto + "/src/main/webapp/resources/assets/imagenesPlatos/";
                    Files.createDirectories(Paths.get(rutaBase));

                    String extension = imagen.getOriginalFilename().substring(imagen.getOriginalFilename().lastIndexOf("."));
                    String nombreArchivo = UUID.randomUUID() + extension;
                    Path rutaDestino = Paths.get(rutaBase, nombreArchivo);

                    Files.copy(imagen.getInputStream(), rutaDestino);

                    platoDto.setImagen("/assets/imagenesPlatos/" + nombreArchivo);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Boolean guardado = servicioRestaurante.guardarPlato(platoDto);

            return new ModelAndView(guardado ? "redirect:/hacer-pedido-platos" : "redirect:/perfil-home");
        }


    }



