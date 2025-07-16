package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Plato;
import com.tallerwebi.dominio.entidades.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ServicioBusquedaImpl implements ServicioBusqueda {
    private final RepositorioUsuarioRestaurante repositorioUsuarioRestaurante;
    private final RepositorioPlato repositorioPlato;

    @Autowired
    public ServicioBusquedaImpl(RepositorioUsuarioRestaurante repositorioUsuarioRestaurante, RepositorioPlato repositorioPlato) {
        this.repositorioUsuarioRestaurante = repositorioUsuarioRestaurante;
        this.repositorioPlato = repositorioPlato;
    }

    @Override
    public BusquedaResultadoDto buscarRestaurantesYPlatos(String texto) {
        String textoBuscado = normalizarTexto(texto);

        List<RestauranteDto> restaurantesEncontrados = new ArrayList<>();
        List<Restaurante> restaurantesObtenidos = repositorioUsuarioRestaurante.buscarTodosLosRestaurantes();

        for (Restaurante r : restaurantesObtenidos) {
            String nombreSinAcentos = normalizarTexto(r.getNombre());
            String descripcionSinAcentos = normalizarTexto(r.getDescripcion());

            if (nombreSinAcentos.contains(textoBuscado) || descripcionSinAcentos.contains(textoBuscado)) {
                restaurantesEncontrados.add(r.obtenerDto());
            }
        }

        List<PlatoDto> platosEncontrados = new ArrayList<>();
        List<Plato> platosObtenidos = repositorioPlato.traerTodosLosPlatos();

        for (Plato p : platosObtenidos) {
            String nombreSinAcentos = normalizarTexto(p.getNombre());
            String descripcionSinAcentos = normalizarTexto(p.getDescripcion());

            if (nombreSinAcentos.contains(textoBuscado) || descripcionSinAcentos.contains(textoBuscado)) {
                platosEncontrados.add(p.obtenerDto());
            }
        }

        return new BusquedaResultadoDto(restaurantesEncontrados, platosEncontrados);
    }

    private String normalizarTexto(String texto) {
        if (texto == null) return "";
        return Normalizer.normalize(texto.toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    @Override
    public List<SugerenciaDto> obtenerSugerencias(String texto) {
        String textoBuscado = normalizarTexto(texto);

        List<SugerenciaDto> sugerencias = new ArrayList<>();
        Set<String> vistos = new HashSet<>();

        List<Restaurante> restaurantes = repositorioUsuarioRestaurante.buscarTodosLosRestaurantes();
        for (Restaurante r : restaurantes) {
            String nombreSinAcentos = normalizarTexto(r.getNombre());
            if (nombreSinAcentos.startsWith(textoBuscado) && !vistos.contains(r.getNombre())) {
                sugerencias.add(new SugerenciaDto(r.getNombre(), "restaurante", r.getId()));
                vistos.add(r.getNombre());
            }
        }

        List<Plato> platos = repositorioPlato.traerTodosLosPlatos();
        for (Plato p : platos) {
            String nombreSinAcentos = normalizarTexto(p.getNombre());
            if (nombreSinAcentos.startsWith(textoBuscado) && !vistos.contains(p.getNombre())) {
                sugerencias.add(new SugerenciaDto(p.getNombre(), "plato", Long.valueOf(p.getId())));
                vistos.add(p.getNombre());
            }
        }

        return sugerencias;
    }

}
