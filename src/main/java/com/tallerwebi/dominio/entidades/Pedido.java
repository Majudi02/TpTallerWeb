package com.tallerwebi.dominio.entidades;

import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.presentacion.PedidoDto;
import com.tallerwebi.presentacion.PedidoPlatoDto;

import javax.persistence.*;
import java.util.ArrayList;
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
    private boolean pagado;


    public PedidoDto obtenerDto() {
        PedidoDto dto = new PedidoDto();
        dto.setId(this.id);
        dto.setFecha(this.fecha);
        dto.setPrecio(this.precio);
        dto.setEstadoPedido(this.estadoPedido);

        List<PedidoPlatoDto> platosDto = new ArrayList<>();
        for (PedidoPlato pedidoPlato : this.pedidoPlatos) {
            PedidoPlatoDto pedidoPlatoDto = new PedidoPlatoDto();
            pedidoPlatoDto.setId(pedidoPlato.getId());
            pedidoPlatoDto.setPlato(pedidoPlato.getPlato().obtenerDto());
            pedidoPlatoDto.setEstadoPlato(pedidoPlato.getEstadoPlato());
            pedidoPlatoDto.setCalificacion(pedidoPlato.getCalificacion());

            platosDto.add(pedidoPlatoDto);
        }

        dto.setPedidoPlatos(platosDto);
        return dto;
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

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
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
