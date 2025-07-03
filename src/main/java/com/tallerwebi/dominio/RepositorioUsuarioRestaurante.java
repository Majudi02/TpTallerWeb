package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Restaurante;
import com.tallerwebi.dominio.entidades.UsuarioRestaurante;

import java.util.List;

public interface RepositorioUsuarioRestaurante {
    UsuarioRestaurante buscarPorUsuarioId(Long id);
    Restaurante buscarRestaurantePorId(Long restauranteId);
    List<UsuarioRestaurante> buscarTodos();
    List<Restaurante> buscarTodosLosRestaurantes();
    void guardar(UsuarioRestaurante usuarioRestaurante);
    void modificar(UsuarioRestaurante usuarioRestaurante);
    List<Restaurante> traerRestaurantesDestacados();
}
