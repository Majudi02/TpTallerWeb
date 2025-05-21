package com.tallerwebi.dominio;

import java.util.List;

public class Restaurante {
    private String nombre;
    private String descripcion;
    private String imagen;
    private String calle;
    private Integer numero;
    private String localidad;
    private String zona;
    private List<TipoComida> tiposComida;

    public Restaurante(String nombre, String descripcion, String imagen, String calle, Integer numero, String localidad, String zona, List<TipoComida> tiposComida) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.zona = zona;
        this.tiposComida = tiposComida;
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

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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

    public List<TipoComida> getTiposComida() {
        return tiposComida;
    }

    public void setTiposComida(List<TipoComida> tiposComida) {
        this.tiposComida = tiposComida;
    }
}
