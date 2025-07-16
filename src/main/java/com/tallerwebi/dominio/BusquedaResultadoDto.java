package com.tallerwebi.dominio;

import java.util.List;

public class BusquedaResultadoDto {
    private List<RestauranteDto> restaurantes;
    private List<PlatoDto> platos;

    public BusquedaResultadoDto(List<RestauranteDto> restaurantes, List<PlatoDto> platos) {
        this.restaurantes = restaurantes;
        this.platos = platos;
    }

    public List<RestauranteDto> getRestaurantes() {
        return restaurantes;
    }

    public void setRestaurantes(List<RestauranteDto> restaurantes) {
        this.restaurantes = restaurantes;
    }

    public List<PlatoDto> getPlatos() {
        return platos;
    }

    public void setPlatos(List<PlatoDto> platos) {
        this.platos = platos;
    }
}
