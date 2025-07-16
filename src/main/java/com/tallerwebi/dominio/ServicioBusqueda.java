package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioBusqueda {
    BusquedaResultadoDto buscarRestaurantesYPlatos(String texto);

    List<SugerenciaDto> obtenerSugerencias(String texto);
}
