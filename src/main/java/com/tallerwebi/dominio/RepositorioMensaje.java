package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.chat.Mensaje;

import java.util.List;

public interface RepositorioMensaje {
    List<Mensaje> traerMensajePorPedido(Long pedidoId);

    void guardar(Mensaje mensaje);
}
