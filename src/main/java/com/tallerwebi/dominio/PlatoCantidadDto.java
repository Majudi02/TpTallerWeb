package com.tallerwebi.dominio;

public class PlatoCantidadDto {
    private String nombre;
    private int cantidad;

    public PlatoCantidadDto(String nombreProducto, int cantidad) {
        this.nombre = nombreProducto;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
