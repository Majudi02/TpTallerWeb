package com.tallerwebi.presentacion;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

@ControllerAdvice
public class UsuarioSession {
    @ModelAttribute("tipoUsuario")
    public String tipoUsuario(HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        if (usuario != null) {
            return usuario.getTipoUsuario();
        }
        return null;
    }

    @ModelAttribute("logeado")
    public boolean estaLogueado(HttpSession session) {
        return session.getAttribute("usuario") != null;
    }
}
