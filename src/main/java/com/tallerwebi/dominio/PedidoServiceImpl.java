package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoServiceImpl  implements PedidoService{


    List<Plato> platosDestacados = List.of(
            new Plato("Milanesa con papas fritas", "Clásica milanesa de carne acompañada con papas fritas crujientes.", "/assets/imagen-plato.png", 2500.0),
            new Plato("Ravioles de ricota", "Ravioles caseros rellenos de ricota y nuez, servidos con salsa bolognesa.", "/assets/imagen-plato.png", 2800.0),
            new Plato("Pizza napolitana", "Pizza con tomate, mozzarella, ajo, y albahaca fresca.", "/assets/imagen-plato.png", 3200.0),
            new Plato("Hamburguesa completa", "Hamburguesa con lechuga, tomate, queso, panceta y papas fritas.", "/assets/imagen-plato.png", 2900.0),
            new Plato("Ensalada César", "Ensalada con lechuga romana, pollo, crutones, parmesano y aderezo César.", "/assets/imagen-plato.png", 2300.0),
            new Plato("Tarta de espinaca", "Tarta casera de espinaca y queso con masa hojaldrada.", "/assets/imagen-plato.png", 2000.0)
    );

    List<Plato> platosTotales = List.of(
            new Plato("Milanesa con papas fritas", "Clásica milanesa de carne acompañada con papas fritas crujientes.", "/assets/imagen-plato.png", 2500.0),
            new Plato("Ravioles de ricota", "Ravioles caseros rellenos de ricota y nuez, servidos con salsa bolognesa.", "/assets/imagen-plato.png", 2800.0),
            new Plato("Pizza napolitana", "Pizza con tomate, mozzarella, ajo y albahaca fresca.", "/assets/imagen-plato.png", 3200.0),
            new Plato("Hamburguesa completa", "Hamburguesa con lechuga, tomate, queso, panceta y papas fritas.", "/assets/imagen-plato.png", 2900.0),
            new Plato("Ensalada César", "Ensalada con lechuga romana, pollo, crutones, parmesano y aderezo César.", "/assets/imagen-plato.png", 2300.0),
            new Plato("Tarta de espinaca", "Tarta casera de espinaca y queso con masa hojaldrada.", "/assets/imagen-plato.png", 2000.0),
            new Plato("Lasaña de carne", "Capas de pasta con carne, salsa bechamel y queso gratinado.", "/assets/imagen-plato.png", 3100.0),
            new Plato("Empanadas salteñas", "Empanadas rellenas de carne cortada a cuchillo, típicas del norte argentino.", "/assets/imagen-plato.png", 1800.0),
            new Plato("Pollo al horno con papas", "Muslo de pollo al horno con papas doradas y especias.", "/assets/imagen-plato.png", 2600.0),
            new Plato("Ñoquis con salsa rosa", "Ñoquis de papa acompañados con salsa de tomate y crema.", "/assets/imagen-plato.png", 2700.0),
            new Plato("Sopa crema de calabaza", "Sopa suave y cremosa de calabaza natural.", "/assets/imagen-plato.png", 1900.0),
            new Plato("Tacos de carne", "Tortillas mexicanas rellenas de carne, cebolla y cilantro.", "/assets/imagen-plato.png", 3000.0),
            new Plato("Milanesa napolitana", "Milanesa con jamón, queso y salsa de tomate, servida con papas.", "/assets/imagen-plato.png", 3200.0),
            new Plato("Pizza cuatro quesos", "Pizza con una mezcla de mozzarella, azul, provolone y parmesano.", "/assets/imagen-plato.png", 3400.0),
            new Plato("Panqueques con dulce de leche", "Postre de panqueques caseros rellenos con dulce de leche argentino.", "/assets/imagen-plato.png", 1600.0),
            new Plato("Churrasco con ensalada", "Carne asada a la plancha con guarnición de ensalada fresca.", "/assets/imagen-plato.png", 3500.0),
            new Plato("Fideos al pesto", "Fideos largos acompañados con salsa pesto casera.", "/assets/imagen-plato.png", 2500.0),
            new Plato("Sándwich de lomito", "Lomito con huevo, jamón, queso, lechuga y tomate en pan tostado.", "/assets/imagen-plato.png", 2800.0),
            new Plato("Canelones de verdura", "Pasta rellena de verdura con salsa blanca y gratinada al horno.", "/assets/imagen-plato.png", 3000.0),
            new Plato("Helado artesanal", "Helado de elaboración artesanal, sabores surtidos.", "/assets/imagen-plato.png", 1500.0)
    );


    List<Restaurante> restaurantes = List.of(
            new Restaurante("La Parrilla del Sur", "Especialidad en carnes a la parrilla", "/assets/restaurante-logo.png"),
            new Restaurante("Sushi Zen", "Lo mejor de la cocina japonesa", "/assets/restaurante-logo.png"),
            new Restaurante("Pizza Napoli", "Pizzas artesanales al horno de leña", "/assets/restaurante-logo.png"),
            new Restaurante("Verde Vivo", "Comida saludable y vegana", "/assets/restaurante-logo.png")
    );

    @Override
    public List<Restaurante> traerRestaurantesDestacados() {
        return this.restaurantes;
    }

    @Override
    public List<Plato> traerPlatosDestacados() {
        return this.platosDestacados;
    }

    @Override
    public List<Plato> traerTodosLosPlatos() {
        return this.platosTotales;
    }
}
