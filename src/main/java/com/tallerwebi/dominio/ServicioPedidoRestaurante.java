package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.PedidoDto;

import java.util.List;

public interface ServicioPedidoRestaurante {

    List<PedidoDto> traerTodosLosPedidos();

    List<PedidoDto> traerPedidosDelRestaurante(Long id);

    Long obtenerIdDelRestaurate(Long id);

    void finalizarPedido(Long id);

    List<PedidoVistaDto> traerPedidosListosParaVista();

    void entregarPedido(Integer idPedido);
}
