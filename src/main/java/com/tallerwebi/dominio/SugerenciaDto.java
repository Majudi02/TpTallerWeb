package com.tallerwebi.dominio;

import java.util.List;

public class SugerenciaDto {
    private String texto;
    private String tipo;
    private Long id;

    public SugerenciaDto(String texto, String tipo, Long id) {
        this.texto = texto;
        this.tipo = tipo;
        this.id = id;
    }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}

