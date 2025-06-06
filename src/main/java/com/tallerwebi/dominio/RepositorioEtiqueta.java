package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Entity.Etiqueta;

import java.util.List;

public interface RepositorioEtiqueta {
    List<Etiqueta> traerTodasLasEtiquetas();
    Etiqueta buscarEtiquetaPorId(Integer id);
}
