package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Mensaje;

import java.util.List;

public interface RepositorioMensaje {
    List<Mensaje> traerMensajesPorPedido(Long pedidoId);

    void guardar(Mensaje mensaje);
}
