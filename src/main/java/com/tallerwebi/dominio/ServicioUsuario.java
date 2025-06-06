package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.UsuarioDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface ServicioUsuario {

    void registrarUsuario(UsuarioDTO usuarioDTO);

    UsuarioDTO getUsuario(String email);

    UsuarioDTO validarUsuario(String email, String password);
}

