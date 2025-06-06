package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioUsuarioRestaurante {
    UsuarioRestaurante buscarPorId(Long id);
    List<UsuarioRestaurante> buscarTodos();
    List<Restaurante> buscarTodosLosRestaurantes();
    void guardar(UsuarioRestaurante usuarioRestaurante);
    void modificar(UsuarioRestaurante usuarioRestaurante);
}
