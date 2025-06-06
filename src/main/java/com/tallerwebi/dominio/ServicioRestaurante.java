package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioRestaurante {
    Restaurante obtenerRestaurante(String nombre);

    List<Restaurante> obtenerRestaurantes();

    List<Restaurante> obtenerRestaurantesPorZona(String zona);

    List<Restaurante> buscarPorTipoComida(String tipoComida);

    List<Restaurante> buscarPorTipoComidaYZona(String zona, String tipoComida);

    void inicializarDatos();
}
