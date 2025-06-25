package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.dominio.entidades.EstadoPlato;

public class PedidoPlatoDto {

    private Long id;
    private PlatoDto plato;
    private EstadoPlato estadoPlato;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlatoDto getPlato() {
        return plato;
    }

    public void setPlato(PlatoDto plato) {
        this.plato = plato;
    }

    public EstadoPlato getEstadoPlato() {
        return estadoPlato;
    }

    public void setEstadoPlato(EstadoPlato estadoPlato) {
        this.estadoPlato = estadoPlato;
    }
}