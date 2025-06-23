package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Cliente;
import com.tallerwebi.dominio.entidades.Pedido;
import com.tallerwebi.dominio.entidades.Plato;
import com.tallerwebi.dominio.entidades.UsuarioNutriya;

import java.util.List;

public interface RepositorioPedido {

    Pedido buscarPedidoActivoPorUsuario();
    void crearPedido(Pedido pedido);
    void agregarPlatoAlPedido(Plato plato, Long idUsuario);
    List<Plato> mostrarPlatosDelPedidoActual(Long idUsuario);

    Double mostrarPrecioTotalDelPedidoActual(Long idUsuario);

    Pedido buscarPedidoActivoPorUsuario(Long idUsuario);
    void agregarPlatoAlPedido(Long idUsuario,Plato plato);
    void finalizarPedido(Long id);
}
