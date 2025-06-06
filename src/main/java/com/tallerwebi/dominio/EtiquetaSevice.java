package com.tallerwebi.dominio;

import java.util.List;

public interface EtiquetaSevice {

    List<EtiquetaDto> traerTodasLasEtiquetas();
    EtiquetaDto buscarEtiquetaPorId(Integer id);
}
