package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Restaurante;
import com.tallerwebi.presentacion.PedidoDto;
import com.tallerwebi.presentacion.UsuarioDTO;

import java.util.List;

public interface PedidoService {

    List<Restaurante> traerRestaurantesDestacados();
    List<PlatoDto> traerPlatosDestacados();
    List<PlatoDto> traerTodosLosPlatos();
    List<PlatoDto> buscarPlatosPorTipoComida(String tipoComida);
    List<PlatoDto> ordenarPlatos(List<PlatoDto> platos,String tipoOrdenar);
    PedidoDto buscarPedidoActivoPorUsuario();
    //void crearPedido(PedidoDto pedido);
    void agregarPlatoAlPedido(PlatoDto plato, UsuarioDTO usuario);
    List<PlatoDto>mostrarPlatosDelPedidoActual(Long idUsuario);
    Double mostrarPrecioTotalDelPedidoActual(Long idUsuario);
    void finalizarPedido(Long id);
  //  void guardarPlatoDb(Plato plato);
}
