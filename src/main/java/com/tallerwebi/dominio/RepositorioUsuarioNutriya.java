package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.UsuarioNutriya;

public interface RepositorioUsuarioNutriya {
    UsuarioNutriya buscarPorEmailYPassword(String email, String password);
    void guardar(UsuarioNutriya usuario);
    UsuarioNutriya buscarPorEmail(String email);
    void modificar(UsuarioNutriya usuario);
    UsuarioNutriya buscarPorTokenConfirmacion(String token);

    UsuarioNutriya buscarPorId(Long id);
}
