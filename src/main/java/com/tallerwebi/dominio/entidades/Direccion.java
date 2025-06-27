package com.tallerwebi.dominio.entidades;

import com.tallerwebi.presentacion.DireccionDto;

import javax.persistence.*;

@Entity
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String calle;
    private Integer numero;
    private String localidad;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public DireccionDto obtenerDto() {
        DireccionDto dto = new DireccionDto();
        dto.setId(this.id);
        dto.setCalle(this.calle);
        dto.setNumero(this.numero);
        dto.setLocalidad(this.localidad);
        return dto;
    }

}