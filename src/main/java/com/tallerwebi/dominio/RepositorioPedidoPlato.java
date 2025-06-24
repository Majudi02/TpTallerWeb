package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.PedidoPlato;

public interface RepositorioPedidoPlato {
    PedidoPlato buscarPorId(Long id);
    void guardar(PedidoPlato pedidoPlato);
    void finalizarPedido(Long id);
}
