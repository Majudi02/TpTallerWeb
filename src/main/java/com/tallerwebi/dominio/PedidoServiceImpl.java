package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoServiceImpl  implements PedidoService{


    List<Plato> platos = List.of(
            new Plato("Milanesa con papas fritas", "Clásica milanesa de carne acompañada con papas fritas crujientes.", "/assets/imagen-plato.png", 2500.0),
            new Plato("Ravioles de ricota", "Ravioles caseros rellenos de ricota y nuez, servidos con salsa bolognesa.", "/assets/imagen-plato.png", 2800.0),
            new Plato("Pizza napolitana", "Pizza con tomate, mozzarella, ajo, y albahaca fresca.", "/assets/imagen-plato.png", 3200.0),
            new Plato("Hamburguesa completa", "Hamburguesa con lechuga, tomate, queso, panceta y papas fritas.", "/assets/imagen-plato.png", 2900.0),
            new Plato("Ensalada César", "Ensalada con lechuga romana, pollo, crutones, parmesano y aderezo César.", "/assets/imagen-plato.png", 2300.0),
            new Plato("Tarta de espinaca", "Tarta casera de espinaca y queso con masa hojaldrada.", "/assets/imagen-plato.png", 2000.0)
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
        return this.platos;
    }
}
