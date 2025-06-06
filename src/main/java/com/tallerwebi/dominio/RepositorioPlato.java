package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Entity.Plato;

import java.util.List;

public interface RepositorioPlato {
    Boolean crearPlato(Plato plato);
    Plato buscarPlatoPorId(Integer id);
    boolean eliminarPlato(Integer id);
    List<Plato> buscarPlatosPorTipoComida(String tipoComida);
    List<Plato> traerTodosLosPlatos();
}
