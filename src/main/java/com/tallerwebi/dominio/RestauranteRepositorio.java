package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Plato;

import java.util.List;

public interface RestauranteRepositorio {
    Boolean crearPlato(Plato plato);
    Plato buscarPlatoPorId(Integer id);
    Boolean eliminarPlato(Integer id);
    List<Plato> buscarPlatosPorTipoComida(String tipoComida);
    List<Plato> traerTodosLosPlatos();
    Boolean editarEtiquetas(Plato plato);
}
