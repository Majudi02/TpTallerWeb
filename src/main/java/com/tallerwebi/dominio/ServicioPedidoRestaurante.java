package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.PedidoDto;

import java.util.List;

public interface ServicioPedidoRestaurante {

    List<PedidoDto> traerTodosLosPedidos();

    List<PedidoDto> traerPedidosDelRestaurante(Long id);

    Long obtenerIdDelRestaurate(Long id);

    void finalizarPlatoPedido(Long id);

    void confirmarPedidoListoParaEnviar(Integer idPedido);

    List<PedidoVistaDto> traerPedidosListosParaVista();

    void entregarPedido(Integer idPedido);

    PedidoVistaDto traerDetallePedidoPorId(Integer id);

    void finalizarPedidoCompleto(Integer idPedido);
}
