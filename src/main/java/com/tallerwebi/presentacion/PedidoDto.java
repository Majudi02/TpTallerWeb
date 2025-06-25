package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.dominio.entidades.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoDto {

    private Integer id;
    private String fecha;
    private Long usuarioId;
    private Double precio;
    private boolean finalizo;
    private List<PedidoPlatoDto> pedidoPlatos;
    private EstadoPedido estadoPedido;
    private boolean platosFinalizados;




    public PedidoDto() {
    }

    public PedidoDto(Integer id, String fecha, Long usuarioId, Double precio, boolean finalizo, List<PedidoPlatoDto> pedidoPlatos, EstadoPedido estadoPedido) {
        this.id = id;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
        this.precio = precio;
        this.finalizo = finalizo;
        this.pedidoPlatos = pedidoPlatos;
        this.estadoPedido = estadoPedido;
    }

    public PedidoDto(Pedido pedido) {
        this.id = pedido.getId();
        this.fecha = pedido.getFecha();
        this.usuarioId = pedido.getUsuario().getId();
        this.precio = pedido.getPrecio();
        this.finalizo = pedido.isFinalizo();
        this.estadoPedido = pedido.getEstadoPedido();

        this.pedidoPlatos = pedido.getPedidoPlatos()
                .stream()
                .map(pedidoPlato -> {
                    PedidoPlatoDto dto = new PedidoPlatoDto();
                    dto.setId(pedidoPlato.getId());
                    dto.setEstadoPlato(pedidoPlato.getEstadoPlato());
                    dto.setPlato(new PlatoDto(pedidoPlato.getPlato()));
                    return dto;
                })
                .collect(Collectors.toList());
    }


    public List<PedidoPlatoDto> getPedidoPlatosDelRestaurante(Long idRestaurante) {
        return pedidoPlatos != null
                ? pedidoPlatos.stream()
                .filter(pp -> pp.getPlato().getIdRestaurante().equals(idRestaurante))
                .collect(Collectors.toList())
                : new ArrayList<>();
    }


    public Pedido obtenerEntidad(UsuarioNutriya usuario) {
        Pedido pedido = new Pedido();
        pedido.setId(this.id);
        pedido.setFecha(this.fecha);
        pedido.setUsuario(usuario);
        pedido.setPrecio(this.precio);
        pedido.setFinalizo(this.finalizo);
        pedido.setEstadoPedido(this.estadoPedido);


        List<PedidoPlato> pedidoPlatosEntidad = this.pedidoPlatos.stream().map(ppDto -> {
            PedidoPlato pedidoPlato = new PedidoPlato();
            pedidoPlato.setPlato(ppDto.getPlato().obtenerEntidad());
            pedidoPlato.setEstadoPlato(ppDto.getEstadoPlato());
            pedidoPlato.setPedido(pedido);
            return pedidoPlato;
        }).collect(Collectors.toList());

        pedido.setPedidoPlatos(pedidoPlatosEntidad);

        return pedido;
    }

    public List<PedidoPlatoDto> obtenerPlatosDelRestaurante(Long idRestaurante) {
        return pedidoPlatos.stream()
                .filter(pp -> pp.getPlato().getIdRestaurante().equals(idRestaurante))
                .collect(Collectors.toList());
    }


    public boolean isPlatosFinalizados() {
        return platosFinalizados;
    }

    public void setPlatosFinalizados(boolean platosFinalizados) {
        this.platosFinalizados = platosFinalizados;
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

    // Getters y setters
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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public List<PedidoPlatoDto> getPedidoPlatos() {
        return pedidoPlatos;
    }

    public void setPedidoPlatos(List<PedidoPlatoDto> pedidoPlatos) {
        this.pedidoPlatos = pedidoPlatos;
    }
}