package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Pedido;

import com.tallerwebi.presentacion.PedidoDto;
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

    @Autowired
    public ServicioPedidoRestauranteImpl(RepositorioPedidoRestaurante repositorioPedidoRestaurante) {
        this.repositorioPedidoRestaurante = repositorioPedidoRestaurante;
    }
    @Override
    public List<PedidoDto> traerTodosLosPedidos() {
        List<Pedido> pedidos = repositorioPedidoRestaurante.traerTodosLosPedidos();
        return  pedidos.stream().map(Pedido::obtenerDto).collect(Collectors.toList());
    }

    @Override
    public List<PedidoDto> traerPedidosDelRestaurante(Long id) {
        List<PedidoDto> pedidosTotales = this.traerTodosLosPedidos();

        System.out.println("ID del restaurante buscado: " + id);
        System.out.println("Pedidos totales: " + pedidosTotales.size());
        List<PedidoDto> pedidosFiltrados = new ArrayList<>();

        for (PedidoDto pedido : pedidosTotales) {
            List<PlatoDto> platosDelRestaurante = pedido.obtenerPlatosDelRestaurante(id);

            if (!platosDelRestaurante.isEmpty()) {
                System.out.println("Platos del restaurante: " + platosDelRestaurante.size());
                PedidoDto pedidoFiltrado = new PedidoDto(
                        pedido.getId(),
                        pedido.getFecha(),
                        pedido.getUsuarioId(),
                        pedido.getPrecio(),
                        pedido.isFinalizo(),
                        platosDelRestaurante,
                        pedido.getEstadoPedido()
                );

                pedidosFiltrados.add(pedidoFiltrado);
            }
        }
        System.out.println("Pedidos Filtrados: " + pedidosFiltrados.size());
        return pedidosFiltrados;
    }

    @Override
    public Long obtenerIdDelRestaurate(Long id) {
        return repositorioPedidoRestaurante.obtenerIdDelRestaurate(id);
    }
}
