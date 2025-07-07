package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.PedidoPlato;

import java.util.List;

public interface RepositorioPedidoPlato {
    PedidoPlato buscarPorId(Long id);
    void guardar(PedidoPlato pedidoPlato);
    void finalizarPedido(Long id);
    Double obtenerPromedioCalificacionPorPlato(Integer id);
    List<PedidoPlato> obtenerPlatosPorRestaurante(Long idRestaurante);
}
