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

    private String nombre;
    private String descripcion;
    private String imagen;
    private Double precio;

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
        platoDto.setNombre(this.nombre);
        platoDto.setDescripcion(this.descripcion);
        platoDto.setImagen(this.imagen);
        platoDto.setPrecio(this.precio);

        List<EtiquetaDto> etiquetasDto = this.etiquetas.stream()
                .map(e -> new EtiquetaDto(e.getId(), e.getNombre()))
                .collect(Collectors.toList());
        platoDto.setEtiquetas(etiquetasDto);

        return platoDto;
    }

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public List<Etiqueta> getEtiquetas() { return etiquetas; }
    public void setEtiquetas(List<Etiqueta> etiquetas) { this.etiquetas = etiquetas; }
}