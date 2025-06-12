package com.tallerwebi.dominio.entidades;

import com.tallerwebi.presentacion.UsuarioDTO;

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


    public Cliente obtenerEntidad(UsuarioDTO dto) {
        Cliente cliente = new Cliente();

        cliente.setNombre(dto.getNombre());
        cliente.setEmail(dto.getEmail());
        cliente.setPassword(dto.getPassword());
        cliente.setEdad(dto.getEdad());
        cliente.setPesoActual(dto.getPesoActual());
        cliente.setPesoDeseado(dto.getPesoDeseado());
        cliente.setAltura(dto.getAltura());
        cliente.setObjetivo(dto.getObjetivo());
        cliente.setEtiquetas(dto.getEtiquetas());
        cliente.setConfirmado(dto.getConfirmado());
        cliente.setTokenConfirmacion(dto.getTokenConfirmacion());

        return cliente;
    }

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
