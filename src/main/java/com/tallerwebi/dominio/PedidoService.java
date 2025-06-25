package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Restaurante;
import com.tallerwebi.presentacion.PedidoDto;
import com.tallerwebi.presentacion.PedidoPlatoDto;
import com.tallerwebi.presentacion.UsuarioDTO;

import java.util.List;

public interface PedidoService {

    List<PlatoDto> traerPlatosDestacados();
    List<PlatoDto> traerTodosLosPlatos();
    List<PlatoDto> buscarPlatosPorTipoComida(String tipoComida);
    List<PlatoDto> ordenarPlatos(List<PlatoDto> platos,String tipoOrdenar);
    PedidoDto buscarPedidoActivoPorUsuario(Long idUsuario);
    void agregarPlatoAlPedido(PlatoDto plato, UsuarioDTO usuario);
    List<PedidoPlatoDto>mostrarPlatosDelPedidoActual(Long idUsuario);
    Double mostrarPrecioTotalDelPedidoActual(Long idUsuario);
    void finalizarPedido(Long id);
    List<PedidoDto> listarPedidosPorUsuario(Long usuarioId);
}
