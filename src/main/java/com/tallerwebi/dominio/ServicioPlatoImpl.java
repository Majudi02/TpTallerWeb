package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Plato;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServicioPlatoImpl implements ServicioPlato {

    private final RepositorioPlato repositorioPlato;

    public ServicioPlatoImpl(RepositorioPlato repositorioPlato) {
        this.repositorioPlato = repositorioPlato;
    }

    @Override
    public List<PlatoDto> traerTodosLosPlatos() {
        List<Plato> platos = repositorioPlato.traerTodosLosPlatos();
        return platos.stream()
                .map(Plato::obtenerDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlatoDto> buscarPlatosPorTipoComida(String tipoComida) {
        List<Plato> platos = repositorioPlato.buscarPlatosPorTipoComida(tipoComida);
        return platos.stream()
                .map(Plato::obtenerDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlatoDto buscarPlatoPorId(Integer id) {
        Plato plato = repositorioPlato.buscarPlatoPorId(id);
        return plato != null ? plato.obtenerDto() : null;
    }

    @Override
    public List<PlatoDto> buscarPlatosPorEtiquetasDelCliente(Long idCliente) {
        List<Plato> platos = repositorioPlato.buscarPlatosPorEtiquetasDelCliente(idCliente);
        return platos.stream()
                .map(Plato::obtenerDto)
                .collect(Collectors.toList());
    }
}