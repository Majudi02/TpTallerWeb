package com.tallerwebi.dominio;

import java.util.List;

public interface PedidoService {

    List<Restaurante> traerRestaurantesDestacados();
    List<Plato> traerPlatosDestacados();
    List<Plato> traerTodosLosPlatos();
}
