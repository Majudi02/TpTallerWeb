package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Pago;

public interface PagoServicio {
    void guardarPago(Pago pago);

    Pago obtenerPagoPorIdMercadoPago(Long idPagoMP);

    Pago obtenerPagoPorIdPedido(Integer idPedido);
}
