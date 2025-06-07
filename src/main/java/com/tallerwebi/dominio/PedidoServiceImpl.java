package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Plato;
import com.tallerwebi.dominio.entidades.Restaurante;
import com.tallerwebi.infraestructura.RepositorioPlatoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PedidoServiceImpl implements PedidoService {
    private final RepositorioPlatoImpl repositorioPlatoImpl;

    @Autowired
    public PedidoServiceImpl(RepositorioPlatoImpl repositorioPlatoImpl) {
        this.repositorioPlatoImpl = repositorioPlatoImpl;
    }
/*
        List<PlatoDto> platosDestacados = List.of(
                new PlatoDto(1,"Milanesa con papas fritas", "Clásica milanesa de carne acompañada con papas fritas crujientes.", "/assets/imagen-plato.png", 2500.0, List.of("Proteica")),
                new PlatoDto( 2,"Ravioles de ricota", "Ravioles caseros rellenos de ricota y nuez, servidos con salsa bolognesa.", "/assets/imagen-plato.png", 2800.0, List.of("Vegetariana")),
                new PlatoDto( 3,"Pizza napolitana", "Pizza con tomate, mozzarella, ajo, y albahaca fresca.", "/assets/imagen-plato.png", 3200.0, List.of("Vegetariana")),
                new PlatoDto( 4,"Hamburguesa completa", "Hamburguesa con lechuga, tomate, queso, panceta y papas fritas.", "/assets/imagen-plato.png", 2900.0, List.of("Proteica")),
                new PlatoDto( 5,"Ensalada César", "Ensalada con lechuga romana, pollo, crutones, parmesano y aderezo César.", "/assets/imagen-plato.png", 2300.0, List.of("Proteica")),
                new PlatoDto( 6,"Tarta de espinaca", "Tarta casera de espinaca y queso con masa hojaldrada.", "/assets/imagen-plato.png", 2000.0, List.of("Vegetariana"))
        );
/*
        List<PlatoDto> platosTotales = List.of(
                new PlatoDto( "Milanesa con papas fritas", "Clásica milanesa de carne acompañada con papas fritas crujientes.", "/assets/imagen-plato.png", 2500.0, List.of("Proteica")),
                new PlatoDto( "Ravioles de ricota", "Ravioles caseros rellenos de ricota y nuez, servidos con salsa bolognesa.", "/assets/imagen-plato.png", 2800.0, List.of("Vegetariana")),
                new PlatoDto( "Pizza napolitana", "Pizza con tomate, mozzarella, ajo y albahaca fresca.", "/assets/imagen-plato.png", 3200.0, List.of("Vegetariana")),
                new PlatoDto( "Hamburguesa completa", "Hamburguesa con lechuga, tomate, queso, panceta y papas fritas.", "/assets/imagen-plato.png", 2900.0, List.of("Proteica")),
                new PlatoDto( "Ensalada César", "Ensalada con lechuga romana, pollo, crutones, parmesano y aderezo César.", "/assets/imagen-plato.png", 2300.0, List.of("Proteica")),
                new PlatoDto( "Tarta de espinaca", "Tarta casera de espinaca y queso con masa hojaldrada.", "/assets/imagen-plato.png", 2000.0, List.of("Vegetariana")),
                new PlatoDto( "Lasaña de carne", "Capas de pasta con carne, salsa bechamel y queso gratinado.", "/assets/imagen-plato.png", 3100.0, List.of("Proteica")),
                new PlatoDto( "Empanadas salteñas", "Empanadas rellenas de carne cortada a cuchillo, típicas del norte argentino.", "/assets/imagen-plato.png", 1800.0, List.of("Proteica")),
                new PlatoDto( "Pollo al horno con papas", "Muslo de pollo al horno con papas doradas y especias.", "/assets/imagen-plato.png", 2600.0, List.of("Proteica")),
                new PlatoDto( "Ñoquis con salsa rosa", "Ñoquis de papa acompañados con salsa de tomate y crema.", "/assets/imagen-plato.png", 2700.0, List.of("Vegetariana")),
                new PlatoDto( "Sopa crema de calabaza", "Sopa suave y cremosa de calabaza natural.", "/assets/imagen-plato.png", 1900.0, List.of("Vegetariana", "Vegana", "Sin Gluten")),
                new PlatoDto( "Tacos de carne", "Tortillas mexicanas rellenas de carne, cebolla y cilantro.", "/assets/imagen-plato.png", 3000.0, List.of("Proteica")),
                new PlatoDto( "Milanesa napolitana", "Milanesa con jamón, queso y salsa de tomate, servida con papas.", "/assets/imagen-plato.png", 3200.0, List.of("Proteica")),
                new PlatoDto( "Pizza cuatro quesos", "Pizza con una mezcla de mozzarella, azul, provolone y parmesano.", "/assets/imagen-plato.png", 3400.0, List.of("Vegetariana")),
                new PlatoDto( "Panqueques con dulce de leche", "Postre de panqueques caseros rellenos con dulce de leche argentino.", "/assets/imagen-plato.png", 1600.0, List.of()),
                new PlatoDto( "Churrasco con ensalada", "Carne asada a la plancha con guarnición de ensalada fresca.", "/assets/imagen-plato.png", 3500.0, List.of("Proteica", "Sin Gluten")),
                new PlatoDto( "Fideos al pesto", "Fideos largos acompañados con salsa pesto casera.", "/assets/imagen-plato.png", 2500.0, List.of("Vegetariana")),
                new PlatoDto( "Sándwich de lomito", "Lomito con huevo, jamón, queso, lechuga y tomate en pan tostado.", "/assets/imagen-plato.png", 2800.0, List.of("Proteica")),
                new PlatoDto( "Canelones de verdura", "Pasta rellena de verdura con salsa blanca y gratinada al horno.", "/assets/imagen-plato.png", 3000.0, List.of("Vegetariana")),
                new PlatoDto( "Helado artesanal", "Helado de elaboración artesanal, sabores surtidos.", "/assets/imagen-plato.png", 1500.0, List.of("Sin Gluten"))
        );
*/

    List<Restaurante> restaurantes = List.of(
            new Restaurante("Green Bowl", "Comida Vegana", "/assets/restaurante.png", "calle", 123, "Don Torcuato", "Norte", List.of("Vegana")),
            new Restaurante("Natural Express", "Comida Vegana", "/assets/restaurante.png", "calle", 321, "La Matanza", "Oeste", List.of("Proteica")),
            new Restaurante("Vital Food", "Comida Proteica", "/assets/restaurante.png", "calle", 213, "La Matanza", "Oeste", List.of("Vegana", "Proteica")),
            new Restaurante("La Parrilla del Sur", "Especialidad en carnes a la parrilla", "/assets/restaurante-logo.png", "Av. Corrientes", 1234, "Buenos Aires", "Microcentro", List.of("Proteica")),
            new Restaurante("Sushi Zen", "Lo mejor de la cocina japonesa", "/assets/restaurante-logo.png", "Calle Defensa", 567, "Buenos Aires", "San Telmo", List.of("Proteica", "Sin gluten")),
            new Restaurante("Pizza Napoli", "Pizzas artesanales al horno de leña", "/assets/restaurante-logo.png", "Av. Santa Fe", 890, "Buenos Aires", "Recoleta", List.of("Opciones vegetarianas")),
            new Restaurante("Verde Vivo", "Comida saludable y vegana", "/assets/restaurante-logo.png", "Calle Mendoza", 234, "Mendoza", "Centro", List.of("Vegana", "Vegetariana", "Sin gluten"))
    );


    @Override
    public List<Restaurante> traerRestaurantesDestacados() {
        return this.restaurantes;
    }

    @Override
    public List<PlatoDto> traerPlatosDestacados() {

        return null;
    }

    @Override
    @Transactional
    public List<PlatoDto> traerTodosLosPlatos() {
        List<Plato> platos = this.repositorioPlatoImpl.traerTodosLosPlatos();
        return platos.stream().map(Plato::obtenerDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<PlatoDto> buscarPlatosPorTipoComida(String tipoComida) {
        List<Plato> platos = this.repositorioPlatoImpl.buscarPlatosPorTipoComida(tipoComida);
        return platos.stream().map(Plato::obtenerDto).collect(Collectors.toList());
    }

    public List<PlatoDto> ordenarPlatos(List<PlatoDto> platos, String tipoOrdenar) {
        List<PlatoDto> platosOrdenados = new ArrayList<>(platos);
        if (tipoOrdenar.equals("mayorAMenor")) {
            platosOrdenados.sort(Comparator.comparing(PlatoDto::getPrecio).reversed());
        } else {
            platosOrdenados.sort(Comparator.comparing(PlatoDto::getPrecio));
        }
        return platosOrdenados;
    }

/*        @Override
        public void guardarPlatoDb(Plato plato) {

        }

 */


}
