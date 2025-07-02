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
    private ServicioPlato servicioPlato;
    private RepositorioPedido repositorioPedido;
    private ServicioUsuario servicioUsuario;
    private ServicioPedidoPlato servicioPedidoPlato;



    @Autowired
    private NotificacionPedidoController notificacionController;

    @Autowired
    public PedidoServiceImpl(ServicioPlato servicioPlato, RepositorioPedido repositorioPedido, ServicioUsuario servicioUsuario,ServicioPedidoPlato servicioPedidoPlato) {
        this.servicioPlato = servicioPlato;
        this.repositorioPedido = repositorioPedido;
        this.servicioUsuario = servicioUsuario;
        this.servicioPedidoPlato = servicioPedidoPlato;
    }


    @Override
    @Transactional
    public List<PlatoDto> traerTodosLosPlatos() {
        return servicioPlato.traerTodosLosPlatos();
    }

    @Override

    public List<PlatoDto> buscarPlatosPorTipoComida(String tipoComida) {
        return servicioPlato.buscarPlatosPorTipoComida(tipoComida);
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
        Pedido pedidoExistente = repositorioPedido.buscarPedidoActivoPorUsuario(idUsuario);
        if (pedidoExistente != null) {
            return;
        }

      //  UsuarioNutriya usuario = repositorioUsuario.buscarPorId(idUsuario);
        UsuarioNutriya usuario =servicioUsuario.buscarPorId(idUsuario);

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

    @Override
    public List<PlatoDto> traerPlatosDestacadosPorLaEtiquetaDelCliente(Long idCliente) {
        return servicioPlato.buscarPlatosPorEtiquetasDelCliente(idCliente);
    }

    @Override
    public void guardarCalificacion(Integer pedidoPlatoId, Integer calificacion, Long id) {
        PedidoPlatoDto pedidoPlatoDto = servicioPedidoPlato.buscarPorId(Long.valueOf(pedidoPlatoId));
        pedidoPlatoDto.setCalificacion(calificacion);
        servicioPedidoPlato.guardar(pedidoPlatoDto);
    }

}
