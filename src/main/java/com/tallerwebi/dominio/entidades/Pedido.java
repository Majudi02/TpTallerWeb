package com.tallerwebi.dominio.entidades;

import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.presentacion.PedidoDto;

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

    @ManyToMany
    @JoinTable(
            name = "Pedido_Plato",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "plato_id")
    )
    private List<Plato> platos;

    private Double Precio;
    private boolean finalizo;


    public PedidoDto obtenerDto() {
        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.setId(this.id);
        pedidoDto.setFecha(this.fecha);
        pedidoDto.setUsuarioId(this.usuario.getId());
        pedidoDto.setPrecio(this.Precio);
        pedidoDto.setFinalizo(this.finalizo);

        List<PlatoDto> platosDto = this.platos.stream()
                .map(Plato::obtenerDto)
                .collect(Collectors.toList());

        pedidoDto.setPlatos(platosDto);

        return pedidoDto;
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



    public List<Plato> getPlatos() {
        return platos;
    }

    public void setPlatos(List<Plato> platos) {
        this.platos = platos;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }
}
