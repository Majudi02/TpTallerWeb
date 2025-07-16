package com.tallerwebi.dominio;

import java.util.List;

public class RestauranteDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private String rutaImagen;
    private String calle;
    private Integer numero;
    private String localidad;
    private String zona;
    private List<String> tiposComida;

    public RestauranteDto() {
    }

    public RestauranteDto(Long id, String nombre, String descripcion, String rutaImagen,
                          String calle, Integer numero, String localidad, String zona,
                          List<String> tiposComida) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.zona = zona;
        this.tiposComida = tiposComida;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public List<String> getTiposComida() {
        return tiposComida;
    }

    public void setTiposComida(List<String> tiposComida) {
        this.tiposComida = tiposComida;
    }
}
