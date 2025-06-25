package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Direccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioDireccionImpl implements ServicioDireccion {

    private final RepositorioDireccion repositorioDireccion;

    @Autowired
    public ServicioDireccionImpl(RepositorioDireccion repositorioDireccion) {
        this.repositorioDireccion = repositorioDireccion;
    }

    @Override
    public Direccion guardarDireccion(Direccion direccion) {
        return repositorioDireccion.guardarDireccion(direccion);
    }

    @Override
    public List<Direccion> obtenerDireccionesPorCliente(Long clienteId) {
        return repositorioDireccion.buscarDireccionesCliente(clienteId);
    }

    @Override
    public void eliminarDireccionPorId(Long id) {
        repositorioDireccion.eliminarDireccionPorId(id);
    }

    @Override
    public Direccion actualizarDireccion(Direccion direccion) {
        // Validar que exista
        Direccion existente = buscarPorId(direccion.getId());
        existente.setCalle(direccion.getCalle());
        existente.setNumero(direccion.getNumero());
        existente.setLocalidad(direccion.getLocalidad());
        // Si tiene cliente asociado, podés actualizarlo también o dejar fijo
        return repositorioDireccion.guardarDireccion(existente);
    }

    private Direccion buscarPorId(Long id) {
        return repositorioDireccion.buscarPorId(id);
    }
}

