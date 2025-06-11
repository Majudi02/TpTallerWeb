package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Restaurante;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ServicioRestaurante {

    Restaurante obtenerRestaurante(String nombre);

    Restaurante obtenerRestaurantePorId(Long idRestaurante);

    List<Restaurante> obtenerRestaurantes();

    List<Restaurante> obtenerRestaurantesPorZona(String zona);

    List<Restaurante> buscarPorTipoComida(String tipoComida);

    List<Restaurante> buscarPorTipoComidaYZona(String zona, String tipoComida);

    Boolean guardarPlato(PlatoDto platoDto);

    Boolean actualizarPlato(PlatoDto platoDto);

    void inicializarDatos();

    PlatoDto obtenerPlatoPorId(Integer id);

    List<PlatoDto> traerTodosLosPlatos();

    void guardarImagen(PlatoDto platoDto, MultipartFile imagen);

    List<PlatoDto> obtenerPlatosDelRestaurante(Long idRestaurante);

    Restaurante obtenerRestaurantePorUsuarioId(Long usuarioId);
}
