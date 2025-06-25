package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.*;

import java.util.List;

public interface RepositorioPedido {

    Pedido buscarPedidoActivoPorUsuario();
    void crearPedido(Pedido pedido);
    void agregarPlatoAlPedido(Plato plato, Long idUsuario);
    List<PedidoPlato> mostrarPlatosDelPedidoActual(Long idUsuario);
    Double mostrarPrecioTotalDelPedidoActual(Long idUsuario);
    Pedido buscarPedidoActivoPorUsuario(Long idUsuario);
    void finalizarPedido(Long id);
    Pedido buscarPorId(Integer idPedido);
}
