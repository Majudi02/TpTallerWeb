package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioRestauranteImpl implements ServicioRestaurante {

    private RepostitorioPlato repostitorioPlato;
    List<Restaurante> restaurantes;

    @Autowired
    public ServicioRestauranteImpl(RepostitorioPlato repostitorioPlato) {
        this.repostitorioPlato = repostitorioPlato;
    }

    public ServicioRestauranteImpl() {
        this.restaurantes = new ArrayList<>();
        // Copiamos los datos de la lista estática a la instancia
        this.restaurantes.addAll(restaurantesVista);
    }



    public ServicioRestauranteImpl(Boolean iniciarVacio) {
        this.restaurantes = new ArrayList<>();
    }



    private static final List<Restaurante> restaurantesVista = List.of(
            new Restaurante("Green Bowl", "Comida Vegana", "/assets/restaurante.png", "calle", 123, "Don Torcuato", "Norte", List.of("Vegana")),
            new Restaurante("Natural Express", "Comida Vegana", "/assets/restaurante.png", "calle", 321, "La Matanza", "Oeste", List.of("Proteica")),
            new Restaurante("Vital Food", "Comida Proteica", "/assets/restaurante.png", "calle", 213, "La Matanza", "Oeste", List.of("Vegana", "Proteica")),
            new Restaurante("La Parrilla del Sur", "Especialidad en carnes a la parrilla", "/assets/restaurante-logo.png", "Av. Corrientes", 1234, "Buenos Aires", "Microcentro", List.of("Proteica")),
            new Restaurante("Sushi Zen", "Lo mejor de la cocina japonesa", "/assets/restaurante-logo.png", "Calle Defensa", 567, "Buenos Aires", "San Telmo", List.of("Proteica", "Sin gluten")),
            new Restaurante("Pizza Napoli", "Pizzas artesanales al horno de leña", "/assets/restaurante-logo.png", "Av. Santa Fe", 890, "Buenos Aires", "Recoleta", List.of("Opciones vegetarianas")),
            new Restaurante("Verde Vivo", "Comida saludable y vegana", "/assets/restaurante-logo.png", "Calle Mendoza", 234, "Mendoza", "Centro", List.of("Vegana", "Vegetariana", "Sin gluten"))
    );


    @Override
    public boolean agregarRestaurante(Restaurante restaurante) {

        for (Restaurante rest : restaurantes) {
            if (rest.getCalle().equalsIgnoreCase(restaurante.getCalle()) && rest.getNumero().equals(restaurante.getNumero())) {
                return false;
            }
        }
        return restaurantes.add(restaurante);
    }

    @Override
    public Restaurante obtenerRestaurante(String nombre) {
        for (Restaurante rest : restaurantes) {
            if (rest.getNombre().equalsIgnoreCase(nombre)) {
                return rest;
            }
        }
        return null;
    }

    @Override
    public List<Restaurante> obtenerRestaurantes() {
        return restaurantes;
    }

    @Override
    public List<Restaurante> obtenerRestaurantesPorZona(String zona) {
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

    @Override
    @Transactional
    public Boolean guardarPlato(PlatoDto platoDto) {
        return this.repostitorioPlato.crearPlato(platoDto.obtenerEntidad());
    }




}
