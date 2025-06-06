package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioRestauranteImpl implements ServicioRestaurante {
    /*
    private static final List<Restaurante> restaurantesVista = List.of(
            new Restaurante("Green Bowl", "Comida Vegana", "/assets/restaurante.png", "calle", 123, "Don Torcuato", "Norte", List.of("Vegana")),
            new Restaurante("Natural Express", "Comida Vegana", "/assets/restaurante.png", "calle", 321, "La Matanza", "Oeste", List.of("Proteica")),
            new Restaurante("Vital Food", "Comida Proteica", "/assets/restaurante.png", "calle", 213, "La Matanza", "Oeste", List.of("Vegana", "Proteica")),
            new Restaurante("La Parrilla del Sur", "Especialidad en carnes a la parrilla", "/assets/restaurante-logo.png", "Av. Corrientes", 1234, "Buenos Aires", "Microcentro", List.of("Proteica")),
            new Restaurante("Sushi Zen", "Lo mejor de la cocina japonesa", "/assets/restaurante-logo.png", "Calle Defensa", 567, "Buenos Aires", "San Telmo", List.of("Proteica", "Sin gluten")),
            new Restaurante("Pizza Napoli", "Pizzas artesanales al horno de leña", "/assets/restaurante-logo.png", "Av. Santa Fe", 890, "Buenos Aires", "Recoleta", List.of("Opciones vegetarianas")),
            new Restaurante("Verde Vivo", "Comida saludable y vegana", "/assets/restaurante-logo.png", "Calle Mendoza", 234, "Mendoza", "Centro", List.of("Vegana", "Vegetariana", "Sin gluten"))
    );
    */
    private final RepositorioUsuarioRestaurante repositorioUsuarioRestaurante;

    @Autowired
    public ServicioRestauranteImpl(RepositorioUsuarioRestaurante repositorioUsuarioRestaurante) {
        this.repositorioUsuarioRestaurante = repositorioUsuarioRestaurante;
    }

    @Override
    public void inicializarDatos() {
        if (repositorioUsuarioRestaurante.buscarTodosLosRestaurantes().isEmpty()) {

            List<Restaurante> restaurantesVista = List.of(
                    new Restaurante("Green Bowl", "Comida Vegana", "/assets/restaurante.png", "calle", 123, "Don Torcuato", "Norte", List.of("Vegana")),
                    new Restaurante("Natural Express", "Comida Vegana", "/assets/restaurante.png", "calle", 321, "La Matanza", "Oeste", List.of("Proteica")),
                    new Restaurante("Vital Food", "Comida Proteica", "/assets/restaurante.png", "calle", 213, "La Matanza", "Oeste", List.of("Vegana", "Proteica")),
                    new Restaurante("La Parrilla del Sur", "Especialidad en carnes a la parrilla", "/assets/restaurante-logo.png", "Av. Corrientes", 1234, "Buenos Aires", "Microcentro", List.of("Proteica")),
                    new Restaurante("Sushi Zen", "Lo mejor de la cocina japonesa", "/assets/restaurante-logo.png", "Calle Defensa", 567, "Buenos Aires", "San Telmo", List.of("Proteica", "Sin gluten")),
                    new Restaurante("Pizza Napoli", "Pizzas artesanales al horno de leña", "/assets/restaurante-logo.png", "Av. Santa Fe", 890, "Buenos Aires", "Recoleta", List.of("Opciones vegetarianas")),
                    new Restaurante("Verde Vivo", "Comida saludable y vegana", "/assets/restaurante-logo.png", "Calle Mendoza", 234, "Mendoza", "Centro", List.of("Vegana", "Vegetariana", "Sin gluten"))
            );

            for (int i = 0; i < restaurantesVista.size(); i++) {
                UsuarioRestaurante usuario = new UsuarioRestaurante();
                usuario.setEmail("email" + (i + 1) + "@email.com");
                usuario.setRestaurante(restaurantesVista.get(i));
                repositorioUsuarioRestaurante.guardar(usuario);
            }
        }
    }


    @Override
    public Restaurante obtenerRestaurante(String nombre) {
        List<Restaurante> restaurantes = repositorioUsuarioRestaurante.buscarTodosLosRestaurantes();
        for (Restaurante rest : restaurantes) {
            if (rest.getNombre().equalsIgnoreCase(nombre)) {
                return rest;
            }
        }
        return null;
    }

    @Override
    public List<Restaurante> obtenerRestaurantes() {
        return repositorioUsuarioRestaurante.buscarTodosLosRestaurantes();
    }

    @Override
    public List<Restaurante> obtenerRestaurantesPorZona(String zona) {
        List<Restaurante> restaurantes = repositorioUsuarioRestaurante.buscarTodosLosRestaurantes();
        List<Restaurante> restaurantesObtenidos = new ArrayList<>();
        for (Restaurante rest : restaurantes) {
            if (rest.getZona().equalsIgnoreCase(zona)) {
                restaurantesObtenidos.add(rest);
            }
        }
        return restaurantesObtenidos;
    }

    @Override
    public List<Restaurante> buscarPorTipoComida(String tipoComida) {
        List<Restaurante> restaurantes = repositorioUsuarioRestaurante.buscarTodosLosRestaurantes();
        List<Restaurante> restaurantesObtenidos = new ArrayList<>();
        for (Restaurante r : restaurantes) {
            if (r.getTiposComida().contains(tipoComida)) {
                restaurantesObtenidos.add(r);
            }
        }
        return restaurantesObtenidos;
    }

    @Override
    public List<Restaurante> buscarPorTipoComidaYZona(String zona, String tipoComida) {
        List<Restaurante> restaurantes = repositorioUsuarioRestaurante.buscarTodosLosRestaurantes();
        List<Restaurante> resultados = new ArrayList<>();
        for (Restaurante rest : restaurantes) {
            boolean coincideZona = (zona == null || zona.isEmpty()) || rest.getZona().equalsIgnoreCase(zona);

            boolean coincideTipo = false;
            if (tipoComida == null || tipoComida.isEmpty()) {
                coincideTipo = true;
            } else {
                for (String tipo : rest.getTiposComida()) {
                    if (tipo.equalsIgnoreCase(tipoComida)) {
                        coincideTipo = true;
                        break;
                    }
                }
            }

            if (coincideZona && coincideTipo) {
                resultados.add(rest);
            }
        }
        return resultados;
    }
}


