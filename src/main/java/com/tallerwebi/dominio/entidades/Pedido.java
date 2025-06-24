package com.tallerwebi.dominio.entidades;

import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.presentacion.PedidoDto;
import com.tallerwebi.presentacion.PedidoPlatoDto;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fecha;


     @ManyToOne
     @JoinColumn(name = "usuario_id")
     private UsuarioNutriya usuario;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoPlato> pedidoPlatos;

    private Double precio;
    private boolean finalizo;
    @Enumerated(EnumType.STRING)
    private EstadoPedido estadoPedido;


    public PedidoDto obtenerDto() {
        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.setId(this.id);
        pedidoDto.setFecha(this.fecha);
        pedidoDto.setUsuarioId(this.usuario.getId());
        pedidoDto.setPrecio(this.precio);
        pedidoDto.setFinalizo(this.finalizo);
        pedidoDto.setEstadoPedido(this.estadoPedido);

        List<PedidoPlatoDto> pedidoPlatoDtos = this.pedidoPlatos.stream().map(pp -> {
            PedidoPlatoDto dto = new PedidoPlatoDto();
            dto.setId(pp.getId());
            dto.setEstadoPlato(pp.getEstadoPlato());
            dto.setPlato(pp.getPlato().obtenerDto());
            return dto;
        }).collect(Collectors.toList());

        pedidoDto.setPedidoPlatos(pedidoPlatoDtos);

        return pedidoDto;
    }

    public Boolean todosLosPlatosFinalizados() {
        if (pedidoPlatos == null || pedidoPlatos.isEmpty()) {
            return false;
        }
        for (PedidoPlato pp : pedidoPlatos) {
            if (pp.getEstadoPlato() != EstadoPlato.FINALIZADO) {
                return false;
            }
        }
        return true;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public boolean isFinalizo() {
        return finalizo;
    }

    public void setFinalizo(boolean finalizo) {
        this.finalizo = finalizo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public UsuarioNutriya getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioNutriya usuario) {
        this.usuario = usuario;
    }


    public List<PedidoPlato> getPedidoPlatos() {
        return pedidoPlatos;
    }

    public void setPedidoPlatos(List<PedidoPlato> pedidoPlatos) {
        this.pedidoPlatos = pedidoPlatos;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
