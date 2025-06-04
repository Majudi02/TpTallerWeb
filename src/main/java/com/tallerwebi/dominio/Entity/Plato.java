package com.tallerwebi.dominio.Entity;

import com.tallerwebi.dominio.PlatoDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Plato")
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String descripcion;
    private String imagen;
    private Double precio;


    @ElementCollection
    @CollectionTable(name = "plato_etiquetas", joinColumns = @JoinColumn(name = "plato_id"))
    @Column(name = "etiqueta")
    private List<String> etiquetas;

    public PlatoDto obtenerDto() {
        PlatoDto platoDto = new PlatoDto();
        platoDto.setNombre(this.nombre);
        platoDto.setDescripcion(this.descripcion);
        platoDto.setImagen(this.imagen);
        platoDto.setPrecio(this.precio);
        platoDto.setEtiquetas(this.etiquetas);
        return platoDto;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }
}