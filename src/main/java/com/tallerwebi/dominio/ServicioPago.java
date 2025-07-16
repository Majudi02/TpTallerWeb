package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Pago;

public interface ServicioPago {
    void guardarPago(Pago pago);

    Pago obtenerPagoPorIdPedido(Integer idPedido);

    void setServicioEmail(ServicioEmail servicioEmailMock);
}
