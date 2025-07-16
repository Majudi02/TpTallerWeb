package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Mensaje;
import com.tallerwebi.presentacion.MensajeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioMensajeImpl implements ServicioMensaje {

    private RepositorioMensaje repositorioMensaje;

    @Autowired
    public ServicioMensajeImpl(RepositorioMensaje repositorioMensaje) {
        this.repositorioMensaje = repositorioMensaje;
    }

    @Override
    public void guardarMensaje(Mensaje mensaje) {
        repositorioMensaje.guardar(mensaje);
    }

    @Override
    public List<MensajeDto> traerMensajesPorPedido(Long pedidoId) {
        return repositorioMensaje.traerMensajesPorPedido(pedidoId).stream()
                .map(MensajeDto::new)
                .collect(Collectors.toList());
    }
}
