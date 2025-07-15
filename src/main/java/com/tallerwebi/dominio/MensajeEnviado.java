package com.tallerwebi.dominio;


public class MensajeEnviado {
    private String contenido;
    private Long remitenteId;
    private Long pedidoId;


    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Long getRemitenteId() {
        return remitenteId;
    }

    public void setRemitenteId(Long remitenteId) {
        this.remitenteId = remitenteId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }
}
