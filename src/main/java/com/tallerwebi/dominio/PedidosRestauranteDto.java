package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.PedidoDto;

import java.util.List;

public class PedidosRestauranteDto {
    private List<PedidoDto> enPreparacion;
    private List<PedidoDto> listosParaEnviar;
    private List<PedidoDto> entregados;

    public PedidosRestauranteDto(List<PedidoDto> enPreparacion, List<PedidoDto> listosParaEnviar, List<PedidoDto> entregados) {
        this.enPreparacion = enPreparacion;
        this.listosParaEnviar = listosParaEnviar;
        this.entregados = entregados;
    }

    public List<PedidoDto> getEnPreparacion() {
        return enPreparacion;
    }

    public void setEnPreparacion(List<PedidoDto> enPreparacion) {
        this.enPreparacion = enPreparacion;
    }

    public List<PedidoDto> getListosParaEnviar() {
        return listosParaEnviar;
    }

    public void setListosParaEnviar(List<PedidoDto> listosParaEnviar) {
        this.listosParaEnviar = listosParaEnviar;
    }

    public List<PedidoDto> getEntregados() {
        return entregados;
    }

    public void setEntregados(List<PedidoDto> entregados) {
        this.entregados = entregados;
    }
}
