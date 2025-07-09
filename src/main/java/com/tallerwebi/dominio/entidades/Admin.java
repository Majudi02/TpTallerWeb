package com.tallerwebi.dominio.entidades;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
public class Admin extends UsuarioNutriya {

    @Override
    public String tipoUsuario() {
        return "admin";
    }
}
