package com.tallerwebi.dominio.entidades;

import com.tallerwebi.presentacion.DireccionDto;
import com.tallerwebi.presentacion.UsuarioDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Cliente extends UsuarioNutriya {

    private String nombre;
    private Integer edad;
    private Integer pesoActual;
    private Integer pesoDeseado;
    private Double altura;
    private String objetivo;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Direccion> direcciones = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "Cliente_Etiqueta",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "etiqueta_id"))
    private List<Etiqueta> etiquetas = new ArrayList<>();



    public Cliente obtenerEntidad(UsuarioDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setEmail(dto.getEmail());
        cliente.setPassword(dto.getPassword());
        cliente.setEdad(dto.getEdad());
        cliente.setPesoActual(dto.getPesoActual());
        cliente.setPesoDeseado(dto.getPesoDeseado());
        cliente.setAltura(dto.getAltura());
        cliente.setObjetivo(dto.getObjetivo());
        cliente.setConfirmado(dto.getConfirmado());
        cliente.setTokenConfirmacion(dto.getTokenConfirmacion());

        return cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getPesoActual() {
        return pesoActual;
    }

    public void setPesoActual(Integer pesoActual) {
        this.pesoActual = pesoActual;
    }

    public Integer getPesoDeseado() {
        return pesoDeseado;
    }

    public void setPesoDeseado(Integer pesoDeseado) {
        this.pesoDeseado = pesoDeseado;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    @Override
    public String tipoUsuario() {
        return "CLIENTE";
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    public List<DireccionDto> obtenerDireccionesDto() {
        return this.direcciones.stream()
                .map(Direccion::obtenerDto)
                .collect(Collectors.toList());
    }

    public UsuarioDTO obtenerDto() {
        UsuarioDTO dto = new UsuarioDTO();

        dto.setId(this.getId());
        dto.setEmail(this.getEmail());
        dto.setPassword(this.getPassword());
        dto.setTokenConfirmacion(this.getTokenConfirmacion());
        dto.setConfirmado(this.getConfirmado());

        dto.setNombre(this.getNombre());
        dto.setEdad(this.getEdad());
        dto.setPesoActual(this.getPesoActual());
        dto.setPesoDeseado(this.getPesoDeseado());
        dto.setAltura(this.getAltura());
        dto.setObjetivo(this.getObjetivo());

        dto.setTipoUsuario(this.tipoUsuario());

        if (this.getEtiquetas() != null) {
            List<String> etiquetasNombres = this.getEtiquetas()
                    .stream()
                    .map(Etiqueta::getNombre)
                    .collect(Collectors.toList());
            dto.setEtiquetas(etiquetasNombres);
        }

        dto.setDirecciones(this.obtenerDireccionesDto());

        return dto;
    }

}
