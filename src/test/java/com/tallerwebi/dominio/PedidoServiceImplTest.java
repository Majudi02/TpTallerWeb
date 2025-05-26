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
                new Plato("Milanesa con papas fritas", "Clásica milanesa de carne acompañada con papas fritas crujientes.", "/assets/imagen-plato.png", 2500.0, List.of("Proteica")),
                new Plato("Ravioles de ricota", "Ravioles caseros rellenos de ricota y nuez, servidos con salsa bolognesa.", "/assets/imagen-plato.png", 2800.0, List.of("Vegetariana")),
                new Plato("Pizza napolitana", "Pizza con tomate, mozzarella, ajo, y albahaca fresca.", "/assets/imagen-plato.png", 3200.0, List.of("Vegetariana")),
                new Plato("Hamburguesa completa", "Hamburguesa con lechuga, tomate, queso, panceta y papas fritas.", "/assets/imagen-plato.png", 2900.0, List.of("Proteica")),
                new Plato("Ensalada César", "Ensalada con lechuga romana, pollo, crutones, parmesano y aderezo César.", "/assets/imagen-plato.png", 2300.0, List.of("Proteica")),
                new Plato("Tarta de espinaca", "Tarta casera de espinaca y queso con masa hojaldrada.", "/assets/imagen-plato.png", 2000.0, List.of("Vegetariana"))
        );

        PedidoServiceImpl pedidoService = new PedidoServiceImpl();

        List<Plato> platosObtenidos = pedidoService.traerPlatosDestacados();

        assertThat(platosObtenidos.size(), equalTo(platos.size()));
    }

    @Test
    public void DadoQueExistenDiezPlatosConLaEtiquetaProteicaObtengoSoloLosDelTipoDeComidaFiltrada(){
        List<Plato> platosTotales = List.of(
                new Plato("Milanesa con papas fritas", "Clásica milanesa de carne acompañada con papas fritas crujientes.", "/assets/imagen-plato.png", 2500.0, List.of("Proteica")),
                new Plato("Ravioles de ricota", "Ravioles caseros rellenos de ricota y nuez, servidos con salsa bolognesa.", "/assets/imagen-plato.png", 2800.0, List.of("Vegetariana")),
                new Plato("Pizza napolitana", "Pizza con tomate, mozzarella, ajo y albahaca fresca.", "/assets/imagen-plato.png", 3200.0, List.of("Vegetariana")),
                new Plato("Hamburguesa completa", "Hamburguesa con lechuga, tomate, queso, panceta y papas fritas.", "/assets/imagen-plato.png", 2900.0, List.of("Proteica")),
                new Plato("Ensalada César", "Ensalada con lechuga romana, pollo, crutones, parmesano y aderezo César.", "/assets/imagen-plato.png", 2300.0, List.of("Proteica")),
                new Plato("Tarta de espinaca", "Tarta casera de espinaca y queso con masa hojaldrada.", "/assets/imagen-plato.png", 2000.0, List.of("Vegetariana")),
                new Plato("Lasaña de carne", "Capas de pasta con carne, salsa bechamel y queso gratinado.", "/assets/imagen-plato.png", 3100.0, List.of("Proteica")),
                new Plato("Empanadas salteñas", "Empanadas rellenas de carne cortada a cuchillo, típicas del norte argentino.", "/assets/imagen-plato.png", 1800.0, List.of("Proteica")),
                new Plato("Pollo al horno con papas", "Muslo de pollo al horno con papas doradas y especias.", "/assets/imagen-plato.png", 2600.0, List.of("Proteica")),
                new Plato("Ñoquis con salsa rosa", "Ñoquis de papa acompañados con salsa de tomate y crema.", "/assets/imagen-plato.png", 2700.0, List.of("Vegetariana")),
                new Plato("Sopa crema de calabaza", "Sopa suave y cremosa de calabaza natural.", "/assets/imagen-plato.png", 1900.0, List.of("Vegetariana", "Vegana", "Sin Gluten")),
                new Plato("Tacos de carne", "Tortillas mexicanas rellenas de carne, cebolla y cilantro.", "/assets/imagen-plato.png", 3000.0, List.of("Proteica")),
                new Plato("Milanesa napolitana", "Milanesa con jamón, queso y salsa de tomate, servida con papas.", "/assets/imagen-plato.png", 3200.0, List.of("Proteica")),
                new Plato("Pizza cuatro quesos", "Pizza con una mezcla de mozzarella, azul, provolone y parmesano.", "/assets/imagen-plato.png", 3400.0, List.of("Vegetariana")),
                new Plato("Panqueques con dulce de leche", "Postre de panqueques caseros rellenos con dulce de leche argentino.", "/assets/imagen-plato.png", 1600.0, List.of()),
                new Plato("Churrasco con ensalada", "Carne asada a la plancha con guarnición de ensalada fresca.", "/assets/imagen-plato.png", 3500.0, List.of("Proteica", "Sin Gluten")),
                new Plato("Fideos al pesto", "Fideos largos acompañados con salsa pesto casera.", "/assets/imagen-plato.png", 2500.0, List.of("Vegetariana")),
                new Plato("Sándwich de lomito", "Lomito con huevo, jamón, queso, lechuga y tomate en pan tostado.", "/assets/imagen-plato.png", 2800.0, List.of("Proteica")),
                new Plato("Canelones de verdura", "Pasta rellena de verdura con salsa blanca y gratinada al horno.", "/assets/imagen-plato.png", 3000.0, List.of("Vegetariana")),
                new Plato("Helado artesanal", "Helado de elaboración artesanal, sabores surtidos.", "/assets/imagen-plato.png", 1500.0, List.of("Sin Gluten"))
        );

        PedidoServiceImpl pedidoService = new PedidoServiceImpl();

        List<Plato> platosFiltrados = pedidoService.buscarPlatosPorTipoComida("Proteica");

        assertThat(platosFiltrados.size(), equalTo(10));
    }

    @Test
    public void DadoQueExisten6PlatosLosQuierOrdenarPorElPrecioDeMayorAMenor(){
        List<Plato> platos = List.of(
                new Plato("Milanesa con papas fritas", "Clásica milanesa de carne acompañada con papas fritas crujientes.", "/assets/imagen-plato.png", 2500.0, List.of("Proteica")),
                new Plato("Ravioles de ricota", "Ravioles caseros rellenos de ricota y nuez, servidos con salsa bolognesa.", "/assets/imagen-plato.png", 2800.0, List.of("Vegetariana")),
                new Plato("Pizza napolitana", "Pizza con tomate, mozzarella, ajo, y albahaca fresca.", "/assets/imagen-plato.png", 3200.0, List.of("Vegetariana")),
                new Plato("Hamburguesa completa", "Hamburguesa con lechuga, tomate, queso, panceta y papas fritas.", "/assets/imagen-plato.png", 2900.0, List.of("Proteica")),
                new Plato("Ensalada César", "Ensalada con lechuga romana, pollo, crutones, parmesano y aderezo César.", "/assets/imagen-plato.png", 2300.0, List.of("Proteica")),
                new Plato("Tarta de espinaca", "Tarta casera de espinaca y queso con masa hojaldrada.", "/assets/imagen-plato.png", 2000.0, List.of("Vegetariana"))
        );
        List<Double> preciosEsperados = List.of(3200.0, 2900.0, 2800.0, 2500.0, 2300.0, 2000.0);

        PedidoServiceImpl pedidoService = new PedidoServiceImpl();

        List<Plato> platosOrdenados = pedidoService.ordenarPlatos(platos,"mayorAMenor");

        assertThat(platosOrdenados.get(0).getPrecio(), equalTo(preciosEsperados.get(0)));
        assertThat(platosOrdenados.get(5).getPrecio(), equalTo(preciosEsperados.get(5)));
    }

    @Test
    public void DadoQueExisten6PlatosLosQuierOrdenarPorElPrecioDeMenorAMayor(){
        List<Plato> platos = List.of(
                new Plato("Milanesa con papas fritas", "Clásica milanesa de carne acompañada con papas fritas crujientes.", "/assets/imagen-plato.png", 2500.0, List.of("Proteica")),
                new Plato("Ravioles de ricota", "Ravioles caseros rellenos de ricota y nuez, servidos con salsa bolognesa.", "/assets/imagen-plato.png", 2800.0, List.of("Vegetariana")),
                new Plato("Pizza napolitana", "Pizza con tomate, mozzarella, ajo, y albahaca fresca.", "/assets/imagen-plato.png", 3200.0, List.of("Vegetariana")),
                new Plato("Hamburguesa completa", "Hamburguesa con lechuga, tomate, queso, panceta y papas fritas.", "/assets/imagen-plato.png", 2900.0, List.of("Proteica")),
                new Plato("Ensalada César", "Ensalada con lechuga romana, pollo, crutones, parmesano y aderezo César.", "/assets/imagen-plato.png", 2300.0, List.of("Proteica")),
                new Plato("Tarta de espinaca", "Tarta casera de espinaca y queso con masa hojaldrada.", "/assets/imagen-plato.png", 2000.0, List.of("Vegetariana"))
        );
        List<Double> preciosEsperados = List.of(2000.0, 2300.0, 2500.0, 2800.0, 2900.0, 3200.0);

        PedidoServiceImpl pedidoService = new PedidoServiceImpl();

        List<Plato> platosOrdenados = pedidoService.ordenarPlatos(platos,"menorAMayor");

        assertThat(platosOrdenados.get(0).getPrecio(), equalTo(preciosEsperados.get(0)));
        assertThat(platosOrdenados.get(5).getPrecio(), equalTo(preciosEsperados.get(5)));
    }

    @Test
    public void DadoQueExistenDiezPlatosConLaEtiquetaProteicaLosQuieroOrdenarDeMenorAMayor(){
        List<Plato> platosTotales = List.of(
                new Plato("Milanesa con papas fritas", "Clásica milanesa de carne acompañada con papas fritas crujientes.", "/assets/imagen-plato.png", 2500.0, List.of("Proteica")),
                new Plato("Ravioles de ricota", "Ravioles caseros rellenos de ricota y nuez, servidos con salsa bolognesa.", "/assets/imagen-plato.png", 2800.0, List.of("Vegetariana")),
                new Plato("Pizza napolitana", "Pizza con tomate, mozzarella, ajo y albahaca fresca.", "/assets/imagen-plato.png", 3200.0, List.of("Vegetariana")),
                new Plato("Hamburguesa completa", "Hamburguesa con lechuga, tomate, queso, panceta y papas fritas.", "/assets/imagen-plato.png", 2900.0, List.of("Proteica")),
                new Plato("Ensalada César", "Ensalada con lechuga romana, pollo, crutones, parmesano y aderezo César.", "/assets/imagen-plato.png", 2300.0, List.of("Proteica")),
                new Plato("Tarta de espinaca", "Tarta casera de espinaca y queso con masa hojaldrada.", "/assets/imagen-plato.png", 2000.0, List.of("Vegetariana")),
                new Plato("Lasaña de carne", "Capas de pasta con carne, salsa bechamel y queso gratinado.", "/assets/imagen-plato.png", 3100.0, List.of("Proteica")),
                new Plato("Empanadas salteñas", "Empanadas rellenas de carne cortada a cuchillo, típicas del norte argentino.", "/assets/imagen-plato.png", 1800.0, List.of("Proteica")),
                new Plato("Pollo al horno con papas", "Muslo de pollo al horno con papas doradas y especias.", "/assets/imagen-plato.png", 2600.0, List.of("Proteica")),
                new Plato("Ñoquis con salsa rosa", "Ñoquis de papa acompañados con salsa de tomate y crema.", "/assets/imagen-plato.png", 2700.0, List.of("Vegetariana")),
                new Plato("Sopa crema de calabaza", "Sopa suave y cremosa de calabaza natural.", "/assets/imagen-plato.png", 1900.0, List.of("Vegetariana", "Vegana", "Sin Gluten")),
                new Plato("Tacos de carne", "Tortillas mexicanas rellenas de carne, cebolla y cilantro.", "/assets/imagen-plato.png", 3000.0, List.of("Proteica")),
                new Plato("Milanesa napolitana", "Milanesa con jamón, queso y salsa de tomate, servida con papas.", "/assets/imagen-plato.png", 3200.0, List.of("Proteica")),
                new Plato("Pizza cuatro quesos", "Pizza con una mezcla de mozzarella, azul, provolone y parmesano.", "/assets/imagen-plato.png", 3400.0, List.of("Vegetariana")),
                new Plato("Panqueques con dulce de leche", "Postre de panqueques caseros rellenos con dulce de leche argentino.", "/assets/imagen-plato.png", 1600.0, List.of()),
                new Plato("Churrasco con ensalada", "Carne asada a la plancha con guarnición de ensalada fresca.", "/assets/imagen-plato.png", 3500.0, List.of("Proteica", "Sin Gluten")),
                new Plato("Fideos al pesto", "Fideos largos acompañados con salsa pesto casera.", "/assets/imagen-plato.png", 2500.0, List.of("Vegetariana")),
                new Plato("Sándwich de lomito", "Lomito con huevo, jamón, queso, lechuga y tomate en pan tostado.", "/assets/imagen-plato.png", 2800.0, List.of("Proteica")),
                new Plato("Canelones de verdura", "Pasta rellena de verdura con salsa blanca y gratinada al horno.", "/assets/imagen-plato.png", 3000.0, List.of("Vegetariana")),
                new Plato("Helado artesanal", "Helado de elaboración artesanal, sabores surtidos.", "/assets/imagen-plato.png", 1500.0, List.of("Sin Gluten"))
        );

        PedidoServiceImpl pedidoService = new PedidoServiceImpl();
        List<Double> preciosEsperados = List.of(1800.0, 2300.0, 2500.0, 2600.0, 2800.0, 2900.0, 3000.0, 3100.0, 3200.0, 3500.0);

        List<Plato> platosFiltrados = pedidoService.buscarPlatosPorTipoComida("Proteica");

        List<Plato> platosOrdenados = pedidoService.ordenarPlatos(platosFiltrados,"menorAMayor");

        assertThat(platosOrdenados.get(0).getPrecio(),equalTo(preciosEsperados.get(0)));
        assertThat(platosOrdenados.get(9).getPrecio(),equalTo(preciosEsperados.get(9)));
    }

}
