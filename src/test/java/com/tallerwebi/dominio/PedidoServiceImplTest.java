package com.tallerwebi.dominio;


import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

public class PedidoServiceImplTest {

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


        PedidoServiceImpl pedidoService = new PedidoServiceImpl();

        List<Restaurante> restaurantesObtenidos = pedidoService.traerRestaurantesDestacados();

        assertThat(restaurantesObtenidos.size(), equalTo(restaurantesEsperados.size()));
    }


    @Test
    public void dadoQueHayPlatosDestacadosLosQuieroMostrarEnLaPantalla() {
        List<Plato> platos = List.of(
                new Plato("Milanesa con papas fritas", "Clásica milanesa de carne acompañada con papas fritas crujientes.", "/assets/imagen-plato.png", 2500.0),
                new Plato("Ravioles de ricota", "Ravioles caseros rellenos de ricota y nuez, servidos con salsa bolognesa.", "/assets/imagen-plato.png", 2800.0),
                new Plato("Pizza napolitana", "Pizza con tomate, mozzarella, ajo, y albahaca fresca.", "/assets/imagen-plato.png", 3200.0),
                new Plato("Hamburguesa completa", "Hamburguesa con lechuga, tomate, queso, panceta y papas fritas.", "/assets/imagen-plato.png", 2900.0),
                new Plato("Ensalada César", "Ensalada con lechuga romana, pollo, crutones, parmesano y aderezo César.", "/assets/imagen-plato.png", 2300.0),
                new Plato("Tarta de espinaca", "Tarta casera de espinaca y queso con masa hojaldrada.", "/assets/imagen-plato.png", 2000.0)
        );

        PedidoServiceImpl pedidoService = new PedidoServiceImpl();

        List<Plato> platosObtenidos = pedidoService.traerPlatosDestacados();

        assertThat(platosObtenidos.size(), equalTo(platos.size()));
    }

}
