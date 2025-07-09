package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Plato;

import java.util.List;

public interface ServicioPlato {
    List<PlatoDto> traerTodosLosPlatos();
    List<PlatoDto> buscarPlatosPorTipoComida(String tipoComida);
    PlatoDto buscarPlatoPorId(Integer id);
    List <PlatoDto>buscarPlatosPorEtiquetasDelCliente(Long idCliente);

    List<PlatoDto> traerPlatosEnPromocion();
}
