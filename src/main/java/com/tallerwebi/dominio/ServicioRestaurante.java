package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Entity.Plato;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ServicioRestaurante {
    boolean agregarRestaurante(Restaurante restaurante);

    Restaurante obtenerRestaurante(String nombre);

    List<Restaurante> obtenerRestaurantes();

    List<Restaurante> obtenerRestaurantesPorZona(String zona);

    List<Restaurante> buscarPorTipoComida(String tipoComida);

    List<Restaurante> buscarPorTipoComidaYZona(String zona, String tipoComida);

    Boolean guardarPlato(PlatoDto platoDto);

    Boolean actualizarPlato(PlatoDto platoDto);

    PlatoDto obtenerPlatoPorId(Integer id);

    // DESPUES SACAR
    void limpiarRestaurantes();

    List<PlatoDto> traerTodosLosPlatos();

    void guardarImagen(PlatoDto platoDto, MultipartFile imagen);


}
