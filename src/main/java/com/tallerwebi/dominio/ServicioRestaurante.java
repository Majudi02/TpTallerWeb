package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Restaurante;

import java.util.List;

public interface ServicioRestaurante {
    Restaurante obtenerRestaurante(String nombre);

    List<Restaurante> obtenerRestaurantes();

    List<Restaurante> obtenerRestaurantesPorZona(String zona);

    List<Restaurante> buscarPorTipoComida(String tipoComida);

    List<Restaurante> buscarPorTipoComidaYZona(String zona, String tipoComida);

    Boolean guardarPlato(PlatoDto platoDto);

    void inicializarDatos();

    Boolean editarEtiquetas(PlatoDto platoDto);

    PlatoDto obtenerPlatoPorId(Integer id);
}
