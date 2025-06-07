package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Etiqueta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class EtiquetaServiceImpl implements EtiquetaService {

    private final RepositorioEtiqueta repositorioEtiqueta;

    @Autowired
    public EtiquetaServiceImpl(RepositorioEtiqueta repositorioEtiqueta) {
        this.repositorioEtiqueta = repositorioEtiqueta;
    }

    @Override
    public List<EtiquetaDto> traerTodasLasEtiquetas() {
        List<Etiqueta> etiquetas = this.repositorioEtiqueta.traerTodasLasEtiquetas();
        return etiquetas.stream()
                .map(Etiqueta::obtenerDto)
                .collect(Collectors.toList());
    }

    @Override
    public EtiquetaDto buscarEtiquetaPorId(Integer id) {
        Etiqueta etiqueta = repositorioEtiqueta.buscarEtiquetaPorId(id);
        if (etiqueta == null) {
            throw new RuntimeException("Etiqueta con id " + id + " no encontrada");

        }

        return etiqueta.obtenerDto();
    }
}



