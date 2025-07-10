package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.PedidoPlato;
import com.tallerwebi.dominio.entidades.Plato;

import java.util.List;

public interface RepositorioPedidoPlato {
    PedidoPlato buscarPorId(Long id);
    void guardar(PedidoPlato pedidoPlato);
    void finalizarPedido(Long id);
    Double obtenerPromedioCalificacionPorPlato(Integer id);
    List<Plato> obtenerPlatosConCalificacionesPorRestaurante(Long idRestaurante);
    List<PedidoPlato> obtenerPlatosPorRestaurante(Long idRestaurante);
    List<Plato> traerLos3PlatosMenosPedidos(Long idRestaurante);

}
