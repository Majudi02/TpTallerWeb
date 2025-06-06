package com.tallerwebi.dominio;

public interface RepositorioUsuarioNutriya {
    UsuarioNutriya buscarPorEmailYPassword(String email, String password);
    void guardar(UsuarioNutriya usuario);
    UsuarioNutriya buscarPorEmail(String email);
    void modificar(UsuarioNutriya usuario);
}
