package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Restaurante;
import com.tallerwebi.dominio.ServicioRestaurante;
import com.tallerwebi.dominio.ServicioRestauranteImpl;
import com.tallerwebi.dominio.TipoComida;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RestauranteServiceTest {

    @Test
    public void DadoQueExisteUnServicioRestauranteSePuedeAgregarUno() {
        ServicioRestaurante servicioRestaurante = new ServicioRestauranteImpl();
        TipoComida vegana = new TipoComida(1, "Vegana");
        Restaurante restaurante1 = new Restaurante("Green Bowl", "Comida Vegana", "core/images/restaurante.png", "calle", 123, "Don Torcuato", "Norte", List.of(vegana));

        Boolean agregado = servicioRestaurante.agregarRestaurante(restaurante1);
        Restaurante restauranteObtenido = servicioRestaurante.obtenerRestaurante("Green Bowl");


        assertThat(agregado, is(true));
        assertThat(restauranteObtenido, equalTo(restaurante1));
    }

    @Test
    public void DadoQueExistenRestaurantesNoSePuedeAgregarUnoConLaMismaCalleYElMismoNumero() {
        ServicioRestaurante servicioRestaurante = new ServicioRestauranteImpl();
        TipoComida vegana = new TipoComida(1, "Vegana");
        TipoComida proteica = new TipoComida(2, "Proteica");

        Restaurante restaurante1 = new Restaurante("Green Bowl", "Comida Vegana", "core/images/restaurante.png", "calle", 123, "Don Torcuato", "Norte", List.of(vegana));
        Restaurante restaurante2 = new Restaurante("Natural Express", "Comida Vegana", "core/images/restaurante.png", "calle", 123, "La Matanza", "Oeste", List.of(proteica));

        servicioRestaurante.agregarRestaurante(restaurante1);
        Boolean agregado = servicioRestaurante.agregarRestaurante(restaurante2);

        assertThat(agregado, is(false));
    }


    @Test
    public void DadoQueExistenCincoRestaurantesLosObtengo() {
        ServicioRestaurante servicioRestaurante = new ServicioRestauranteImpl();

        TipoComida vegana = new TipoComida(1, "Vegana");
        TipoComida proteica = new TipoComida(2, "Proteica");
        TipoComida sinGluten = new TipoComida(3, "Sin Gluten");

        Restaurante restaurante1 = new Restaurante("Green Bowl", "Comida Vegana", "core/images/restaurante.png", "calle", 123, "Don Torcuato", "Norte", List.of(vegana));
        Restaurante restaurante2 = new Restaurante("Natural Express", "Comida Vegana", "core/images/restaurante.png", "calle", 321, "La Matanza", "Oeste", List.of(proteica));
        Restaurante restaurante3 = new Restaurante("Vital Food", "Comida Vegana", "core/images/restaurante.png", "calle", 213, "La Matanza", "Oeste", List.of(vegana, proteica));
        Restaurante restaurante4 = new Restaurante("Greem Bowl", "Comida Vegana", "core/images/restaurante.png", "calle", 231, "La Matanza", "Oeste", List.of(vegana));
        Restaurante restaurante5 = new Restaurante("Vital Food", "Comida Vegana", "core/images/restaurante.png", "calle", 345, "Banfield", "Sur", List.of(sinGluten));

        servicioRestaurante.agregarRestaurante(restaurante1);
        servicioRestaurante.agregarRestaurante(restaurante2);
        servicioRestaurante.agregarRestaurante(restaurante3);
        servicioRestaurante.agregarRestaurante(restaurante4);
        servicioRestaurante.agregarRestaurante(restaurante5);

        List<Restaurante> restaurantesObtenidos = servicioRestaurante.obtenerRestaurantes();

        assertThat(restaurantesObtenidos.size(), is(5));
    }

    @Test
    public void DadoQueExistenCincoRestaurantesObtengoSoloLosDeLaZonaFiltrada() {
        ServicioRestaurante servicioRestaurante = new ServicioRestauranteImpl();

        TipoComida vegana = new TipoComida(1, "Vegana");
        TipoComida proteica = new TipoComida(2, "Proteica");
        TipoComida sinGluten = new TipoComida(3, "Sin Gluten");


        Restaurante restaurante1 = new Restaurante("Green Bowl", "Comida Vegana", "core/images/restaurante.png", "calle", 123, "Don Torcuato", "Norte", List.of(vegana));
        Restaurante restaurante2 = new Restaurante("Natural Express", "Comida Vegana", "core/images/restaurante.png", "calle", 321, "La Matanza", "Oeste", List.of(proteica));
        Restaurante restaurante3 = new Restaurante("Vital Food", "Comida Vegana", "core/images/restaurante.png", "calle", 213, "La Matanza", "Oeste", List.of(vegana, proteica));
        Restaurante restaurante4 = new Restaurante("Greem Bowl", "Comida Vegana", "core/images/restaurante.png", "calle", 231, "La Matanza", "Oeste", List.of(vegana));
        Restaurante restaurante5 = new Restaurante("Vital Food", "Comida Vegana", "core/images/restaurante.png", "calle", 345, "Banfield", "Sur", List.of(sinGluten));

        servicioRestaurante.agregarRestaurante(restaurante1);
        servicioRestaurante.agregarRestaurante(restaurante2);
        servicioRestaurante.agregarRestaurante(restaurante3);
        servicioRestaurante.agregarRestaurante(restaurante4);
        servicioRestaurante.agregarRestaurante(restaurante5);

        List<Restaurante> restaurantesObtenidos = servicioRestaurante.obtenerRestaurantesPorZona("Oeste");

        assertThat(restaurantesObtenidos.size(), is(3));
    }

    @Test
    public void DadoQueExistenCincoRestaurantesObtengoSoloLosDelTipoDeComidaFiltrada() {
        ServicioRestaurante servicioRestaurante = new ServicioRestauranteImpl();

        TipoComida vegana = new TipoComida(1, "Vegana");
        TipoComida proteica = new TipoComida(2, "Proteica");
        TipoComida sinGluten = new TipoComida(3, "Sin Gluten");

        Restaurante restaurante1 = new Restaurante("Green Bowl", "Comida Vegana", "core/images/restaurante.png", "calle", 123, "Don Torcuato", "Norte", List.of(vegana));
        Restaurante restaurante2 = new Restaurante("Natural Express", "Comida Vegana", "core/images/restaurante.png", "calle", 321, "La Matanza", "Oeste", List.of(proteica));
        Restaurante restaurante3 = new Restaurante("Vital Food", "Comida Vegana", "core/images/restaurante.png", "calle", 213, "La Matanza", "Oeste", List.of(vegana, proteica));
        Restaurante restaurante4 = new Restaurante("Greem Bowl", "Comida Vegana", "core/images/restaurante.png", "calle", 231, "La Matanza", "Oeste", List.of(vegana));
        Restaurante restaurante5 = new Restaurante("Vital Food", "Comida Vegana", "core/images/restaurante.png", "calle", 345, "Banfield", "Sur", List.of(sinGluten));

        servicioRestaurante.agregarRestaurante(restaurante1);
        servicioRestaurante.agregarRestaurante(restaurante2);
        servicioRestaurante.agregarRestaurante(restaurante3);
        servicioRestaurante.agregarRestaurante(restaurante4);
        servicioRestaurante.agregarRestaurante(restaurante5);

        List<Restaurante> restaurantesObtenidos = servicioRestaurante.buscarPorTipoComida("Vegana");

        assertThat(restaurantesObtenidos.size(), is(3));
    }

    @Test
    public void DadoQueExistenCincoRestaurantesObtengoSoloLosDelTipoDeComidaYZonaFiltrada() {
        ServicioRestaurante servicioRestaurante = new ServicioRestauranteImpl();

        TipoComida vegana = new TipoComida(1, "Vegana");
        TipoComida proteica = new TipoComida(2, "Proteica");
        TipoComida sinGluten = new TipoComida(3, "Sin Gluten");

        Restaurante restaurante1 = new Restaurante("Green Bowl", "Comida Vegana", "core/images/restaurante.png", "calle", 123, "Don Torcuato", "Norte", List.of(vegana));
        Restaurante restaurante2 = new Restaurante("Natural Express", "Comida Vegana", "core/images/restaurante.png", "calle", 321, "La Matanza", "Oeste", List.of(proteica));
        Restaurante restaurante3 = new Restaurante("Vital Food", "Comida Vegana", "core/images/restaurante.png", "calle", 213, "La Matanza", "Oeste", List.of(vegana, proteica));
        Restaurante restaurante4 = new Restaurante("Greem Bowl", "Comida Vegana", "core/images/restaurante.png", "calle", 231, "La Matanza", "Oeste", List.of(vegana));
        Restaurante restaurante5 = new Restaurante("Vital Food", "Comida Vegana", "core/images/restaurante.png", "calle", 345, "Banfield", "Sur", List.of(sinGluten));

        servicioRestaurante.agregarRestaurante(restaurante1);
        servicioRestaurante.agregarRestaurante(restaurante2);
        servicioRestaurante.agregarRestaurante(restaurante3);
        servicioRestaurante.agregarRestaurante(restaurante4);
        servicioRestaurante.agregarRestaurante(restaurante5);

        List<Restaurante> restaurantesObtenidos = servicioRestaurante.buscarPorTipoComidaYZona("Oeste", "Vegana");

        assertThat(restaurantesObtenidos.size(), is(2));
    }


}
