package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.*;

import java.util.List;

public interface RepositorioPedido {

    void crearPedido(Pedido pedido);

    void agregarPlatoAlPedido(Plato plato, Long idUsuario);

    List<PedidoPlato> mostrarPlatosDelPedidoActual(Long idUsuario);

    Double mostrarPrecioTotalDelPedidoActual(Long idUsuario);

    Pedido buscarPedidoActivoPorUsuario(Long idUsuario);

    Pedido finalizarPedido(Long id);

    void agregarPlatoAlPedido(Long idUsuario, Plato plato);

    void finalizarPedido(Long id);

    // List<Pedido> traerPedidosListosParaRetirar();

    void entregarPedido(Integer idPedido);


    Pedido buscarPorId(Integer idPedido);

    List<Pedido> listarPedidosPorUsuario(Long usuarioId);
}
