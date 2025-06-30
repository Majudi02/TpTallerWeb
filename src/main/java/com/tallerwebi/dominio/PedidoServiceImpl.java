package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.presentacion.NotificacionPedidoController;
import com.tallerwebi.presentacion.PedidoPlatoDto;
import com.tallerwebi.infraestructura.RepositorioPlatoImpl;
import com.tallerwebi.presentacion.PedidoDto;
import com.tallerwebi.presentacion.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {
    private final RepositorioPlatoImpl repositorioPlatoImpl;
    private RepositorioPedido repositorioPedido;
    private RepositorioUsuarioNutriya repositorioUsuario;


    @Autowired
    private NotificacionPedidoController notificacionController;

    @Autowired
    public PedidoServiceImpl(RepositorioPlatoImpl repositorioPlatoImpl, RepositorioPedido repositorioPedido, RepositorioUsuarioNutriya repositorioUsuario) {
        this.repositorioPlatoImpl = repositorioPlatoImpl;
        this.repositorioPedido = repositorioPedido;
        this.repositorioUsuario = repositorioUsuario;
    }


    @Override
    public List<PlatoDto> traerPlatosDestacados() {
        return null;
    }

    @Override
    @Transactional
    public List<PlatoDto> traerTodosLosPlatos() {
        List<Plato> platos = this.repositorioPlatoImpl.traerTodosLosPlatos();
        return platos.stream().map(Plato::obtenerDto).collect(Collectors.toList());
    }

    @Override

    public List<PlatoDto> buscarPlatosPorTipoComida(String tipoComida) {
        List<Plato> platos = this.repositorioPlatoImpl.buscarPlatosPorTipoComida(tipoComida);
        return platos.stream().map(Plato::obtenerDto).collect(Collectors.toList());
    }

    public List<PlatoDto> ordenarPlatos(List<PlatoDto> platos, String tipoOrdenar) {
        List<PlatoDto> platosOrdenados = new ArrayList<>(platos);
        if (tipoOrdenar.equals("mayorAMenor")) {
            platosOrdenados.sort(Comparator.comparing(PlatoDto::getPrecio).reversed());
        } else {
            platosOrdenados.sort(Comparator.comparing(PlatoDto::getPrecio));
        }
        return platosOrdenados;
    }

    @Override
    public void agregarPlatoAlPedido(PlatoDto platoDto, UsuarioDTO usuarioDTO) {
        List<Etiqueta> etiquetasEntidad = new ArrayList<>();
        if (platoDto.getEtiquetas() != null) {
            for (EtiquetaDto etiquetaDto : platoDto.getEtiquetas()) {
                Etiqueta etiqueta = new Etiqueta();
                etiqueta.setId(etiquetaDto.getId());
                etiqueta.setNombre(etiquetaDto.getNombre());
                etiquetasEntidad.add(etiqueta);
            }
        }


        Plato platoEntidad = platoDto.obtenerEntidad(etiquetasEntidad);

        this.repositorioPedido.agregarPlatoAlPedido(platoEntidad, usuarioDTO.getId());
    }

    @Override
    public List<PedidoPlatoDto> mostrarPlatosDelPedidoActual(Long idUsuario) {
        System.out.println("Buscando platos del pedido actual del usuario ID: " + idUsuario);
        return this.repositorioPedido
                .mostrarPlatosDelPedidoActual(idUsuario)
                .stream()
                .map(PedidoPlato::obtenerDto)
                .collect(Collectors.toList());
    }


    @Override
    public Double mostrarPrecioTotalDelPedidoActual(Long idUsuario) {
        return this.repositorioPedido.mostrarPrecioTotalDelPedidoActual(idUsuario);
    }


    @Override
    public void finalizarPedido(Long id) {
        Pedido pedidoFinalizado = this.repositorioPedido.finalizarPedido(id);
        if (pedidoFinalizado != null) {
            notificacionController.notificarMensaje("**Nuevo pedido disponible**");
        }
    }

    @Override
    public List<PedidoDto> listarPedidosPorUsuario(Long usuarioId) {
        return repositorioPedido.listarPedidosPorUsuario(usuarioId).stream()
                .map(Pedido::obtenerDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoDto> listarPedidosActivosPorUsuario(Long idUsuario) {
        List<Pedido> pedidos = repositorioPedido.listarPedidosPorUsuario(idUsuario);
        List<PedidoDto> activos = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            if (!pedido.isFinalizo()) {
                activos.add(pedido.obtenerDto());
            }
        }

        return activos;
    }

    @Override
    public List<PedidoDto> listarPedidosEntregadosPorUsuario(Long idUsuario) {
        List<Pedido> pedidos = repositorioPedido.listarPedidosPorUsuario(idUsuario);
        List<PedidoDto> entregados = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            if (pedido.isFinalizo()) {
                entregados.add(pedido.obtenerDto());
            }
        }

        return entregados;
    }


    @Override
    public void crearPedido(Long idUsuario) {
        // Verificar si ya existe un pedido activo para ese usuario
        Pedido pedidoExistente = repositorioPedido.buscarPedidoActivoPorUsuario(idUsuario);
        if (pedidoExistente != null) {
            // Ya hay un pedido activo, no crear uno nuevo
            return;
        }

        UsuarioNutriya usuario = repositorioUsuario.buscarPorId(idUsuario);

        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setUsuario(usuario);
        nuevoPedido.setEstadoPedido(EstadoPedido.PENDIENTE);
        nuevoPedido.setFecha(LocalDateTime.now().toString());
        nuevoPedido.setFinalizo(false);
        nuevoPedido.setPrecio(0.0);
        nuevoPedido.setPedidoPlatos(new ArrayList<>());

        repositorioPedido.crearPedido(nuevoPedido);
    }

    @Override
    public void confirmarPedido(Long idUsuario) {
        repositorioPedido.confirmarPedido(idUsuario);
        Pedido pedido = repositorioPedido.buscarPedidoActivoPorUsuario(idUsuario);
        if (pedido != null) {
            notificacionController.notificarMensaje("**Nuevo pedido disponible**");
        }
    }

    @Override
    public PedidoDto buscarPorId(Integer idPedido) {
        Pedido pedido= repositorioPedido.buscarPorId(idPedido);
        return pedido.obtenerDto();
    }

}
