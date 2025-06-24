package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.EstadoPedido;
import com.tallerwebi.dominio.entidades.EstadoPlato;
import com.tallerwebi.dominio.entidades.Pedido;

import com.tallerwebi.dominio.entidades.PedidoPlato;
import com.tallerwebi.presentacion.PedidoDto;
import com.tallerwebi.presentacion.PedidoPlatoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServicioPedidoRestauranteImpl implements  ServicioPedidoRestaurante{

    private final RepositorioPedidoRestaurante repositorioPedidoRestaurante;
    private final RepositorioPedidoPlato repositorioPedidoPlato;

    @Autowired
    public ServicioPedidoRestauranteImpl(RepositorioPedidoRestaurante repositorioPedidoRestaurante,RepositorioPedidoPlato repositorioPedidoPlato) {
        this.repositorioPedidoRestaurante = repositorioPedidoRestaurante;
        this.repositorioPedidoPlato=repositorioPedidoPlato;
    }
    @Override
    public List<PedidoDto> traerTodosLosPedidos() {
        List<Pedido> pedidos = repositorioPedidoRestaurante.traerTodosLosPedidos();
        return  pedidos.stream().map(Pedido::obtenerDto).collect(Collectors.toList());
    }

    @Override
    public List<PedidoDto> traerPedidosDelRestaurante(Long id) {
        List<PedidoDto> pedidosTotales = this.traerTodosLosPedidos();

        List<PedidoDto> pedidosFiltrados = new ArrayList<>();

        for (PedidoDto pedido : pedidosTotales) {
            List<PedidoPlatoDto> pedidoPlatosDelRestaurante = pedido.obtenerPlatosDelRestaurante(id);

            if (!pedidoPlatosDelRestaurante.isEmpty()) {
                PedidoDto pedidoFiltrado = new PedidoDto(
                        pedido.getId(),
                        pedido.getFecha(),
                        pedido.getUsuarioId(),
                        pedido.getPrecio(),
                        pedido.isFinalizo(),
                        pedidoPlatosDelRestaurante,
                        pedido.getEstadoPedido()
                );

                pedidosFiltrados.add(pedidoFiltrado);
            }
        }

        return pedidosFiltrados;
    }

    @Override
    public Long obtenerIdDelRestaurate(Long id) {
        return repositorioPedidoRestaurante.obtenerIdDelRestaurate(id);
    }

    @Override
    public void finalizarPedido(Long id) {
        PedidoPlato pedidoPlato = repositorioPedidoPlato.buscarPorId(id);

        if (pedidoPlato != null) {
            pedidoPlato.setEstadoPlato(EstadoPlato.FINALIZADO);

            Pedido pedido = pedidoPlato.getPedido();
/*
            boolean todosFinalizados = pedido.getPedidoPlatos()
                    .stream()
                    .allMatch(pp -> pp.getEstadoPlato() == EstadoPlato.FINALIZADO);

            if (todosFinalizados) {
                pedido.setEstadoPedido(EstadoPedido.FINALIZADO);
                pedido.setFinalizo(true);
            }

 */
        }
    }
}
