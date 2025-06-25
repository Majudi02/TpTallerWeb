package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Direccion;

import java.util.List;

public interface RepositorioDireccion {
    Direccion guardarDireccion(Direccion direccion);

    List<Direccion> buscarDireccionesCliente(Long clienteId);

    void eliminarDireccionPorId(Long id);

    Direccion buscarPorId(Long id);
}
