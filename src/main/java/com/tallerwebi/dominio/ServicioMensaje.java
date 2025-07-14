package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.chat.Mensaje;

import java.util.List;

public interface ServicioMensaje {
    void guardarMensaje(Mensaje mensaje);

    List<Mensaje> traerMensajePorPedido(Long pedidoId);
}
