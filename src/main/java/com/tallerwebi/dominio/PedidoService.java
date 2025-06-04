package com.tallerwebi.dominio;

import java.util.List;

public interface PedidoService {

    List<Restaurante> traerRestaurantesDestacados();
    List<PlatoDto> traerPlatosDestacados();
    List<PlatoDto> traerTodosLosPlatos();
    List<PlatoDto> buscarPlatosPorTipoComida(String tipoComida);
    List<PlatoDto> ordenarPlatos(List<PlatoDto> platos,String tipoOrdenar);
  //  void guardarPlatoDb(Plato plato);
}
