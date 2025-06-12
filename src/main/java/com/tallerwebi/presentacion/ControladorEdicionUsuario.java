package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class ControladorEdicionUsuario {

    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorEdicionUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }


    @GetMapping("/editar-usuario")
    public String mostrarFormularioEdicion(HttpServletRequest request, Model model) {
        UsuarioDTO usuarioLogueado = (UsuarioDTO) request.getSession().getAttribute("usuario");
        if (usuarioLogueado == null) {
            return "redirect:/nutriya-login";
        }

        // Cargar datos actualizados del usuario (para evitar info desactualizada en sesión)
        UsuarioDTO usuarioCompleto = servicioUsuario.getUsuario(usuarioLogueado.getEmail());

        model.addAttribute("usuarioDTO", usuarioCompleto);
        System.out.println(usuarioCompleto);
        return "editar-usuario";
    }

    @PostMapping("/editar-usuario")
    public String procesarEdicion(@ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO,
                                  @RequestPart(value = "imagenRestaurante", required = false) MultipartFile imagen,
                                  HttpServletRequest request,
                                  Model model) {
        UsuarioDTO usuarioLogueado = (UsuarioDTO) request.getSession().getAttribute("usuario");
        if (usuarioLogueado == null) {
            return "redirect:/nutriya-login";
        }

        if (!usuarioLogueado.getId().equals(usuarioDTO.getId())) {
            return "redirect:/nutriya";
        }

        try {
            if ("restaurante".equals(usuarioDTO.getTipoUsuario())) {
                if (imagen != null && !imagen.isEmpty()) {
                    try {
                        String rutaProyecto = System.getProperty("user.dir");
                        String rutaBase = rutaProyecto + "/src/main/webapp/resources/assets/imagenesRestaurantes/";
                        Files.createDirectories(Paths.get(rutaBase));

                        String extension = imagen.getOriginalFilename()
                                .substring(imagen.getOriginalFilename().lastIndexOf("."));
                        String nombreArchivo = UUID.randomUUID() + extension;
                        Path rutaDestino = Paths.get(rutaBase, nombreArchivo);

                        Files.copy(imagen.getInputStream(), rutaDestino);

                        usuarioDTO.setImagen("/assets/imagenesRestaurantes/" + nombreArchivo);
                    } catch (Exception e) {
                        e.printStackTrace();
                        model.addAttribute("error", "No se pudo guardar la nueva imagen.");
                        return "editar-usuario";
                    }
                } else {
                    usuarioDTO.setImagen(usuarioLogueado.getImagen());
                }
            }

            servicioUsuario.editarUsuario(usuarioDTO);
            UsuarioDTO actualizado = servicioUsuario.getUsuario(usuarioDTO.getEmail());
            request.getSession().setAttribute("usuario", actualizado);

            switch (actualizado.getTipoUsuario()) {
                case "cliente":
                    return "redirect:/perfil-cliente";
                case "restaurante":
                    return "redirect:/perfil-restaurante";
                case "repartidor":
                    return "redirect:/perfil-repartidor";
                default:
                    return "redirect:/nutriya";
            }

        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            model.addAttribute("usuarioDTO", usuarioDTO);
            model.addAttribute("error", "Error al editar el usuario: " + ex.getMessage());
            return "editar-usuario";
        }
    }

}
