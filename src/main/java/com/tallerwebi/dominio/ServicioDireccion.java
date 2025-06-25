package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Direccion;

import java.util.List;

public interface ServicioDireccion {
    Direccion guardarDireccion(Direccion direccion);

    List<Direccion> obtenerDireccionesPorCliente(Long clienteId);

    void eliminarDireccionPorId(Long id);

    Direccion actualizarDireccion(Direccion direccion);
}
