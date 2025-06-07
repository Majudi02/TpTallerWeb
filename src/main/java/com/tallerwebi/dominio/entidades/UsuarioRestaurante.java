package com.tallerwebi.dominio.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class UsuarioRestaurante extends UsuarioNutriya {
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Restaurante restaurante;

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    @Override
    public String tipoUsuario() {
        return "RESTAURANTE";
    }
}

