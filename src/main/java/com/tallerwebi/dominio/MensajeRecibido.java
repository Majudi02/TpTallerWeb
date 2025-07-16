package com.tallerwebi.dominio;


public class MensajeRecibido {
    private String mensaje;
    private Long remitenteId;
    private Long destinatarioId;
    private Long pedidoId;


    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mesanje) {
        this.mensaje = mesanje;
    }

    public Long getRemitenteId() {
        return remitenteId;
    }

    public void setRemitenteId(Long remitenteId) {
        this.remitenteId = remitenteId;
    }

    public Long getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(Long destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }
}