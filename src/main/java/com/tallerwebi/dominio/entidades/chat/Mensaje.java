package com.tallerwebi.dominio.entidades.chat;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenido;
    private Long remitenteId;
    private Long destinatarioId;
    private Long pedidoId;
    private LocalDateTime fecha;


    public Long getId() { return id; }

    public String getContenido() { return contenido; }

    public void setContenido(String contenido) { this.contenido = contenido; }

    public Long getRemitenteId() { return remitenteId; }

    public void setRemitenteId(Long remitenteId) { this.remitenteId = remitenteId; }


    public Long getDestinatarioId() { return destinatarioId; }

    public void setDestinatarioId(Long destinatarioId) { this.destinatarioId = destinatarioId; }

    public Long getPedidoId() { return pedidoId; }

    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

    public LocalDateTime getFecha() { return fecha; }

    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}