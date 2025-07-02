package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.PedidoDto;
import com.tallerwebi.presentacion.PedidoPlatoDto;
import com.tallerwebi.presentacion.UsuarioDTO;

import java.util.List;

public interface PedidoService {

    List<PlatoDto> traerTodosLosPlatos();
    List<PlatoDto> buscarPlatosPorTipoComida(String tipoComida);
    List<PlatoDto> ordenarPlatos(List<PlatoDto> platos,String tipoOrdenar);
    void agregarPlatoAlPedido(PlatoDto plato, UsuarioDTO usuario);
    List<PedidoPlatoDto>mostrarPlatosDelPedidoActual(Long idUsuario);
    Double mostrarPrecioTotalDelPedidoActual(Long idUsuario);
    List<PedidoDto> listarPedidosPorUsuario(Long usuarioId);

    List<PedidoDto> listarPedidosActivosPorUsuario(Long idUsuario);

    List<PedidoDto> listarPedidosEntregadosPorUsuario(Long idUsuario);

    void crearPedido(Long idUsuario);
    void confirmarPedido(Long idUsuario);
    PedidoDto buscarPorId(Integer idPedido);
    List<PlatoDto> traerPlatosDestacadosPorLaEtiquetaDelCliente(Long idCliente);

    void guardarCalificacion(Integer pedidoPlatoId, Integer calificacion, Long id);
}
