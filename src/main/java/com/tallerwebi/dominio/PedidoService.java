package com.tallerwebi.dominio;

import java.util.List;

public interface PedidoService {

    List<Restaurante> traerRestaurantesDestacados();
    List<Plato> traerPlatosDestacados();
    List<Plato> traerTodosLosPlatos();
    List<Plato> buscarPlatosPorTipoComida(String tipoComida);
    List<Plato> ordenarPlatos(List<Plato> platos,String tipoOrdenar);
}
