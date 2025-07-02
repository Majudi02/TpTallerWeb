package com.tallerwebi.dominio.entidades;

import com.tallerwebi.dominio.EtiquetaDto;
import com.tallerwebi.dominio.PlatoDto;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Plato")
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    private String nombre;
    private String descripcion;
    private String imagen;
    private Double precio;
    private Double calorias;
    private Double proteinas;
    private Double grasas;
    private Double carbohidratos;

    @ManyToMany()
    @JoinTable(
            name = "Plato_Etiqueta",
            joinColumns = @JoinColumn(name = "plato_id"),
            inverseJoinColumns = @JoinColumn(name = "etiqueta_id")
    )
    private List<Etiqueta> etiquetas;

    public PlatoDto obtenerDto() {
        PlatoDto platoDto = new PlatoDto();
        platoDto.setId(this.id);

        if (this.restaurante != null) {
            platoDto.setIdRestaurante(restaurante.getId());
        } else {
            platoDto.setIdRestaurante(null);
        }

        platoDto.setNombre(this.nombre);
        platoDto.setDescripcion(this.descripcion);
        platoDto.setImagen(this.imagen);
        platoDto.setPrecio(this.precio);
        platoDto.setCalorias(this.calorias);
        platoDto.setProteinas(this.proteinas);
        platoDto.setGrasas(this.grasas);
        platoDto.setCarbohidratos(this.carbohidratos);

        List<EtiquetaDto> etiquetasDto = this.etiquetas.stream()
                .map(e -> new EtiquetaDto(e.getId(), e.getNombre()))
                .collect(Collectors.toList());
        platoDto.setEtiquetas(etiquetasDto);

        return platoDto;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Double getCarbohidratos() {
        return carbohidratos;
    }

    public void setCarbohidratos(Double carbohidratos) {
        this.carbohidratos = carbohidratos;
    }

    public Double getGrasas() {
        return grasas;
    }

    public void setGrasas(Double grasas) {
        this.grasas = grasas;
    }

    public Double getProteinas() {
        return proteinas;
    }

    public void setProteinas(Double proteinas) {
        this.proteinas = proteinas;
    }

    public Double getCalorias() {
        return calorias;
    }

    public void setCalorias(Double calorias) {
        this.calorias = calorias;
    }
}