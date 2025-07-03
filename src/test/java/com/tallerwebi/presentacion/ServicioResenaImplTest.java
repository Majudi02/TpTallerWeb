package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.RepositorioResena;
import com.tallerwebi.dominio.RepositorioUsuarioNutriya;
import com.tallerwebi.dominio.RepositorioUsuarioRestaurante;
import com.tallerwebi.dominio.entidades.Cliente;
import com.tallerwebi.dominio.entidades.Resena;
import com.tallerwebi.dominio.entidades.Restaurante;
import com.tallerwebi.dominio.entidades.UsuarioRestaurante;
import com.tallerwebi.dominio.ServicioResenaImpl;
import com.tallerwebi.dominio.ServicioResena;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ServicioResenaImplTest {

    private RepositorioResena repositorioResenaMock;
    private RepositorioUsuarioNutriya repositorioUsuarioNutriyaMock;
    private RepositorioUsuarioRestaurante repositorioUsuarioRestauranteMock;

    private ServicioResena servicioResena;

    @BeforeEach
    public void init() {
        repositorioResenaMock = mock(RepositorioResena.class);
        repositorioUsuarioNutriyaMock = mock(RepositorioUsuarioNutriya.class);
        repositorioUsuarioRestauranteMock = mock(RepositorioUsuarioRestaurante.class);

        servicioResena = new ServicioResenaImpl(repositorioResenaMock, repositorioUsuarioNutriyaMock, repositorioUsuarioRestauranteMock);
    }

    @Test
    public void queSePuedaGuardarUnaResena() {
        Long clienteId = 1L;
        Long restauranteId = 2L;
        String comentario = "Muy sabroso";

        Cliente cliente = new Cliente();
        cliente.setId(clienteId);

        Restaurante restaurante = new Restaurante();
        restaurante.setId(restauranteId);

        when(repositorioUsuarioNutriyaMock.buscarPorId(clienteId)).thenReturn(cliente);
        when(repositorioUsuarioRestauranteMock.buscarRestaurantePorId(restauranteId)).thenReturn(restaurante);

        servicioResena.guardarResena(restauranteId, clienteId, comentario);

        verify(repositorioResenaMock).guardar(argThat(resena ->
                resena.getRestaurante().getId().equals(restauranteId) &&
                        resena.getCliente().getId().equals(clienteId) &&
                        resena.getComentario().equals("Muy sabroso")
        ));
    }


    @Test
    public void queSeObtenganLasResenasPorRestaurante() {
        Long restauranteId = 7L;

        Restaurante restaurante = new Restaurante();
        restaurante.setId(restauranteId);

        Cliente cliente1 = new Cliente();
        cliente1.setNombre("Ana");
        Cliente cliente2 = new Cliente();
        cliente2.setNombre("Juan");

        Resena resena1 = new Resena();
        resena1.setCliente(cliente1);
        resena1.setRestaurante(restaurante);
        resena1.setComentario("Muy rico");

        Resena resena2 = new Resena();
        resena2.setCliente(cliente2);
        resena2.setRestaurante(restaurante);
        resena2.setComentario("Excelente");

        List<Resena> resenas = List.of(resena1, resena2);

        when(repositorioResenaMock.obtenerResenasPorRestaurante(restauranteId)).thenReturn(resenas);

        List<Resena> resultado = servicioResena.obtenerResenasPorRestaurante(restauranteId);

        assertThat(resultado, hasSize(2));
        assertThat(resultado.get(0).getComentario(), is("Muy rico"));
        assertThat(resultado.get(1).getComentario(), is("Excelente"));
    }

    @Test
    public void queDevuelvaListaVaciaSiNoHayResenasParaElRestaurante() {
        Long restauranteId = 10L;
        when(repositorioResenaMock.obtenerResenasPorRestaurante(restauranteId))
                .thenReturn(List.of());

        List<Resena> resultado = servicioResena.obtenerResenasPorRestaurante(restauranteId);

        assertTrue(resultado.isEmpty(), "La lista de reseñas debería estar vacía");
        verify(repositorioResenaMock).obtenerResenasPorRestaurante(restauranteId);
    }

}
