package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Resena;
import com.tallerwebi.dominio.entidades.Restaurante;
import com.tallerwebi.presentacion.PedidoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServicioAdminImpl implements ServicioAdmin {

    private final ServicioPedidoRestaurante servicioPedidoRestaurante;
    private final ServicioPedidoPlato servicioPedidoPlato;
    private final ServicioPlato servicioPlato;
    private final ServicioRestaurante servicioRestaurante;
    private final ServicioResena servicioResena;

    @Autowired
    public ServicioAdminImpl(ServicioPedidoRestaurante servicioPedidoRestaurante,
                             ServicioPedidoPlato servicioPedidoPlato,
                             ServicioPlato servicioPlato,
                             ServicioRestaurante servicioRestaurante,
                             ServicioResena servicioResena) {
        this.servicioPedidoRestaurante = servicioPedidoRestaurante;
        this.servicioPedidoPlato = servicioPedidoPlato;
        this.servicioPlato = servicioPlato;
        this.servicioRestaurante = servicioRestaurante;
        this.servicioResena = servicioResena;
    }

    // Total pedidos en general (todos)
    @Override
    public Integer obtenerTotalPedidos() {
        return servicioPedidoRestaurante.traerTodosLosPedidos().size();
    }

    // Top platos vendidos: SOLO pedidos finalizados (ventas reales)
    @Override
    public List<PlatoDto> obtenerTopPlatosMasVendidos(int cantidad) {
        List<PedidoDto> pedidosFinalizados = servicioPedidoRestaurante.traerPedidosFinalizados();

        Map<Integer, Integer> conteoPlatos = new HashMap<>();

        for (PedidoDto pedido : pedidosFinalizados) {
            if (pedido.getPedidoPlatos() != null) {
                for (var pedidoPlatoDto : pedido.getPedidoPlatos()) {
                    Integer idPlato = pedidoPlatoDto.getPlato().getId();
                    conteoPlatos.put(idPlato, conteoPlatos.getOrDefault(idPlato, 0) + 1);
                }
            }
        }

        List<Integer> topIds = conteoPlatos.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(cantidad)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<PlatoDto> topPlatos = new ArrayList<>();
        for (Integer id : topIds) {
            PlatoDto plato = servicioPlato.buscarPlatoPorId(id);
            if (plato != null) {
                topPlatos.add(plato);
            }
        }
        return topPlatos;
    }

    // Top restaurantes por pedidos: también mejor con pedidos finalizados
    @Override
    public List<Restaurante> obtenerTopRestaurantesPorCantidadDePedidos(int cantidad) {
        List<PedidoDto> pedidosFinalizados = servicioPedidoRestaurante.traerPedidosFinalizados();

        Map<Long, Integer> conteoRestaurantes = new HashMap<>();

        for (PedidoDto pedido : pedidosFinalizados) {
            Long idRestaurante = pedido.getIdRestaurante();
            if (idRestaurante != null) {
                conteoRestaurantes.put(idRestaurante,
                        conteoRestaurantes.getOrDefault(idRestaurante, 0) + 1);
            }
        }

        List<Long> topIds = conteoRestaurantes.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(cantidad)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Restaurante> topRestaurantes = new ArrayList<>();
        for (Long id : topIds) {
            Restaurante restaurante = servicioRestaurante.obtenerRestaurantePorId(id);
            if (restaurante != null) {
                topRestaurantes.add(restaurante);
            }
        }
        return topRestaurantes;
    }

    // Total facturado: SOLO pedidos finalizados
    @Override
    public Double obtenerTotalFacturado() {
        List<PedidoDto> pedidosFinalizados = servicioPedidoRestaurante.traerPedidosFinalizados();

        return pedidosFinalizados.stream()
                .mapToDouble(p -> p.getPrecio() != null ? p.getPrecio() : 0.0)
                .sum();
    }

    @Override
    public List<Resena> obtenerUltimasResenas(int cantidad) {
        List<Restaurante> restaurantes = servicioRestaurante.obtenerRestaurantes();

        List<Resena> todasResenas = new ArrayList<>();

        for (Restaurante restaurante : restaurantes) {
            List<Resena> resenas = servicioResena.obtenerUltimasResenas(restaurante.getId(), cantidad);
            todasResenas.addAll(resenas);
        }

        todasResenas.sort((r1, r2) -> r2.getFecha().compareTo(r1.getFecha()));

        return todasResenas.stream().limit(cantidad).collect(Collectors.toList());
    }

    // Cantidad por plato: SOLO pedidos finalizados
    @Override
    public Map<Integer, Integer> obtenerCantidadPorPlato() {
        List<PedidoDto> pedidosFinalizados = servicioPedidoRestaurante.traerPedidosFinalizados();

        Map<Integer, Integer> conteoPlatos = new HashMap<>();

        for (PedidoDto pedido : pedidosFinalizados) {
            if (pedido.getPedidoPlatos() != null) {
                for (var pedidoPlatoDto : pedido.getPedidoPlatos()) {
                    Integer idPlato = pedidoPlatoDto.getPlato().getId();
                    conteoPlatos.put(idPlato, conteoPlatos.getOrDefault(idPlato, 0) + 1);
                }
            }
        }
        return conteoPlatos;
    }

    // Cantidad pedidos por restaurante: acá mejor TODOS para mostrar actividad total
    @Override
    public Map<Long, Integer> obtenerCantidadPedidosPorRestaurante() {
        List<PedidoDto> pedidos = servicioPedidoRestaurante.traerTodosLosPedidos();

        Map<Long, Integer> conteoRestaurantes = new HashMap<>();

        for (PedidoDto pedido : pedidos) {
            Long idRestaurante = pedido.getIdRestaurante();
            if (idRestaurante != null) {
                conteoRestaurantes.put(idRestaurante,
                        conteoRestaurantes.getOrDefault(idRestaurante, 0) + 1);
            }
        }
        return conteoRestaurantes;
    }

    @Override
    public Map<LocalDate, Integer> obtenerCantidadPedidosPorFecha(LocalDate desde, LocalDate hasta) {
        List<PedidoDto> pedidos = servicioPedidoRestaurante.traerTodosLosPedidos();

        Map<LocalDate, Integer> pedidosPorFecha = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (PedidoDto pedido : pedidos) {
            String fechaStr = pedido.getFecha();
            if (fechaStr != null && !fechaStr.isEmpty()) {
                LocalDateTime fechaHora = LocalDateTime.parse(fechaStr, formatter);
                LocalDate fechaPedido = fechaHora.toLocalDate();

                if (!fechaPedido.isBefore(desde) && !fechaPedido.isAfter(hasta)) {
                    pedidosPorFecha.put(fechaPedido,
                            pedidosPorFecha.getOrDefault(fechaPedido, 0) + 1);
                }
            }
        }

        return pedidosPorFecha;
    }

    @Override
    public Integer obtenerTotalPedidosFiltrado(LocalDate desde, LocalDate hasta) {
        List<PedidoDto> pedidos = servicioPedidoRestaurante.traerTodosLosPedidos();

        if (desde == null || hasta == null) {
            return pedidos.size(); // sin filtro, todos
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        int total = 0;
        for (PedidoDto pedido : pedidos) {
            String fechaStr = pedido.getFecha();
            if (fechaStr != null && !fechaStr.isEmpty()) {
                LocalDateTime fechaHora = LocalDateTime.parse(fechaStr, formatter);
                LocalDate fechaPedido = fechaHora.toLocalDate();

                if (!fechaPedido.isBefore(desde) && !fechaPedido.isAfter(hasta)) {
                    total++;
                }
            }
        }
        return total;
    }

    @Override
    public Double obtenerTotalFacturadoFiltrado(LocalDate desde, LocalDate hasta) {
        List<PedidoDto> pedidos = servicioPedidoRestaurante.traerTodosLosPedidos();

        if (desde == null || hasta == null) {
            return pedidos.stream()
                    .mapToDouble(p -> p.getPrecio() != null ? p.getPrecio() : 0.0)
                    .sum();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        double total = 0;
        for (PedidoDto pedido : pedidos) {
            String fechaStr = pedido.getFecha();
            if (fechaStr != null && !fechaStr.isEmpty()) {
                LocalDateTime fechaHora = LocalDateTime.parse(fechaStr, formatter);
                LocalDate fechaPedido = fechaHora.toLocalDate();

                if (!fechaPedido.isBefore(desde) && !fechaPedido.isAfter(hasta)) {
                    total += (pedido.getPrecio() != null) ? pedido.getPrecio() : 0.0;
                }
            }
        }
        return total;
    }

    @Override
    public Double obtenerPromedioFacturacionDiaria(LocalDate desde, LocalDate hasta) {
        if (desde == null) {
            desde = LocalDate.of(2025, 1, 1);
        }
        if (hasta == null) {
            hasta = LocalDate.now();
        }

        List<PedidoDto> pedidos = servicioPedidoRestaurante.traerPedidosEntreFechas(desde, hasta);

        if (pedidos.isEmpty()) return 0.0;

        double total = pedidos.stream()
                .mapToDouble(p -> p.getPrecio() != null ? p.getPrecio() : 0.0)
                .sum();

        long dias = java.time.temporal.ChronoUnit.DAYS.between(desde, hasta) + 1;

        return total / dias;
    }

}