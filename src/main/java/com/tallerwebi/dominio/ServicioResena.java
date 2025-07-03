package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Resena;

import java.util.List;

public interface ServicioResena {
    void guardarResena(Long restauranteId, Long clienteId, String comentario, Integer calificacion);
    List<Resena> obtenerUltimasResenas(Long restauranteId, int cantidad);
    List<Resena> obtenerResenasPorRestaurante(Long idRestaurante);
}
