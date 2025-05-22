package com.pita.waterpolo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "liga")
public class Liga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "liga")
    private List<EquipoLiga> equiposLiga;

    public Liga(String nombre, Boolean activo) {
        this.nombre = nombre;
        this.activo = activo;
    }

    public Liga(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Liga(Integer id, String nombre, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.activo = activo;
    }
}
