package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.UsuarioDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioUsuarioImpl implements ServicioUsuario {

    private final List<UsuarioDTO> usuarios = new ArrayList<>();

    @Override
    public void registrarUsuario(UsuarioDTO usuarioDTO) {
        for (UsuarioDTO usuario : usuarios) {
            if (usuario.getEmail().equals(usuarioDTO.getEmail())) {
                throw new IllegalArgumentException("Email ya registrado");
            }
        }
        usuarios.add(usuarioDTO);
    }

    @Override
    public List<UsuarioDTO> getUsuarios() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public UsuarioDTO getUsuario(String email) {
        for (UsuarioDTO usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public UsuarioDTO validarUsuario(String email, String password) {
        for (UsuarioDTO usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null;
    }
}
