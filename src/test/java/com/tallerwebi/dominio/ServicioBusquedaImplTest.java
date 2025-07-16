package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Plato;
import com.tallerwebi.dominio.entidades.Restaurante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioBusquedaImplTest {
    private RepositorioUsuarioRestaurante repositorioUsuarioRestauranteMock;
    private RepositorioPlato repositorioPlatoMock;
    private ServicioBusquedaImpl servicio;

    @BeforeEach
    void setUp() {
        repositorioUsuarioRestauranteMock = mock(RepositorioUsuarioRestaurante.class);
        repositorioPlatoMock = mock(RepositorioPlato.class);
        servicio = new ServicioBusquedaImpl(repositorioUsuarioRestauranteMock, repositorioPlatoMock);
    }

    @Test
    void buscarRestaurantesYPlatosConTextoPizzaDevuelveSoloRestaurantesYPlatosConPizza() {
        // inicialización
        Restaurante restaurante1 = mock(Restaurante.class);
        when(restaurante1.getNombre()).thenReturn("Pizza Napoli");
        when(restaurante1.getDescripcion()).thenReturn("Comida italiana");
        RestauranteDto restauranteDto1 = new RestauranteDto();
        restauranteDto1.setNombre("Pizza Napoli");
        when(restaurante1.obtenerDto()).thenReturn(restauranteDto1);

        Restaurante restaurante2 = mock(Restaurante.class);
        when(restaurante2.getNombre()).thenReturn("Burger House");
        when(restaurante2.getDescripcion()).thenReturn("Hamburguesas y más");
        RestauranteDto restauranteDto2 = new RestauranteDto();
        restauranteDto2.setNombre("Burger House");
        when(restaurante2.obtenerDto()).thenReturn(restauranteDto2);

        when(repositorioUsuarioRestauranteMock.buscarTodosLosRestaurantes())
                .thenReturn(Arrays.asList(restaurante1, restaurante2));

        Plato plato1 = mock(Plato.class);
        when(plato1.getNombre()).thenReturn("Pizza Vegana");
        when(plato1.getDescripcion()).thenReturn("Con queso vegano");
        PlatoDto platoDto1 = new PlatoDto();
        platoDto1.setNombre("Pizza Vegana");
        when(plato1.obtenerDto()).thenReturn(platoDto1);

        Plato plato2 = mock(Plato.class);
        when(plato2.getNombre()).thenReturn("Empanada");
        when(plato2.getDescripcion()).thenReturn("Carne cortada a cuchillo");
        PlatoDto platoDto2 = new PlatoDto();
        platoDto2.setNombre("Empanada");
        when(plato2.obtenerDto()).thenReturn(platoDto2);

        when(repositorioPlatoMock.traerTodosLosPlatos())
                .thenReturn(Arrays.asList(plato1, plato2));

        // ejecución
        BusquedaResultadoDto resultado = servicio.buscarRestaurantesYPlatos("pizza");

        // verificación
        assertNotNull(resultado);

        assertEquals(1, resultado.getRestaurantes().size());
        assertEquals("Pizza Napoli", resultado.getRestaurantes().get(0).getNombre());

        assertEquals(1, resultado.getPlatos().size());
        assertEquals("Pizza Vegana", resultado.getPlatos().get(0).getNombre());
    }

    @Test
    void buscarRestaurantesYPlatosSinCoincidenciasDevuelveListasVacias() {
        when(repositorioUsuarioRestauranteMock.buscarTodosLosRestaurantes())
                .thenReturn(new ArrayList<>());
        when(repositorioPlatoMock.traerTodosLosPlatos())
                .thenReturn(new ArrayList<>());

        BusquedaResultadoDto resultado = servicio.buscarRestaurantesYPlatos("abc");

        assertNotNull(resultado);
        assertTrue(resultado.getRestaurantes().isEmpty());
        assertTrue(resultado.getPlatos().isEmpty());
    }

    @Test
    void obtenerSugerenciasDevuelveSugerenciasCorrectasParaElTextoIngresado() {
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNombre("Sushi Roll");
        restaurante.setDescripcion("Comida japonesa");

        Plato plato = new Plato();
        plato.setId(5);
        plato.setNombre("Sushi Especial");
        plato.setDescripcion("Con salmón y palta");

        when(repositorioUsuarioRestauranteMock.buscarTodosLosRestaurantes())
                .thenReturn(new ArrayList<>(List.of(restaurante)));
        when(repositorioPlatoMock.traerTodosLosPlatos())
                .thenReturn(new ArrayList<>(List.of(plato)));

        List<SugerenciaDto> sugerencias = servicio.obtenerSugerencias("sus");

        assertNotNull(sugerencias);
        assertEquals(2, sugerencias.size());

        boolean tieneRestaurante = false;
        for (SugerenciaDto s : sugerencias) {
            if (s.getTexto().equals("Sushi Roll") && s.getTipo().equals("restaurante") && s.getId() == 1L) {
                tieneRestaurante = true;
                break;
            }
        }

        boolean tienePlato = false;
        for (SugerenciaDto s : sugerencias) {
            if (s.getTexto().equals("Sushi Especial") && s.getTipo().equals("plato") && s.getId() == 5L) {
                tienePlato = true;
                break;
            }
        }

        assertTrue(tieneRestaurante);
        assertTrue(tienePlato);
    }

    @Test
    void obtenerSugerenciasDevuelveUnaListaVaciaSiNoHayCoincidencias() {
        when(repositorioUsuarioRestauranteMock.buscarTodosLosRestaurantes())
                .thenReturn(new ArrayList<>());
        when(repositorioPlatoMock.traerTodosLosPlatos())
                .thenReturn(new ArrayList<>());

        List<SugerenciaDto> sugerencias = servicio.obtenerSugerencias("abc");

        assertNotNull(sugerencias);
        assertTrue(sugerencias.isEmpty());
    }
}
