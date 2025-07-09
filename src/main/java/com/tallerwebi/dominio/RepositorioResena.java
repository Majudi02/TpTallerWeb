package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Resena;

import java.util.List;
import java.util.Map;

public interface RepositorioResena {
    void guardar(Resena resena);
    List<Resena> obtenerUltimasPorRestaurante(Long restauranteId, int cantidad);
    List<Resena> obtenerResenasPorRestaurante(Long restauranteId);
    Map<Integer, Double> calcularPromedioCalificacionPorPlato(Long idRestaurante);
}
