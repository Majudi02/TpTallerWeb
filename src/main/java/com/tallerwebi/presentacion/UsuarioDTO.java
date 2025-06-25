package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Direccion;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class UsuarioDTO {
    private String tipoUsuario;
    private Long id;

    private String email;
    private String password;
    private String tokenConfirmacion;
    private boolean confirmado;

    // Campos Repartidor
    private String nombre;
    private String apellido;
    private Integer dni;
    private String telefono;
    private String vehiculo;


    // Campos Cliente
    private Integer edad;
    private Integer pesoActual;
    private Integer pesoDeseado;
    private Double altura;
    private String objetivo;
    private List<String> etiquetas;
    private List<DireccionDto> direcciones;

    // Campos Restaurante
    private String descripcion;
    private MultipartFile imagenRestaurante;
    private String imagen;
    private String calle;
    private Integer numero;
    private String localidad;
    private String zona;
    private List<String> tipoComidas;

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "id=" + id +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", tokenConfirmacion='" + tokenConfirmacion + '\'' +
                ", confirmado=" + confirmado +
                ", descripcion='" + descripcion + '\'' +
                ", imagen='" + imagen + '\'' +
                ", calle='" + calle + '\'' +
                ", numero=" + numero +
                ", localidad='" + localidad + '\'' +
                ", zona='" + zona + '\'' +
                ", tipoComidas=" + tipoComidas +
                '}';
    }


    public boolean getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    public String getTokenConfirmacion() {
        return tokenConfirmacion;
    }

    public void setTokenConfirmacion(String tokenConfirmacion) {
        this.tokenConfirmacion = tokenConfirmacion;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
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

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public List<String> getTipoComidas() {
        return tipoComidas;
    }

    public void setTipoComidas(List<String> tipoComidas) {
        this.tipoComidas = tipoComidas;
    }

    public MultipartFile getImagenRestaurante() {
        return imagenRestaurante;
    }

    public void setImagenRestaurante(MultipartFile imagenRestaurante) {
        this.imagenRestaurante = imagenRestaurante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DireccionDto> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<DireccionDto> direcciones) {
        this.direcciones = direcciones;
    }
}

