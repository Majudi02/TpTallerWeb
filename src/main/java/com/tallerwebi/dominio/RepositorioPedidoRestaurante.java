package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Pedido;

import java.util.List;

public interface RepositorioPedidoRestaurante {

    List<Pedido> traerTodosLosPedidos();

    Long obtenerIdDelRestaurate(Long id);

    List<Pedido> traerPedidosListosParaRetirar();

    void entregarPedido(Integer idPedido);

    Pedido buscarPorId(int i);

    void guardar(Pedido pedido);
}
