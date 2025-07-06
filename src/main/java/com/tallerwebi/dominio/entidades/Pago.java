package com.tallerwebi.dominio.entidades;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idPagoMercadoPago;
    private String estado;
    private String metodoPago;
    private String tipoPago;
    private Double monto;
    private String moneda;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaAprobacion;

    private String correoPagador;

    @OneToOne
    private Pedido pedido;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getCorreoPagador() {
        return correoPagador;
    }

    public void setCorreoPagador(String correoPagador) {
        this.correoPagador = correoPagador;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public LocalDateTime getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDateTime fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdPagoMercadoPago() {
        return idPagoMercadoPago;
    }

    public void setIdPagoMercadoPago(Long idPagoMercadoPago) {
        this.idPagoMercadoPago = idPagoMercadoPago;
    }
}