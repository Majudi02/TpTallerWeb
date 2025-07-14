package com.tallerwebi.dominio.entidades;

import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.presentacion.PedidoDto;
import com.tallerwebi.presentacion.PedidoPlatoDto;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioNutriya usuario;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoPlato> pedidoPlatos;

    private Double precio;
    private boolean finalizo;
    @Enumerated(EnumType.STRING)
    private EstadoPedido estadoPedido;
    private boolean pagado;
    @ManyToOne
    @JoinColumn(name = "repartidor_id")
    private UsuarioNutriya repartidor;


    public PedidoDto obtenerDto() {
        PedidoDto dto = new PedidoDto();
        dto.setId(this.id);
        dto.setPrecio(this.precio);
        dto.setEstadoPedido(this.estadoPedido);

        if (this.fecha != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            dto.setFecha(sdf.format(this.fecha));
        } else {
            dto.setFecha(null);
        }

        if (this.restaurante != null) {
            dto.setIdRestaurante(this.restaurante.getId());
        }

        List<PedidoPlatoDto> platosDto = new ArrayList<>();
        if (this.pedidoPlatos != null) {
            for (PedidoPlato pedidoPlato : this.pedidoPlatos) {
                PedidoPlatoDto pedidoPlatoDto = new PedidoPlatoDto();
                pedidoPlatoDto.setId(pedidoPlato.getId());
                pedidoPlatoDto.setPlato(pedidoPlato.getPlato().obtenerDto());
                pedidoPlatoDto.setEstadoPlato(pedidoPlato.getEstadoPlato());
                pedidoPlatoDto.setCalificacion(pedidoPlato.getCalificacion());

                platosDto.add(pedidoPlatoDto);
            }
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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
        return pedidoPlatos.stream()
                .mapToDouble(pp -> pp.getPlato().getPrecio())
                .sum();
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public UsuarioNutriya getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(UsuarioNutriya repartidor) {
        this.repartidor = repartidor;
    }
}
