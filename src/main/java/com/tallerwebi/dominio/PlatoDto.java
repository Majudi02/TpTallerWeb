package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Etiqueta;
import com.tallerwebi.dominio.entidades.Plato;
import com.tallerwebi.dominio.entidades.Restaurante;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlatoDto {

    private Integer id;
    private Long idRestaurante;
    private String nombre;
    private String descripcion;
    private String imagen;
    private Double precio;
    private List<Integer> etiquetasIds;
    private List<EtiquetaDto> etiquetas;

    public PlatoDto(Integer id, Long idRestaurante, String nombre, String descripcion, String imagen, Double precio, List<EtiquetaDto> etiquetas) {
        this.id = id;
        this.idRestaurante = idRestaurante;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.precio = precio;
        this.etiquetas = etiquetas;
    }

    public PlatoDto(Plato plato) {
        this.id = plato.getId();
        this.idRestaurante = plato.getRestaurante().getId();
        this.nombre = plato.getNombre();
        this.descripcion = plato.getDescripcion();
        this.imagen = plato.getImagen();
        this.precio = plato.getPrecio();
        this.etiquetas = plato.getEtiquetas()
                .stream()
                .map(etiqueta -> new EtiquetaDto(etiqueta.getId(), etiqueta.getNombre()))
                .collect(Collectors.toList());
    }

    public PlatoDto() {
    }

    public Plato obtenerEntidad(List<Etiqueta> listaEtiquetas) {
        Plato plato = new Plato();
        plato.setId(this.id);
        plato.setNombre(this.nombre);
        plato.setDescripcion(this.descripcion);
        plato.setImagen(this.imagen);
        plato.setPrecio(this.precio);
        plato.setEtiquetas(listaEtiquetas);

        Restaurante restaurante = new Restaurante();
        restaurante.setId(this.idRestaurante);
        plato.setRestaurante(restaurante);

        return plato;
    }


    public Plato obtenerDto(List<EtiquetaDto> etiquetasDto) {
        Plato plato = new Plato();
        plato.setId(this.id);
        plato.setNombre(this.nombre);
        plato.setDescripcion(this.descripcion);
        plato.setImagen(this.imagen);
        plato.setPrecio(this.precio);

        List<Etiqueta> listaEtiquetas = new ArrayList<>();

        for (EtiquetaDto dto : etiquetasDto) {
            Etiqueta etiqueta = new Etiqueta();
            etiqueta.setId(dto.getId());
            etiqueta.setNombre(dto.getNombre());
            listaEtiquetas.add(etiqueta);
        }

        plato.setEtiquetas(listaEtiquetas);
        return plato;
    }


    public List<Integer> getEtiquetasIds() {
        return etiquetasIds;
    }

    public void setEtiquetasIds(List<Integer> etiquetasIds) {
        this.etiquetasIds = etiquetasIds;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<EtiquetaDto> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<EtiquetaDto> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdRestaurante(Long idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public Long getIdRestaurante() {
        return idRestaurante;
    }
}