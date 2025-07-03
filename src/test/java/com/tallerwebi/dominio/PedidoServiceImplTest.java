package com.tallerwebi.dominio;


import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.infraestructura.RepositorioPlatoImpl;
import com.tallerwebi.presentacion.PedidoDto;
import com.tallerwebi.presentacion.PedidoPlatoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PedidoServiceImplTest {


    private RepositorioPedido repositorioPedido;
    private PedidoServiceImpl pedidoService;
    private ServicioPlato servicioPlato;
    private ServicioUsuario servicioUsuario;
    private ServicioPedidoPlato servicioPedidoPlato;

    @BeforeEach
    public void setUp() {
        servicioPlato = Mockito.mock(ServicioPlato.class);
        repositorioPedido = Mockito.mock(RepositorioPedido.class);
        servicioUsuario = Mockito.mock(ServicioUsuario.class);
        servicioPedidoPlato = Mockito.mock(ServicioPedidoPlato.class);
        pedidoService = new PedidoServiceImpl(servicioPlato, repositorioPedido, servicioUsuario,servicioPedidoPlato);
    }

/*
    @Test
    public void dadoQueHayRestaurantesDestacadosLosPuedoQuieroMostras() {
        List<Restaurante> restaurantesEsperados = List.of(
                new Restaurante("Green Bowl", "Comida Vegana", "/assets/restaurante.png", "calle", 123, "Don Torcuato", "Norte", List.of("Vegana")),
                new Restaurante("Natural Express", "Comida Vegana", "/assets/restaurante.png", "calle", 321, "La Matanza", "Oeste", List.of("Proteica")),
                new Restaurante("Vital Food", "Comida Proteica", "/assets/restaurante.png", "calle", 213, "La Matanza", "Oeste", List.of("Vegana", "Proteica")),
                new Restaurante("La Parrilla del Sur", "Especialidad en carnes a la parrilla", "/assets/restaurante-logo.png", "Av. Corrientes", 1234, "Buenos Aires", "Microcentro", List.of("Proteica")),
                new Restaurante("Sushi Zen", "Lo mejor de la cocina japonesa", "/assets/restaurante-logo.png", "Calle Defensa", 567, "Buenos Aires", "San Telmo", List.of("Proteica", "Sin gluten")),
                new Restaurante("Pizza Napoli", "Pizzas artesanales al horno de leña", "/assets/restaurante-logo.png", "Av. Santa Fe", 890, "Buenos Aires", "Recoleta", List.of("Opciones vegetarianas")),
                new Restaurante("Verde Vivo", "Comida saludable y vegana", "/assets/restaurante-logo.png", "Calle Mendoza", 234, "Mendoza", "Centro", List.of("Vegana", "Vegetariana", "Sin gluten"))
        );



        List<Restaurante> restaurantesObtenidos = pedidoService.traerRestaurantesDestacados();

        assertThat(restaurantesObtenidos.size(), equalTo(restaurantesEsperados.size()));
    }

 */


    @Test
    public void dadoQueHayPlatosDestacadosLosQuieroMostrarEnLaPantalla() {
        Cliente usuario = new Cliente();
        usuario.setId(1L);

        Etiqueta etiquetaProteica = new Etiqueta();
        etiquetaProteica.setId(1);
        etiquetaProteica.setNombre("Proteica");

        Etiqueta etiquetaVegetariana = new Etiqueta();
        etiquetaVegetariana.setId(2);
        etiquetaVegetariana.setNombre("Vegetariana");


        List<Etiqueta> etiquetasUsuario = List.of(etiquetaProteica, etiquetaVegetariana);
        usuario.setEtiquetas(etiquetasUsuario);

   List<PlatoDto> platos = List.of(
    new PlatoDto(1, 1L, "Milanesa con papas fritas", "Clásica milanesa...", "/assets/imagen-plato.png", 2500.0, List.of(new EtiquetaDto(1, "Proteica")), 0.0, 0.0, 0.0, 0.0),
    new PlatoDto(2, 1L, "Ravioles de ricota", "Ravioles caseros...", "/assets/imagen-plato.png", 2800.0, List.of(new EtiquetaDto(2, "Vegetariana")), 0.0, 0.0, 0.0, 0.0),
    new PlatoDto(3, 1L, "Pizza napolitana", "Pizza con tomate...", "/assets/imagen-plato.png", 3200.0, List.of(new EtiquetaDto(2, "Vegetariana")), 0.0, 0.0, 0.0, 0.0),
    new PlatoDto(4, 1L, "Hamburguesa completa", "Hamburguesa con lechuga...", "/assets/imagen-plato.png", 2900.0, List.of(new EtiquetaDto(1, "Proteica")), 0.0, 0.0, 0.0, 0.0),
    new PlatoDto(5, 1L, "Ensalada César", "Ensalada con lechuga...", "/assets/imagen-plato.png", 2300.0, List.of(new EtiquetaDto(1, "Proteica")), 0.0, 0.0, 0.0, 0.0),
    new PlatoDto(6, 1L, "Tarta de espinaca", "Tarta casera...", "/assets/imagen-plato.png", 2000.0, List.of(new EtiquetaDto(2, "Vegetariana")), 0.0, 0.0, 0.0, 0.0)
);

        when(servicioPlato.buscarPlatosPorEtiquetasDelCliente(usuario.getId())).thenReturn(platos);
        List<PlatoDto> platosObtenidos = pedidoService.traerPlatosDestacadosPorLaEtiquetaDelCliente(usuario.getId());

        assertThat(platosObtenidos.size(), equalTo(platos.size()));
    }



    @Test
    public void DadoQueExistenDiezPlatosConLaEtiquetaProteicaObtengoSoloLosDelTipoDeComidaFiltrada() {
        List<PlatoDto> platosTotales = List.of(
                new PlatoDto(1, 1L, "Milanesa con papas fritas", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2500.0, List.of(new EtiquetaDto(1, "Proteica")), 600.0, 45.0, 25.0, 50.0),
                new PlatoDto(2, 1L, "Ravioles de ricota", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2800.0, List.of(new EtiquetaDto(2, "Vegetariana")), 550.0, 30.0, 20.0, 60.0),
                new PlatoDto(3, 1L, "Pizza napolitana", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 3200.0, List.of(new EtiquetaDto(2, "Vegetariana")), 700.0, 25.0, 30.0, 70.0),
                new PlatoDto(4, 1L, "Hamburguesa completa", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2900.0, List.of(new EtiquetaDto(1, "Proteica")), 650.0, 40.0, 35.0, 45.0),
                new PlatoDto(5, 1L, "Ensalada César", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2300.0, List.of(new EtiquetaDto(1, "Proteica")), 400.0, 20.0, 15.0, 30.0),
                new PlatoDto(6, 1L, "Tarta de espinaca", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2000.0, List.of(new EtiquetaDto(2, "Vegetariana")), 450.0, 18.0, 22.0, 40.0),
                new PlatoDto(7, 1L, "Lasaña de carne", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 3100.0, List.of(new EtiquetaDto(1, "Proteica")), 680.0, 50.0, 28.0, 55.0),
                new PlatoDto(8, 1L, "Empanadas salteñas", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 1800.0, List.of(new EtiquetaDto(1, "Proteica")), 520.0, 35.0, 18.0, 45.0),
                new PlatoDto(9, 1L, "Pollo al horno con papas", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2600.0, List.of(new EtiquetaDto(1, "Proteica")), 600.0, 42.0, 20.0, 50.0),
                new PlatoDto(10, 1L, "Tacos de carne", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 3000.0, List.of(new EtiquetaDto(1, "Proteica")), 700.0, 48.0, 30.0, 60.0)
        );

        when(servicioPlato.buscarPlatosPorTipoComida("Proteica")).thenReturn(
                platosTotales.stream()
                        .filter(dto -> dto.getEtiquetas().stream()
                                .anyMatch(e -> e.getNombre().equalsIgnoreCase("Proteica")))
                        .collect(Collectors.toList())
        );

        List<PlatoDto> platosFiltrados = pedidoService.buscarPlatosPorTipoComida("Proteica");

        assertThat(platosFiltrados.size(), equalTo(7));
    }


    @Test
    public void DadoQueExisten6PlatosLosQuierOrdenarPorElPrecioDeMayorAMenor() {
        List<PlatoDto> platos = List.of(
                new PlatoDto(1, 1L, "Milanesa con papas fritas", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2500.0, List.of(new EtiquetaDto(1, "Proteica")), 600.0, 45.0, 25.0, 50.0),
                new PlatoDto(2, 1L, "Ravioles de ricota", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2800.0, List.of(new EtiquetaDto(2, "Vegetariana")), 550.0, 30.0, 20.0, 60.0),
                new PlatoDto(3, 1L, "Pizza napolitana", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 3200.0, List.of(new EtiquetaDto(2, "Vegetariana")), 700.0, 25.0, 30.0, 70.0),
                new PlatoDto(4, 1L, "Hamburguesa completa", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2900.0, List.of(new EtiquetaDto(1, "Proteica")), 650.0, 40.0, 35.0, 45.0),
                new PlatoDto(5, 1L, "Ensalada César", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2300.0, List.of(new EtiquetaDto(1, "Proteica")), 400.0, 20.0, 15.0, 30.0),
                new PlatoDto(6, 1L, "Tarta de espinaca", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2000.0, List.of(new EtiquetaDto(2, "Vegetariana")), 450.0, 18.0, 22.0, 40.0)
        );
        List<Double> preciosEsperados = List.of(3200.0, 2900.0, 2800.0, 2500.0, 2300.0, 2000.0);


        List<PlatoDto> platosOrdenados = pedidoService.ordenarPlatos(platos, "mayorAMenor");

        assertThat(platosOrdenados.get(0).getPrecio(), equalTo(preciosEsperados.get(0)));
        assertThat(platosOrdenados.get(5).getPrecio(), equalTo(preciosEsperados.get(5)));
    }

    @Test
    public void DadoQueExisten6PlatosLosQuierOrdenarPorElPrecioDeMenorAMayor() {
        List<PlatoDto> platos = List.of(
                new PlatoDto(1, 1L, "Milanesa con papas fritas", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2500.0, List.of(new EtiquetaDto(1, "Proteica")), 600.0, 45.0, 25.0, 50.0),
                new PlatoDto(2, 1L, "Ravioles de ricota", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2800.0, List.of(new EtiquetaDto(2, "Vegetariana")), 550.0, 30.0, 20.0, 60.0),
                new PlatoDto(3, 1L, "Pizza napolitana", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 3200.0, List.of(new EtiquetaDto(2, "Vegetariana")), 700.0, 25.0, 30.0, 70.0),
                new PlatoDto(4, 1L, "Hamburguesa completa", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2900.0, List.of(new EtiquetaDto(1, "Proteica")), 650.0, 40.0, 35.0, 45.0),
                new PlatoDto(5, 1L, "Ensalada César", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2300.0, List.of(new EtiquetaDto(1, "Proteica")), 400.0, 20.0, 15.0, 30.0),
                new PlatoDto(6, 1L, "Tarta de espinaca", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2000.0, List.of(new EtiquetaDto(2, "Vegetariana")), 450.0, 18.0, 22.0, 40.0)
        );
        List<Double> preciosEsperados = List.of(2000.0, 2300.0, 2500.0, 2800.0, 2900.0, 3200.0);


        List<PlatoDto> platosOrdenados = pedidoService.ordenarPlatos(platos, "menorAMayor");

        assertThat(platosOrdenados.get(0).getPrecio(), equalTo(preciosEsperados.get(0)));
        assertThat(platosOrdenados.get(5).getPrecio(), equalTo(preciosEsperados.get(5)));
    }

    @Test
    public void DadoQueExistenDiezPlatosConLaEtiquetaProteicaLosQuieroOrdenarDeMenorAMayor() {
        List<PlatoDto> platosTotales = List.of(
                new PlatoDto(1, 1L, "Milanesa con papas fritas", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2500.0, List.of(new EtiquetaDto(1, "Proteica")), 600.0, 45.0, 25.0, 50.0),
                new PlatoDto(4, 1L, "Hamburguesa completa", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2900.0, List.of(new EtiquetaDto(1, "Proteica")), 650.0, 40.0, 35.0, 45.0),
                new PlatoDto(5, 1L, "Ensalada César", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2300.0, List.of(new EtiquetaDto(1, "Proteica")), 400.0, 20.0, 15.0, 30.0),
                new PlatoDto(7, 1L, "Lasaña de carne", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 3100.0, List.of(new EtiquetaDto(1, "Proteica")), 680.0, 50.0, 28.0, 55.0),
                new PlatoDto(8, 1L, "Empanadas salteñas", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 1800.0, List.of(new EtiquetaDto(1, "Proteica")), 520.0, 35.0, 18.0, 45.0),
                new PlatoDto(9, 1L, "Pollo al horno con papas", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2600.0, List.of(new EtiquetaDto(1, "Proteica")), 600.0, 42.0, 20.0, 50.0),
                new PlatoDto(10, 1L, "Tacos de carne", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 3000.0, List.of(new EtiquetaDto(1, "Proteica")), 700.0, 48.0, 30.0, 60.0),
                new PlatoDto(13, 1L, "Milanesa napolitana", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 3200.0, List.of(new EtiquetaDto(1, "Proteica")),700.0, 48.0, 35.0, 55.0),
                new PlatoDto(16, 1L, "Churrasco", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 3500.0, List.of(new EtiquetaDto(1, "Proteica")),650.0, 50.0, 30.0, 40.0),
                new PlatoDto(18, 1L, "Sándwich de lomito", "...", "/d2f8a6e1-a7c3-4f09-8f7b-2cd1c3b63a88.jpg", 2800.0, List.of(new EtiquetaDto(1, "Proteica")),600.0, 45.0, 25.0, 50.0)
        );

        when(servicioPlato.buscarPlatosPorTipoComida("Proteica"))
                .thenReturn(platosTotales);

        List<Double> preciosEsperados = List.of(1800.0, 2300.0, 2500.0, 2600.0, 2800.0, 2900.0, 3000.0, 3100.0, 3200.0, 3500.0);

        List<PlatoDto> platosFiltrados = pedidoService.buscarPlatosPorTipoComida("Proteica");
        List<PlatoDto> platosOrdenados = pedidoService.ordenarPlatos(platosFiltrados, "menorAMayor");

        assertThat(platosOrdenados.get(0).getPrecio(), equalTo(preciosEsperados.get(0)));
        assertThat(platosOrdenados.get(9).getPrecio(), equalTo(preciosEsperados.get(9)));
    }

    @Test
    public void queSePuedaListarPedidosPorUsuarioYConvertirADto() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setFecha("2025-06-15");
        pedido.setPrecio(1000.0);
        pedido.setFinalizo(true);
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);

        Cliente usuario = new Cliente();
        usuario.setId(1L);
        pedido.setUsuario(usuario);

        pedido.setPedidoPlatos(new ArrayList<>());

        when(repositorioPedido.listarPedidosPorUsuario(1L))
                .thenReturn(List.of(pedido));

        List<PedidoDto> resultado = pedidoService.listarPedidosPorUsuario(1L);

        assertEquals(1, resultado.size());

        PedidoDto dto = resultado.get(0);
        assertEquals(1, dto.getId());
        assertEquals("2025-06-15", dto.getFecha());
        assertEquals(1000.0, dto.getPrecio(), 0.0001);
        assertEquals(EstadoPedido.PENDIENTE, dto.getEstadoPedido());
        assertNotNull(dto.getPedidoPlatos());
        assertTrue(dto.getPedidoPlatos().isEmpty());
    }

    @Test
    public void guardarCalificacion_deberiaActualizarCalificacionYGuardar() {
        Integer pedidoPlatoId = 10;
        Integer calificacion = 5;
        Long id = 1L;

        PedidoPlatoDto dto = new PedidoPlatoDto();
        dto.setId(pedidoPlatoId.longValue());
        dto.setCalificacion(null);

        when(servicioPedidoPlato.buscarPorId(pedidoPlatoId.longValue())).thenReturn(dto);

        pedidoService.guardarCalificacion(pedidoPlatoId, calificacion, id);

        assertEquals(calificacion, dto.getCalificacion());
    }


}