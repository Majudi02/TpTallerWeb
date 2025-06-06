package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Entity.Etiqueta;

public class EtiquetaDto {

    private Integer id;
    private String nombre;




    // Constructor con parámetros
    public EtiquetaDto(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Etiqueta obtenerEntidad() {
        Etiqueta etiqueta = new Etiqueta();
        etiqueta.setId(this.id);
        etiqueta.setNombre(this.nombre);

        return etiqueta;
    }

    // Getters y setters
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
}
