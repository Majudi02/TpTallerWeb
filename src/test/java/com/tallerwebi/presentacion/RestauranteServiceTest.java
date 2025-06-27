package com.tallerwebi.presentacion;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.Restaurante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/*
public class RestauranteServiceTest {
    private RepositorioUsuarioRestaurante repositorioUsuarioRestauranteMock;
    private RepositorioPlato repositorioPlatoMock;
    private ServicioRestauranteImpl servicio;
    private EtiquetaService etiquetaServiceMock;
    ;

    @BeforeEach
    public void setup() {
        repositorioUsuarioRestauranteMock = mock(RepositorioUsuarioRestaurante.class);
        repositorioPlatoMock = mock(RepositorioPlato.class);
        etiquetaServiceMock = mock(EtiquetaService.class);

        // Datos controlados para el test
        List<Restaurante> restaurantesMock = List.of(
                new Restaurante("Green Bowl", "Comida Vegana", "/assets/restaurante.png", "calle", 123, "Don Torcuato", "Norte", List.of("Vegana")),
                new Restaurante("Vital Food", "Comida Proteica", "/assets/restaurante.png", "calle", 213, "La Matanza", "Oeste", List.of("Vegana", "Proteica")),
                new Restaurante("Natural Express", "Comida Vegana", "/assets/restaurante.png", "calle", 321, "La Matanza", "Oeste", List.of("Proteica"))
        );

        when(repositorioUsuarioRestauranteMock.buscarTodosLosRestaurantes()).thenReturn(restaurantesMock);

        servicio = new ServicioRestauranteImpl(repositorioUsuarioRestauranteMock, repositorioPlatoMock, etiquetaServiceMock)
        ;
    }

    @Test
    public void dadoQueTengoUnServicioRestaurantePuedoObtenerUnRestauranteExistente() {
        Restaurante r = servicio.obtenerRestaurante("Green Bowl");
        assertNotNull(r);
        assertEquals("Green Bowl", r.getNombre());
    }

    @Test
    public void dadoQueTengoUnServicioRestauranteObtengoNullSiNoExisteUnRestaurante() {
        Restaurante r = servicio.obtenerRestaurante("McDonalds");
        assertNull(r);
    }

    @Test
    public void dadoQueTengoUnServicioRestaurantePuedoObtenerRestaurantesPorZona() {
        List<Restaurante> lista = servicio.obtenerRestaurantesPorZona("Oeste");

        boolean todasSonDeOeste = true;
        for (Restaurante r : lista) {
            if (!r.getZona().equalsIgnoreCase("Oeste")) {
                todasSonDeOeste = false;
                break;
            }
        }

        assertEquals(2, lista.size());
        assertTrue(todasSonDeOeste);
    }

    @Test
    public void dadoQueTengoUnServicioRestaurantePuedoObtenerRestaurantesPorTipoComida() {
        List<Restaurante> lista = servicio.buscarPorTipoComida("Vegana");

        boolean todasSonVeganas = true;
        for (Restaurante r : lista) {
            if (!r.getTiposComida().contains("Vegana")) {
                todasSonVeganas = false;
                break;
            }
        }
        assertTrue(todasSonVeganas);
        assertEquals(2, lista.size());
    }

    @Test
    public void dadoQueTengoUnServicioRestaurantePuedoBuscarPorTipoComidaYZona() {
        // Busco zona Oeste y tipo Proteica (debería dar solo 2 restaurantes: Vital Food y Natural Express)
        List<Restaurante> resultado = servicio.buscarPorTipoComidaYZona("Oeste", "Proteica");

        boolean todasSonDeOesteYProteica = true;
        for (Restaurante r : resultado) {
            if (!r.getZona().equalsIgnoreCase("Oeste") && !r.getTiposComida().contains("Proteica")) {
                todasSonDeOesteYProteica = false;
                break;
            }
        }

        assertEquals(2, resultado.size());
        assertTrue(todasSonDeOesteYProteica);
    }

    @Test
    public void dadoQueTengoUnServicioRestaurantePuedoObtenerTodosLosRestaurantes() {
        List<Restaurante> lista = servicio.obtenerRestaurantes();
        assertEquals(3, lista.size());
    }
}


 */