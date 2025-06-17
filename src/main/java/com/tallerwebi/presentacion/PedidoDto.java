package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PlatoDto;
import com.tallerwebi.dominio.entidades.Pedido;
import com.tallerwebi.dominio.entidades.Plato;
import com.tallerwebi.dominio.entidades.UsuarioNutriya;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoDto {

    private Integer id;
    private String fecha;
    private Long usuarioId;
    private Double precio;
    private boolean finalizo;
    private List<PlatoDto> platos;


    public PedidoDto() {
    }

    public PedidoDto(Integer id, String fecha, Long usuarioId, Double precio,boolean finalizo, List<PlatoDto> platos) {
        this.id = id;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
        this.precio = precio;
        this.finalizo=finalizo;
        this.platos = platos;
    }

    public PedidoDto(Pedido pedido) {
        this.id = pedido.getId();
        this.fecha = pedido.getFecha();
        this.usuarioId = pedido.getUsuario().getId();
        this.precio = pedido.getPrecio();
        this.finalizo=pedido.isFinalizo();
        this.platos = pedido.getPlatos()
                .stream()
                .map(PlatoDto::new)
                .collect(Collectors.toList());
    }

    
    public Pedido obtenerEntidad(UsuarioNutriya usuario, List<Plato> platosEntidad) {
        Pedido pedido = new Pedido();
        pedido.setId(this.id);
        pedido.setFecha(this.fecha);
        pedido.setUsuario(usuario);
        pedido.setPlatos(platosEntidad);
        pedido.setPrecio(this.precio);
        pedido.setFinalizo(this.finalizo);
        return pedido;
    }

    public List<Plato> obtenerPlatosEntidad() {
        List<Plato> platosEntidad = new ArrayList<>();

        for (PlatoDto platoDto : platos) {
            platosEntidad.add(platoDto.obtenerEntidad());
        }

        return platosEntidad;
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

    public List<PlatoDto> getPlatos() {
        return platos;
    }

    public void setPlatos(List<PlatoDto> platos) {
        this.platos = platos;
    }
}