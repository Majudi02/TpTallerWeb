package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Restaurante;
import com.tallerwebi.dominio.ServicioRestaurante;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.UsuarioRestaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ControladorUsuario {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Autowired
    private ServicioRestaurante servicioRestaurante;

    public ControladorUsuario(ServicioUsuario servicioUsuario, ServicioRestaurante servicioRestaurante) {
        this.servicioUsuario = servicioUsuario;
        this.servicioRestaurante = servicioRestaurante;
    }

    @GetMapping("/nutriya-register")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("registroUsuarioDTO", new UsuarioDTO());
        return "nutriya-register";
    }

    @PostMapping("/nutriya-register")
    public String registrarUsuario(@ModelAttribute("registroUsuarioDTO") UsuarioDTO usuarioDTO, RedirectAttributes redirectAttributes) {
        if (usuarioDTO.getTipoUsuario() == null || usuarioDTO.getTipoUsuario().isEmpty()) {
            return "nutriya-register";
        }

        // Validar campos obligatorios según tipo de usuario
        if ("cliente".equals(usuarioDTO.getTipoUsuario())) {
            if (usuarioDTO.getNombre() == null || usuarioDTO.getEmail() == null || usuarioDTO.getPassword() == null ||
                    usuarioDTO.getEdad() == null || usuarioDTO.getPesoActual() == null ||
                    usuarioDTO.getPesoDeseado() == null || usuarioDTO.getAltura() == null ||
                    usuarioDTO.getObjetivo() == null) {
                return "nutriya-register";
            }
        } else if ("restaurante".equals(usuarioDTO.getTipoUsuario())) {
            if (usuarioDTO.getNombre() == null || usuarioDTO.getEmail() == null || usuarioDTO.getPassword() == null ||
                    usuarioDTO.getDescripcion() == null || usuarioDTO.getCalle() == null ||
                    usuarioDTO.getNumero() == null || usuarioDTO.getLocalidad() == null ||
                    usuarioDTO.getZona() == null) {
                return "nutriya-register";
            }
        } else if ("repartidor".equals(usuarioDTO.getTipoUsuario())) {
            if (usuarioDTO.getNombre() == null || usuarioDTO.getApellido() == null ||
                    usuarioDTO.getDni() == null || usuarioDTO.getTelefono() == null ||
                    usuarioDTO.getEmail() == null || usuarioDTO.getPassword() == null ||
                    usuarioDTO.getVehiculo() == null) {
                return "nutriya-register";
            }
        } else {
            return "nutriya-register";
        }

        // Verificar si ya hay un usuario con ese email
        UsuarioDTO usuarioEncontrado = servicioUsuario.getUsuario(usuarioDTO.getEmail());

        if (usuarioEncontrado != null) {
            redirectAttributes.addFlashAttribute("errorEmail", "Ya existe un usuario registrado con ese email.");
            return "redirect:/resultado-registro";
        }

        // SOLO UNA LLAMADA AL SERVICIO
        servicioUsuario.registrarUsuario(usuarioDTO);

        System.out.println("DTO recibido: " + usuarioDTO);
        return "redirect:/resultado-registro";
    }


    @GetMapping("/resultado-registro")
    public String mostrarRegistroExitoso() {
        return "resultado-registro";
    }


    @GetMapping("/nutriya-login")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("loginDTO", new UsuarioDTO());  // Para ligar el formulario
        return "nutriya-login";  // nombre de la vista login.html
    }

    @PostMapping("/nutriya-login")
    public String procesarLogin(@ModelAttribute("loginDTO") UsuarioDTO loginDTO, Model model) {
        UsuarioDTO usuarioEncontrado = servicioUsuario.validarUsuario(loginDTO.getEmail(), loginDTO.getPassword());

        if (usuarioEncontrado == null) {
            model.addAttribute("errorLogin", "Email o contraseña incorrectos");
            return "nutriya-login";
        }

        model.addAttribute("usuario", usuarioEncontrado);

        // Redirigir según tipoUsuario
        String tipo = usuarioEncontrado.getTipoUsuario();

        if ("cliente".equalsIgnoreCase(tipo)) {
            return "perfil-cliente";
        } else if ("repartidor".equalsIgnoreCase(tipo)) {
            return "perfil-repartidor";
        } else if ("restaurante".equalsIgnoreCase(tipo)) {
            return "perfil-restaurante";
        }
        return "nutriya-login";
    }

}
