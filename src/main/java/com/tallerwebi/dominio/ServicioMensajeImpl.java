package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.chat.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Mensaje> traerMensajePorPedido(Long pedidoId) {
       return repositorioMensaje.traerMensajePorPedido(pedidoId);
    }
}
