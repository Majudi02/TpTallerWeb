package com.tallerwebi.dominio;

import java.util.List;

public class PedidoVistaDto {
    private String nombreRestaurante;
    private String direccionRestaurante;
    private String direccionCliente;
    private List<PlatoCantidadDto> platos;

    private Integer pedidoId;

    public String getNombreRestaurante() {
        return nombreRestaurante;
    }

    public void setNombreRestaurante(String nombreRestaurante) {
        this.nombreRestaurante = nombreRestaurante;
    }

    public String getDireccionRestaurante() {
        return direccionRestaurante;
    }

    public void setDireccionRestaurante(String direccionRestaurante) {
        this.direccionRestaurante = direccionRestaurante;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public List<PlatoCantidadDto> getPlatos() {
        return platos;
    }

    public void setPlatos(List<PlatoCantidadDto> platos) {
        this.platos = platos;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }
}
