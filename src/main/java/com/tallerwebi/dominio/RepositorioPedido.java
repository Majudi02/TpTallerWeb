package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Cliente;
import com.tallerwebi.dominio.entidades.Pedido;
import com.tallerwebi.dominio.entidades.Plato;
import com.tallerwebi.dominio.entidades.UsuarioNutriya;

public interface RepositorioPedido {

    Pedido buscarPedidoActivoPorUsuario();
    void crearPedido(Pedido pedido);
    void agregarPlatoAlPedido(Plato plato);
/*
    Pedido buscarPedidoActivoPorUsuario(Cliente usuario);
    void crearPedido(Pedido pedido);
    void agregarPlatoAlPedido(Cliente cliente,Plato plato);

 */
}
