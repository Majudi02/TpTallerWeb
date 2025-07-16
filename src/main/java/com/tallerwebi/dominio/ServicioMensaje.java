package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Mensaje;
import com.tallerwebi.presentacion.MensajeDto;

import java.util.List;

public interface ServicioMensaje {
    void guardarMensaje(Mensaje mensaje);

    List<MensajeDto> traerMensajesPorPedido(Long pedidoId);
}
