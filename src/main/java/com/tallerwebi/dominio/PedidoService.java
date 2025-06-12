package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Restaurante;
import com.tallerwebi.presentacion.PedidoDto;

import java.util.List;

public interface PedidoService {

    List<Restaurante> traerRestaurantesDestacados();
    List<PlatoDto> traerPlatosDestacados();
    List<PlatoDto> traerTodosLosPlatos();
    List<PlatoDto> buscarPlatosPorTipoComida(String tipoComida);
    List<PlatoDto> ordenarPlatos(List<PlatoDto> platos,String tipoOrdenar);
    PedidoDto buscarPedidoActivoPorUsuario();
    void crearPedido(PedidoDto pedido);
    void agregarPlatoAlPedido(PlatoDto plato);
  //  void guardarPlatoDb(Plato plato);
}
