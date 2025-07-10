package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.Resena;
import com.tallerwebi.dominio.entidades.Restaurante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ServicioAdminImplTest {

    private ServicioPedidoRestaurante pedidoRestauranteMock;
    private ServicioPlato platoMock;
    private ServicioRestaurante restauranteMock;
    private ServicioResena resenaMock;

    private ServicioAdminImpl servicio;

    @BeforeEach
    public void setUp() {
        pedidoRestauranteMock = mock(ServicioPedidoRestaurante.class);
        platoMock = mock(ServicioPlato.class);
        restauranteMock = mock(ServicioRestaurante.class);
        resenaMock = mock(ServicioResena.class);

        servicio = new ServicioAdminImpl(pedidoRestauranteMock, platoMock, restauranteMock, resenaMock);
    }

    @Test
    public void puedoObtenerTotalDePedidos() {
        when(pedidoRestauranteMock.traerTodosLosPedidos()).thenReturn(Arrays.asList(new PedidoDto(), new PedidoDto()));
        int total = servicio.obtenerTotalPedidos();
        assertEquals(2, total);
    }

    @Test
    public void puedoObtenerTopRestaurantesPorCantidadDePedidos() {
        Restaurante r1 = new Restaurante();
        r1.setId(10L);
        Restaurante r2 = new Restaurante();
        r2.setId(20L);

        PedidoDto pedido1 = mock(PedidoDto.class);
        when(pedido1.getIdRestaurante()).thenReturn(10L);
        PedidoDto pedido2 = mock(PedidoDto.class);
        when(pedido2.getIdRestaurante()).thenReturn(10L);
        PedidoDto pedido3 = mock(PedidoDto.class);
        when(pedido3.getIdRestaurante()).thenReturn(20L);

        when(pedidoRestauranteMock.traerPedidosFinalizados()).thenReturn(Arrays.asList(pedido1, pedido2, pedido3));
        when(restauranteMock.obtenerRestaurantePorId(10L)).thenReturn(r1);
        when(restauranteMock.obtenerRestaurantePorId(20L)).thenReturn(r2);

        List<Restaurante> resultado = servicio.obtenerTopRestaurantesPorCantidadDePedidos(2);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(r1));
        assertTrue(resultado.contains(r2));
    }

    @Test
    public void puedoObtenerElTotalFacturado() {
        PedidoDto p1 = mock(PedidoDto.class);
        when(p1.getPrecio()).thenReturn(100.0);
        PedidoDto p2 = mock(PedidoDto.class);
        when(p2.getPrecio()).thenReturn(200.0);

        when(pedidoRestauranteMock.traerPedidosFinalizados()).thenReturn(Arrays.asList(p1, p2));
        Double total = servicio.obtenerTotalFacturado();
        assertEquals(300.0, total);
    }

    @Test
    public void puedoObtenerLasUltimasResenas() {
        Restaurante r1 = new Restaurante();
        r1.setId(1L);
        Restaurante r2 = new Restaurante();
        r2.setId(2L);

        Resena res1 = mock(Resena.class);
        when(res1.getFecha()).thenReturn(LocalDateTime.now().minusDays(1));
        Resena res2 = mock(Resena.class);
        when(res2.getFecha()).thenReturn(LocalDateTime.now());

        when(restauranteMock.obtenerRestaurantes()).thenReturn(Arrays.asList(r1, r2));
        when(resenaMock.obtenerUltimasResenas(1L, 2)).thenReturn(Collections.singletonList(res1));
        when(resenaMock.obtenerUltimasResenas(2L, 2)).thenReturn(Collections.singletonList(res2));

        List<Resena> resultado = servicio.obtenerUltimasResenas(2);
        assertEquals(2, resultado.size());
        assertEquals(res2, resultado.get(0));
        assertEquals(res1, resultado.get(1));
    }

    @Test
    public void puedoObtenerLaCantidadPedidaPorPlato() {
        PlatoDto plato1 = new PlatoDto(); plato1.setId(1);
        PlatoDto plato2 = new PlatoDto(); plato2.setId(2);

        PedidoPlatoDto pp1 = new PedidoPlatoDto(); pp1.setPlato(plato1);
        PedidoPlatoDto pp2 = new PedidoPlatoDto(); pp2.setPlato(plato2);
        PedidoPlatoDto pp3 = new PedidoPlatoDto(); pp3.setPlato(plato1);

        PedidoDto pedido = mock(PedidoDto.class);
        when(pedido.getPedidoPlatos()).thenReturn(Arrays.asList(pp1, pp2, pp3));
        when(pedidoRestauranteMock.traerPedidosFinalizados()).thenReturn(Collections.singletonList(pedido));

        Map<Integer, Integer> resultado = servicio.obtenerCantidadPorPlato();
        assertEquals(2, resultado.size());
        assertEquals(2, resultado.get(1));
        assertEquals(1, resultado.get(2));
    }

    @Test
    public void puedoObtenerCantidadPedidosPorRestaurante() {
        PedidoDto p1 = mock(PedidoDto.class);
        when(p1.getIdRestaurante()).thenReturn(1L);
        PedidoDto p2 = mock(PedidoDto.class);
        when(p2.getIdRestaurante()).thenReturn(1L);
        PedidoDto p3 = mock(PedidoDto.class);
        when(p3.getIdRestaurante()).thenReturn(2L);

        when(pedidoRestauranteMock.traerTodosLosPedidos()).thenReturn(Arrays.asList(p1, p2, p3));

        Map<Long, Integer> resultado = servicio.obtenerCantidadPedidosPorRestaurante();
        assertEquals(2, resultado.size());
        assertEquals(2, resultado.get(1L));
        assertEquals(1, resultado.get(2L));
    }

    @Test
    public void puedoObtenerCantidadDePedidosPorFechasDesdeYHasta() {
        PedidoDto p1 = mock(PedidoDto.class);
        when(p1.getFecha()).thenReturn("10/07/2025 15:30");
        PedidoDto p2 = mock(PedidoDto.class);
        when(p2.getFecha()).thenReturn("11/07/2025 10:00");
        PedidoDto p3 = mock(PedidoDto.class);
        when(p3.getFecha()).thenReturn("12/07/2025 20:00");

        when(pedidoRestauranteMock.traerTodosLosPedidos()).thenReturn(Arrays.asList(p1, p2, p3));

        LocalDate desde = LocalDate.of(2025,7,10);
        LocalDate hasta = LocalDate.of(2025,7,11);

        Map<LocalDate, Integer> resultado = servicio.obtenerCantidadPedidosPorFecha(desde, hasta);
        assertEquals(2, resultado.size());
        assertEquals(1, resultado.get(LocalDate.of(2025,7,10)));
        assertEquals(1, resultado.get(LocalDate.of(2025,7,11)));
    }

    @Test
    public void puedoObtenerTotalPedidosFiltrado() {
        PedidoDto p1 = mock(PedidoDto.class);
        when(p1.getFecha()).thenReturn("10/07/2025 15:30");
        PedidoDto p2 = mock(PedidoDto.class);
        when(p2.getFecha()).thenReturn("11/07/2025 10:00");
        PedidoDto p3 = mock(PedidoDto.class);
        when(p3.getFecha()).thenReturn("12/07/2025 20:00");

        when(pedidoRestauranteMock.traerTodosLosPedidos()).thenReturn(Arrays.asList(p1, p2, p3));

        LocalDate desde = LocalDate.of(2025,7,10);
        LocalDate hasta = LocalDate.of(2025,7,11);

        int totalFiltrado = servicio.obtenerTotalPedidosFiltrado(desde, hasta);
        assertEquals(2, totalFiltrado);

        // Cuando alguno es null, devuelve todos
        assertEquals(3, servicio.obtenerTotalPedidosFiltrado(null, hasta));
        assertEquals(3, servicio.obtenerTotalPedidosFiltrado(desde, null));
    }

    @Test
    public void puedoObtenerTotalFacturadoFiltrado() {
        PedidoDto p1 = mock(PedidoDto.class);
        when(p1.getFecha()).thenReturn("10/07/2025 15:30");
        when(p1.getPrecio()).thenReturn(100.0);

        PedidoDto p2 = mock(PedidoDto.class);
        when(p2.getFecha()).thenReturn("11/07/2025 10:00");
        when(p2.getPrecio()).thenReturn(200.0);

        PedidoDto p3 = mock(PedidoDto.class);
        when(p3.getFecha()).thenReturn("12/07/2025 20:00");
        when(p3.getPrecio()).thenReturn(300.0);

        when(pedidoRestauranteMock.traerTodosLosPedidos()).thenReturn(Arrays.asList(p1, p2, p3));

        LocalDate desde = LocalDate.of(2025,7,10);
        LocalDate hasta = LocalDate.of(2025,7,11);

        double totalFiltrado = servicio.obtenerTotalFacturadoFiltrado(desde, hasta);
        assertEquals(300.0, totalFiltrado);

        double totalSinFiltro = servicio.obtenerTotalFacturadoFiltrado(null, null);
        assertEquals(600.0, totalSinFiltro);
    }

    @Test
    public void puedoObtenerPromedioFacturacionDiaria() {
        PedidoDto p1 = mock(PedidoDto.class);
        when(p1.getFecha()).thenReturn("10/07/2025 15:30");
        when(p1.getPrecio()).thenReturn(100.0);

        PedidoDto p2 = mock(PedidoDto.class);
        when(p2.getFecha()).thenReturn("11/07/2025 10:00");
        when(p2.getPrecio()).thenReturn(200.0);

        when(pedidoRestauranteMock.traerPedidosEntreFechas(any(), any())).thenReturn(Arrays.asList(p1, p2));

        LocalDate desde = LocalDate.of(2025, 7, 10);
        LocalDate hasta = LocalDate.of(2025, 7, 11);

        double promedio = servicio.obtenerPromedioFacturacionDiaria(desde, hasta);
        assertEquals(150.0, promedio, 0.001);

        // si no hay pedidos devuelve 0.0
        when(pedidoRestauranteMock.traerPedidosEntreFechas(any(), any())).thenReturn(Collections.emptyList());
        assertEquals(0.0, servicio.obtenerPromedioFacturacionDiaria(desde, hasta));
    }


}
