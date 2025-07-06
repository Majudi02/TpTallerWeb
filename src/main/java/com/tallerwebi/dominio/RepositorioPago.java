package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Pago;

public interface RepositorioPago {
    void guardar(Pago pago);

    Pago buscarPorPedidoId(Integer idPedido);
}
