package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioEmail;
import com.tallerwebi.dominio.ServicioRestaurante;
import com.tallerwebi.dominio.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class ControladorUsuario {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Autowired
    private ServicioRestaurante servicioRestaurante;

    @Autowired
    private ServicioEmail servicioEmail;

    public ControladorUsuario(ServicioUsuario servicioUsuario, ServicioRestaurante servicioRestaurante, ServicioEmail servicioEmail) {
        this.servicioUsuario = servicioUsuario;
        this.servicioRestaurante = servicioRestaurante;
        this.servicioEmail = servicioEmail;
    }

    @GetMapping("/nutriya-register")
    public ModelAndView mostrarFormularioRegistro() {
        ModelMap model = new ModelMap();
        model.addAttribute("registroUsuarioDTO", new UsuarioDTO());
        return new ModelAndView("nutriya-register", model);
    }

    @PostMapping("/nutriya-register")
    public ModelAndView registrarUsuario(@ModelAttribute("registroUsuarioDTO") UsuarioDTO usuarioDTO,
                                         @RequestPart(value = "imagenRestaurante", required = false) MultipartFile imagen,
                                         RedirectAttributes redirectAttributes) {

        if (usuarioDTO.getTipoUsuario() == null || usuarioDTO.getTipoUsuario().isEmpty()) {
            return mostrarFormularioRegistro();
        }


        boolean datosInvalidos = false;
        switch (usuarioDTO.getTipoUsuario()) {
            case "cliente":
                datosInvalidos = usuarioDTO.getNombre() == null || usuarioDTO.getEmail() == null || usuarioDTO.getPassword() == null ||
                        usuarioDTO.getEdad() == null || usuarioDTO.getPesoActual() == null ||
                        usuarioDTO.getPesoDeseado() == null || usuarioDTO.getAltura() == null ||
                        usuarioDTO.getObjetivo() == null || usuarioDTO.getCalle() == null || usuarioDTO.getNumero() == null || usuarioDTO.getLocalidad() == null;
                break;
            case "restaurante":
                datosInvalidos = usuarioDTO.getNombre() == null || usuarioDTO.getEmail() == null || usuarioDTO.getPassword() == null ||
                        usuarioDTO.getDescripcion() == null || usuarioDTO.getCalle() == null ||
                        usuarioDTO.getNumero() == null || usuarioDTO.getLocalidad() == null ||
                        usuarioDTO.getZona() == null;
                break;
            case "repartidor":
                datosInvalidos = usuarioDTO.getNombre() == null || usuarioDTO.getApellido() == null ||
                        usuarioDTO.getDni() == null || usuarioDTO.getTelefono() == null ||
                        usuarioDTO.getEmail() == null || usuarioDTO.getPassword() == null ||
                        usuarioDTO.getVehiculo() == null;
                break;
            default:
                datosInvalidos = true;
        }

        if (datosInvalidos) {
            return mostrarFormularioRegistro();
        }

        UsuarioDTO usuarioEncontrado = servicioUsuario.getUsuario(usuarioDTO.getEmail());
        if (usuarioEncontrado != null) {
            redirectAttributes.addFlashAttribute("errorEmail", "Ya existe un usuario registrado con ese email.");
            return new ModelAndView("redirect:/resultado-registro");
        }

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
                }
            }
        }

        String token = UUID.randomUUID().toString();
        usuarioDTO.setTokenConfirmacion(token);
        usuarioDTO.setConfirmado(false);

        servicioUsuario.registrarUsuario(usuarioDTO);

        String destinatario = usuarioDTO.getEmail();
        String asunto = "Confirma tu cuenta en NutriYa";
        String urlConfirmacion = "http://localhost:8080/nutriya-confirmar-registro?token=" + token;
        String cuerpoMensaje = "Hola " + usuarioDTO.getNombre() + ",\n\n" +
                "Gracias por registrarte en NutriYa. Por favor, haz clic en el siguiente enlace para confirmar tu cuenta:\n" +
                urlConfirmacion + "\n\n" +
                "Saludos,\nEl equipo de NutriYa";
        servicioEmail.enviarEmail(destinatario, asunto, cuerpoMensaje);

        ModelMap modelo = new ModelMap();
        modelo.put("emailEnviadoA", usuarioDTO.getEmail());

        return new ModelAndView("confirmacion", modelo);


    }

    @GetMapping("/nutriya-confirmar-registro")
    public ModelAndView confirmarRegistro(@RequestParam("token") String token) {
        ModelMap model = new ModelMap();
        Boolean exitoConfirmacion = servicioUsuario.confirmarUsuarioPorToken(token);

        if (exitoConfirmacion) {
            model.addAttribute("mensaje", "¡Tu cuenta ha sido confirmada exitosamente! Ya puedes iniciar sesión.");
            return new ModelAndView("cuenta-confirmada", model);
        } else {
            model.addAttribute("error", "El enlace de confirmación no es válido o ha expirado.");
            return new ModelAndView("confirmacion", model);
        }
    }


    @GetMapping("/resultado-registro")
    public ModelAndView mostrarRegistroExitoso() {
        return new ModelAndView("resultado-registro");
    }

    @GetMapping("/nutriya-login")
    public ModelAndView mostrarFormularioLogin(HttpServletRequest request) {
        UsuarioDTO usuarioSesion = (UsuarioDTO) request.getSession().getAttribute("usuario");

        if (usuarioSesion != null) {
            String tipo = usuarioSesion.getTipoUsuario();
            if ("cliente".equalsIgnoreCase(tipo)) {
                return new ModelAndView("redirect:/cliente/perfil");
            } else if ("repartidor".equalsIgnoreCase(tipo)) {
                return new ModelAndView("redirect:/repartidor/perfil");
            } else if ("restaurante".equalsIgnoreCase(tipo)) {
                return new ModelAndView("redirect:/restaurante/perfil");
            }
        }

        // Si no hay usuario en sesión, mostrar formulario de login
        ModelMap model = new ModelMap();
        model.addAttribute("loginDTO", new UsuarioDTO());
        return new ModelAndView("nutriya-login", model);
    }


    @PostMapping("/nutriya-login")
    public ModelAndView procesarLogin(@ModelAttribute("loginDTO") UsuarioDTO loginDTO, RedirectAttributes redirectAttributes, ModelMap model, HttpServletRequest request) {
        UsuarioDTO usuarioEncontrado = servicioUsuario.validarUsuario(loginDTO.getEmail(), loginDTO.getPassword());

        if (usuarioEncontrado == null) {
            model.addAttribute("errorLogin", "Email o contraseña incorrectos");
            model.addAttribute("loginDTO", new UsuarioDTO());
            return new ModelAndView("nutriya-login", model);
        }

        if (!usuarioEncontrado.getConfirmado()) {
            ModelMap modelMap = new ModelMap();
            modelMap.put("emailParaConfirmar", usuarioEncontrado.getEmail());
            modelMap.put("nombre", usuarioEncontrado.getNombre());
            return new ModelAndView("pendiente-confirmacion", modelMap);
        }

        request.getSession().setAttribute("usuario", usuarioEncontrado);

        model.addAttribute("usuario", usuarioEncontrado);

        String tipo = usuarioEncontrado.getTipoUsuario();

        if ("cliente".equalsIgnoreCase(tipo)) {
            return new ModelAndView("redirect:/cliente/perfil");
        } else if ("repartidor".equalsIgnoreCase(tipo)) {
            return new ModelAndView("redirect:/repartidor/perfil");
        } else if ("restaurante".equalsIgnoreCase(tipo)) {
            return new ModelAndView("redirect:/restaurante/perfil");
        }

        return new ModelAndView("nutriya-login", model);
    }


    @GetMapping(value = "/validar-email", produces = "application/json")
    @ResponseBody
    public String validarEmail(@RequestParam String email) {
        boolean disponible = servicioUsuario.getUsuario(email) == null;
        return "{\"disponible\": " + disponible + "}";
    }

    @GetMapping("/cerrar-sesion")
    public String cerrarSesion(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/nutriya-login";
    }

    @GetMapping("/cliente/perfil")
    public ModelAndView perfilCliente(HttpServletRequest request) {
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");
        if (usuario == null || !"cliente".equalsIgnoreCase(usuario.getTipoUsuario())) {
            return new ModelAndView("redirect:/nutriya-login");
        }
        ModelMap model = new ModelMap();
        model.put("usuario", usuario);
        return new ModelAndView("perfil-cliente", model);
    }

    @GetMapping("/repartidor/perfil")
    public ModelAndView perfilRepartidor(HttpServletRequest request) {
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");
        if (usuario == null || !"repartidor".equalsIgnoreCase(usuario.getTipoUsuario())) {
            return new ModelAndView("redirect:/nutriya-login");
        }
        ModelMap model = new ModelMap();
        model.put("usuario", usuario);
        return new ModelAndView("perfil-repartidor", model);
    }

    @GetMapping("/restaurante/perfil")
    public ModelAndView perfilRestaurante(HttpServletRequest request) {
        UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("usuario");
        if (usuario == null || !"restaurante".equalsIgnoreCase(usuario.getTipoUsuario())) {
            return new ModelAndView("redirect:/nutriya-login");
        }
        ModelMap model = new ModelMap();
        model.put("usuario", usuario);
        return new ModelAndView("perfil-restaurante", model);
    }
}

