package com.tallerwebi.dominio;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class Cliente extends UsuarioNutriya {

    private String nombre;
    private Integer edad;
    private Integer pesoActual;
    private Integer pesoDeseado;
    private Double altura;
    private String objetivo;

    @ElementCollection
    private List<String> etiquetas;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getPesoActual() {
        return pesoActual;
    }

    public void setPesoActual(Integer pesoActual) {
        this.pesoActual = pesoActual;
    }

    public Integer getPesoDeseado() {
        return pesoDeseado;
    }

    public void setPesoDeseado(Integer pesoDeseado) {
        this.pesoDeseado = pesoDeseado;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    @Override
    public String tipoUsuario() {
        return "CLIENTE";
    }
}
