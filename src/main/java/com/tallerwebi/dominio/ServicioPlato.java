package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioPlato {
    List<PlatoDto> traerTodosLosPlatos();
    List<PlatoDto> buscarPlatosPorTipoComida(String tipoComida);
    PlatoDto buscarPlatoPorId(Integer id);
    List <PlatoDto>buscarPlatosPorEtiquetasDelCliente(Long idCliente);
}
