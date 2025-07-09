package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Plato;

public class ResumenRestauranteDTO {
    private double gananciasTotales;
    private Plato masPedido;
    private Plato menosPedido;
    private Plato mejorValorado;
    private Plato peorValorado;

    public ResumenRestauranteDTO(double gananciasTotales, Plato masPedido, Plato menosPedido, Plato mejorValorado, Plato peorValorado) {
        this.gananciasTotales = gananciasTotales;
        this.masPedido = masPedido;
        this.menosPedido = menosPedido;
        this.mejorValorado = mejorValorado;
        this.peorValorado = peorValorado;
    }

    // Getters


    public double getGananciasTotales() {
        return gananciasTotales;
    }

    public void setGananciasTotales(double gananciasTotales) {
        this.gananciasTotales = gananciasTotales;
    }

    public Plato getMasPedido() {
        return masPedido;
    }

    public void setMasPedido(Plato masPedido) {
        this.masPedido = masPedido;
    }

    public Plato getMenosPedido() {
        return menosPedido;
    }

    public void setMenosPedido(Plato menosPedido) {
        this.menosPedido = menosPedido;
    }

    public Plato getMejorValorado() {
        return mejorValorado;
    }

    public void setMejorValorado(Plato mejorValorado) {
        this.mejorValorado = mejorValorado;
    }

    public Plato getPeorValorado() {
        return peorValorado;
    }

    public void setPeorValorado(Plato peorValorado) {
        this.peorValorado = peorValorado;
    }
}

