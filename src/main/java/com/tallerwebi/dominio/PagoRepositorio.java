package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Pago;

public interface PagoRepositorio {
    void guardar(Pago pago);

    Pago buscarPorIdPagoMercadoPago(Long idPagoMP);

    Pago buscarPorPedidoId(Integer idPedido);
}
