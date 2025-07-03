package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Etiqueta;
import com.tallerwebi.dominio.entidades.Plato;
import com.tallerwebi.dominio.entidades.Restaurante;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlatoDto {

    private Integer id;
    private Long idRestaurante;
    private String nombre;
    private String descripcion;
    private String imagen;
    private Double precio;
    private Double calorias;
    private Double proteinas;
    private Double grasas;
    private Double carbohidratos;
    private List<Integer> etiquetasIds;
    private List<EtiquetaDto> etiquetas;
    private Double calificacionPromedio;

    public PlatoDto(Integer id, Long idRestaurante, String nombre, String descripcion, String imagen, Double precio, List<EtiquetaDto> etiquetas, Double calorias, Double proteinas, Double grasas, Double carbohidratos) {
        this.id = id;
        this.idRestaurante = idRestaurante;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.precio = precio;
        this.etiquetas = etiquetas;
        this.calorias = calorias;
        this.proteinas = proteinas;
        this.grasas = grasas;
        this.carbohidratos = carbohidratos;
    }

    public PlatoDto(Plato plato) {
        this.id = plato.getId();
        this.idRestaurante = plato.getRestaurante().getId();
        this.nombre = plato.getNombre();
        this.descripcion = plato.getDescripcion();
        this.imagen = plato.getImagen();
        this.precio = plato.getPrecio();
        this.etiquetas = plato.getEtiquetas()
                .stream()
                .map(etiqueta -> new EtiquetaDto(etiqueta.getId(), etiqueta.getNombre()))
                .collect(Collectors.toList());
        this.calorias = plato.getCalorias();
        this.proteinas = plato.getProteinas();
        this.grasas = plato.getGrasas();
        this.carbohidratos = plato.getCarbohidratos();
    }

    public PlatoDto() {
    }

    public Plato obtenerEntidad(List<Etiqueta> listaEtiquetas) {
        Plato plato = new Plato();
        plato.setId(this.id);
        plato.setNombre(this.nombre);
        plato.setDescripcion(this.descripcion);
        plato.setImagen(this.imagen);
        plato.setPrecio(this.precio);
        plato.setEtiquetas(listaEtiquetas);

        Restaurante restaurante = new Restaurante();
        restaurante.setId(this.idRestaurante);
        plato.setRestaurante(restaurante);

        return plato;
    }


    public Plato obtenerDto(List<EtiquetaDto> etiquetasDto) {
        Plato plato = new Plato();
        plato.setId(this.id);
        plato.setNombre(this.nombre);
        plato.setDescripcion(this.descripcion);
        plato.setImagen(this.imagen);
        plato.setPrecio(this.precio);
        plato.setCalorias(this.calorias);
        plato.setProteinas(this.proteinas);
        plato.setGrasas(this.grasas);
        plato.setCarbohidratos(this.carbohidratos);

        List<Etiqueta> listaEtiquetas = new ArrayList<>();

        for (EtiquetaDto dto : etiquetasDto) {
            Etiqueta etiqueta = new Etiqueta();
            etiqueta.setId(dto.getId());
            etiqueta.setNombre(dto.getNombre());
            listaEtiquetas.add(etiqueta);
        }

        plato.setEtiquetas(listaEtiquetas);
        return plato;
    }


    public Plato obtenerEntidad() {
        Plato plato = new Plato();
        plato.setId(this.id);
        plato.setNombre(this.nombre);
        plato.setDescripcion(this.descripcion);
        plato.setImagen(this.imagen);
        plato.setPrecio(this.precio);
        plato.setCalorias(this.calorias);
        plato.setProteinas(this.proteinas);
        plato.setGrasas(this.grasas);
        plato.setCarbohidratos(this.carbohidratos);

        List<Etiqueta> etiquetasEntidad = new ArrayList<>();
        if (this.etiquetas != null) {
            for (EtiquetaDto dto : this.etiquetas) {
                Etiqueta etiqueta = new Etiqueta();
                etiqueta.setId(dto.getId());
                etiqueta.setNombre(dto.getNombre());
                etiquetasEntidad.add(etiqueta);
            }
        }

        plato.setEtiquetas(etiquetasEntidad);
        return plato;
    }

    public Double getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public void setCalificacionPromedio(Double calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    public List<Integer> getEtiquetasIds() {
        return etiquetasIds;
    }

    public void setEtiquetasIds(List<Integer> etiquetasIds) {
        this.etiquetasIds = etiquetasIds;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<EtiquetaDto> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<EtiquetaDto> etiquetas) {
        this.etiquetas = etiquetas;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdRestaurante(Long idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public Long getIdRestaurante() {
        return idRestaurante;
    }

    public Double getCalorias() {
        return calorias;
    }

    public void setCalorias(Double calorias) {
        this.calorias = calorias;
    }

    public Double getProteinas() {
        return proteinas;
    }

    public void setProteinas(Double proteinas) {
        this.proteinas = proteinas;
    }

    public Double getGrasas() {
        return grasas;
    }

    public void setGrasas(Double grasas) {
        this.grasas = grasas;
    }

    public Double getCarbohidratos() {
        return carbohidratos;
    }

    public void setCarbohidratos(Double carbohidratos) {
        this.carbohidratos = carbohidratos;
    }
}