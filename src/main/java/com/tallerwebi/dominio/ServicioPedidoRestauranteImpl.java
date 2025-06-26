package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.*;

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
public class ServicioPedidoRestauranteImpl implements ServicioPedidoRestaurante {

    private final RepositorioPedidoRestaurante repositorioPedidoRestaurante;
    private final RepositorioPedidoPlato repositorioPedidoPlato;
    private final RepositorioPedido repositorioPedido;

    @Autowired
    public ServicioPedidoRestauranteImpl(RepositorioPedidoRestaurante repositorioPedidoRestaurante,RepositorioPedidoPlato repositorioPedidoPlato,RepositorioPedido repositorioPedido) {
        this.repositorioPedido=repositorioPedido;
        this.repositorioPedidoRestaurante = repositorioPedidoRestaurante;
        this.repositorioPedidoPlato=repositorioPedidoPlato;
    }

    @Override
    public List<PedidoDto> traerTodosLosPedidos() {
        List<Pedido> pedidos = repositorioPedidoRestaurante.traerTodosLosPedidos();
        return pedidos.stream().map(Pedido::obtenerDto).collect(Collectors.toList());
    }

    @Override
    public List<PedidoDto> traerPedidosDelRestaurante(Long id) {
        List<PedidoDto> pedidosTotales = this.traerTodosLosPedidos(); // trae todos, sin filtrar por estado

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
    public void finalizarPlatoPedido(Long id) {
        PedidoPlato pedidoPlato = repositorioPedidoPlato.buscarPorId(id);

        if (pedidoPlato != null) {
            pedidoPlato.setEstadoPlato(EstadoPlato.FINALIZADO);
            // Guardar el cambio del plato
            repositorioPedidoPlato.guardar(pedidoPlato);

       //     confirmarPedidoListoParaEnviar(pedidoPlato.getPedido().getId());
        }
    }

    @Override
    public void confirmarPedidoListoParaEnviar(Integer idPedido) {
        Pedido pedido = repositorioPedidoRestaurante.buscarPorId(idPedido);

        if (pedido != null) {
            // Validar que TODOS los platos están finalizados antes de cambiar
            boolean todosFinalizados = pedido.getPedidoPlatos()
                    .stream()
                    .allMatch(pp -> pp.getEstadoPlato() == EstadoPlato.FINALIZADO);

            if (todosFinalizados) {
                pedido.setEstadoPedido(EstadoPedido.LISTO_PARA_ENVIAR);
                pedido.setFinalizo(false);
                repositorioPedidoRestaurante.guardar(pedido);
            } else {
                // Opcional: lanzar excepción o error si no todos están finalizados
                throw new IllegalStateException("No todos los platos están finalizados");
            }
        }
    }


    @Override
    public List<PedidoVistaDto> traerPedidosListosParaVista() {
        List<Pedido> pedidosListos = repositorioPedidoRestaurante.traerPedidosListosParaRetirar();
        List<PedidoVistaDto> dtos = new ArrayList<>();

        for (Pedido pedido : pedidosListos) {
            PedidoVistaDto dto = new PedidoVistaDto();
            dto.setPedidoId(pedido.getId());

            // Dirección del cliente
            // CAMBIAR DESPUES, SOLO AGARRA LA 1ER DIRECCION
            if (pedido.getUsuario() instanceof Cliente) {
                Cliente cliente = (Cliente) pedido.getUsuario();
                if (cliente.getDirecciones() != null && !cliente.getDirecciones().isEmpty()) {
                    Direccion direccion = cliente.getDirecciones().get(0);
                    dto.setDireccionCliente(direccion.getCalle() + " " + direccion.getNumero() + ", Localidad: " + direccion.getLocalidad());
                } else {
                    dto.setDireccionCliente("Dirección no disponible");
                }
            }

            // Armado de platos y datos del restaurante
            List<PlatoCantidadDto> platos = new ArrayList<>();

            if (pedido.getPedidoPlatos() != null && !pedido.getPedidoPlatos().isEmpty()) {
                Restaurante restaurante = pedido.getPedidoPlatos().get(0).getPlato().getRestaurante();
                dto.setNombreRestaurante(restaurante.getNombre());
                dto.setDireccionRestaurante(restaurante.getCalle() + " " + restaurante.getNumero() + ", Localidad: " + restaurante.getLocalidad());

                for (PedidoPlato pp : pedido.getPedidoPlatos()) {
                    Plato plato = pp.getPlato();
                    int yaExistente = -1;

                    // Sumamos cantidades iguales
                    for (int i = 0; i < platos.size(); i++) {
                        if (platos.get(i).getNombreProducto().equals(plato.getNombre())) {
                            yaExistente = i;
                            break;
                        }
                    }

                    if (yaExistente != -1) {
                        PlatoCantidadDto existente = platos.get(yaExistente);
                        existente.setCantidad(existente.getCantidad() + 1);
                    } else {
                        platos.add(new PlatoCantidadDto(plato.getNombre(), 1));
                    }
                }
            }

            dto.setProductos(platos);
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public void entregarPedido(Integer idPedido) {
        repositorioPedidoRestaurante.entregarPedido(idPedido);
    }

    @Override
    public PedidoVistaDto traerDetallePedidoPorId(Integer id) {
        Pedido pedido = repositorioPedidoRestaurante.buscarPorId(id);
        if (pedido == null) return null;

        // Aquí convertís Pedido a PedidoVistaDto (similar a traerPedidosListosParaVista pero solo uno)
        PedidoVistaDto dto = new PedidoVistaDto();
        dto.setPedidoId(pedido.getId());

        // Dirección cliente (como hacés ya)
        if (pedido.getUsuario() instanceof Cliente) {
            Cliente cliente = (Cliente) pedido.getUsuario();
            if (cliente.getDirecciones() != null && !cliente.getDirecciones().isEmpty()) {
                Direccion direccion = cliente.getDirecciones().get(0);
                dto.setDireccionCliente(direccion.getCalle() + " " + direccion.getNumero() + ", Localidad: " + direccion.getLocalidad());
            } else {
                dto.setDireccionCliente("Dirección no disponible");
            }
        }

        // Datos restaurante
        if (!pedido.getPedidoPlatos().isEmpty()) {
            Restaurante restaurante = pedido.getPedidoPlatos().get(0).getPlato().getRestaurante();
            dto.setNombreRestaurante(restaurante.getNombre());
            dto.setDireccionRestaurante(restaurante.getCalle() + " " + restaurante.getNumero() + ", Localidad: " + restaurante.getLocalidad());
        }

        // Platos con cantidades
        List<PlatoCantidadDto> platos = new ArrayList<>();
        for (PedidoPlato pp : pedido.getPedidoPlatos()) {
            Plato plato = pp.getPlato();
            int index = -1;
            for (int i = 0; i < platos.size(); i++) {
                if (platos.get(i).getNombreProducto().equals(plato.getNombre())) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                platos.get(index).setCantidad(platos.get(index).getCantidad() + 1);
            } else {
                platos.add(new PlatoCantidadDto(plato.getNombre(), 1));
            }
        }
        dto.setProductos(platos);

        return dto;
    }

    @Override
    public void finalizarPedidoCompleto(Integer idPedido) {
        Pedido pedidoBuscado= repositorioPedido.buscarPorId(idPedido);

        if (pedidoBuscado != null) {
            pedidoBuscado.setEstadoPedido(EstadoPedido.LISTO_PARA_ENVIAR);

            for (PedidoPlato plato : pedidoBuscado.getPedidoPlatos()) {
                plato.setEstadoPlato(EstadoPlato.FINALIZADO);
            }
        }

    }

}
