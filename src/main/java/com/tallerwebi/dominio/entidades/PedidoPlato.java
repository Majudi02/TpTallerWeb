package com.tallerwebi.dominio.entidades;

import com.tallerwebi.presentacion.PedidoPlatoDto;

import javax.persistence.*;

@Entity
@Table(name = "Pedido_Plato")
public class PedidoPlato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "plato_id")
    private Plato plato;

    @Enumerated(EnumType.STRING)
    private EstadoPlato estadoPlato;

    @Column(name = "calificacion")
    private Integer calificacion;

    public PedidoPlatoDto obtenerDto() {
        PedidoPlatoDto dto = new PedidoPlatoDto();
        dto.setId(this.id);
        dto.setPlato(this.plato != null ? this.plato.obtenerDto() : null);
        dto.setEstadoPlato(this.estadoPlato);
        dto.setCalificacion(this.calificacion);
        return dto;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    public EstadoPlato getEstadoPlato() {
        return estadoPlato;
    }

    public void setEstadoPlato(EstadoPlato estadoPlato) {
        this.estadoPlato = estadoPlato;
    }
}