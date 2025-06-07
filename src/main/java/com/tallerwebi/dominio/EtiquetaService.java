package com.tallerwebi.dominio;

import java.util.List;

public interface EtiquetaService {

    List<EtiquetaDto> traerTodasLasEtiquetas();
    EtiquetaDto buscarEtiquetaPorId(Integer id);
}
