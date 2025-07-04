package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.dominio.entidades.EstadoPlato;
import com.tallerwebi.dominio.entidades.Pedido;
import com.tallerwebi.dominio.entidades.PedidoPlato;

public class PedidoPlatoDto {

    private Long id;
    private PlatoDto plato;
    private EstadoPlato estadoPlato;

    private Integer calificacion;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlatoDto getPlato() {
        return plato;
    }

    public void setPlato(PlatoDto plato) {
        this.plato = plato;
    }

    public EstadoPlato getEstadoPlato() {
        return estadoPlato;
    }

    public void setEstadoPlato(EstadoPlato estadoPlato) {
        this.estadoPlato = estadoPlato;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public PedidoPlato obtenerEntidad() {
        PedidoPlato entidad = new PedidoPlato();
        entidad.setId(this.id);
        entidad.setEstadoPlato(this.estadoPlato);
        entidad.setCalificacion(this.calificacion);

        if (this.plato != null) {
            entidad.setPlato(this.plato.obtenerEntidad());
        }

        return entidad;
    }

    public PedidoPlato obtenerEntidad(Pedido pedido) {
        PedidoPlato entidad = obtenerEntidad();
        entidad.setPedido(pedido);
        return entidad;
    }
}