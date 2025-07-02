package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Pedido;
import com.tallerwebi.dominio.entidades.PedidoPlato;
import com.tallerwebi.presentacion.PedidoPlatoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class ServicioPedidoPlatoImpl implements ServicioPedidoPlato{

    private final RepositorioPedidoPlato repositorioPedidoPlato;


    @Autowired
    public ServicioPedidoPlatoImpl(RepositorioPedidoPlato repositorioPedidoPlato){
        this.repositorioPedidoPlato = repositorioPedidoPlato;
    }

    @Override
    public PedidoPlatoDto buscarPorId(Long id) {
        PedidoPlato pedidoPlato = repositorioPedidoPlato.buscarPorId(id);
        return pedidoPlato.obtenerDto();
    }

    @Override
    public void guardar(PedidoPlatoDto pedidoPlato) {
        PedidoPlato entidad = repositorioPedidoPlato.buscarPorId(pedidoPlato.getId());
        if (entidad != null) {
            entidad.setEstadoPlato(pedidoPlato.getEstadoPlato());
            entidad.setCalificacion(pedidoPlato.getCalificacion());
            repositorioPedidoPlato.guardar(entidad);
        }
    }

    @Override
    public void finalizarPedido(Long id) {
        repositorioPedidoPlato.finalizarPedido(id);
    }
}
