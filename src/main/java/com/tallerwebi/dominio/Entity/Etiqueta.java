package com.tallerwebi.dominio.Entity;

import com.tallerwebi.dominio.EtiquetaDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Etiqueta")
public class Etiqueta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @ManyToMany(mappedBy = "etiquetas", fetch = FetchType.LAZY)
    private List<Plato> platos;




    public EtiquetaDto obtenerDto() {
        return new EtiquetaDto(this.id, this.nombre);
    }
    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Plato> getPlatos() { return platos; }
    public void setPlatos(List<Plato> platos) { this.platos = platos; }
}