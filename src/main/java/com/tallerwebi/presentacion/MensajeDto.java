package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Mensaje;

import java.time.format.DateTimeFormatter;

public class MensajeDto {
    private String contenido;
    private Long remitenteId;
    private String fecha;

    public MensajeDto(Mensaje mensaje) {
        this.contenido = mensaje.getContenido();
        this.remitenteId = mensaje.getRemitenteId();
        this.fecha = mensaje.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Long getRemitenteId() {
        return remitenteId;
    }

    public void setRemitenteId(Long remitenteId) {
        this.remitenteId = remitenteId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
