package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.*;

import java.util.List;

public interface RepositorioPedido {

    void crearPedido(Pedido pedido);

    void agregarPlatoAlPedido(Plato plato, Long idUsuario);

    List<PedidoPlato> mostrarPlatosDelPedidoActual(Long idUsuario);

    Double mostrarPrecioTotalDelPedidoActual(Long idUsuario);

    Pedido buscarPedidoActivoPorUsuario(Long idUsuario);

    void agregarPlatoAlPedido(Long idUsuario, Plato plato);

    Pedido buscarPorId(Integer idPedido);

    List<Pedido> listarPedidosPorUsuario(Long usuarioId);

    void confirmarPedido(Long idUsuario);

    void eliminarPlatoDelPedido(Integer idPedido, Integer idPlato);

    void actualizarPedido(Pedido pedido);
}
