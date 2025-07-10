package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Resena;
import com.tallerwebi.dominio.entidades.Restaurante;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ServicioAdmin {
    Integer obtenerTotalPedidos();

    List<PlatoDto> obtenerTopPlatosMasVendidos(int cantidad);

    List<Restaurante> obtenerTopRestaurantesPorCantidadDePedidos(int cantidad);

    Double obtenerTotalFacturado();

    List<Resena> obtenerUltimasResenas(int cantidad);

    Map<Integer, Integer> obtenerCantidadPorPlato();

    Map<Long, Integer> obtenerCantidadPedidosPorRestaurante();

    Map<LocalDate, Integer> obtenerCantidadPedidosPorFecha(LocalDate desde, LocalDate hasta);

    Integer obtenerTotalPedidosFiltrado(LocalDate filtroDesde, LocalDate filtroHasta);

    Double obtenerTotalFacturadoFiltrado(LocalDate filtroDesde, LocalDate filtroHasta);

    Double obtenerPromedioFacturacionDiaria(LocalDate desde, LocalDate hasta);
}
