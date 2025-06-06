package com.tallerwebi.dominio;
import com.tallerwebi.dominio.Entity.Plato;

import java.util.List;

public class PlatoDto {

    private String nombre;
    private String descripcion;
    private String imagen;
    private Double precio;
    private List<String> etiquetas;


    public PlatoDto(String nombre, String descripcion, String imagen, Double precio, List<String> etiquetas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.precio = precio;
        this.etiquetas = etiquetas;
    }


    public PlatoDto(Plato plato){
        this.nombre = plato.getNombre();
        this.descripcion = plato.getDescripcion();
        this.imagen = plato.getImagen();
        this.precio = plato.getPrecio();
        this.etiquetas = plato.getEtiquetas();
    }

    public PlatoDto() {}


    public Plato obtenerEntidad() {
        Plato plato = new Plato();
        plato.setNombre(this.nombre);
        plato.setDescripcion(this.descripcion);
        plato.setImagen(this.imagen);
        plato.setPrecio(this.precio);
        plato.setEtiquetas(this.etiquetas);
        return plato;
    }





    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
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
}