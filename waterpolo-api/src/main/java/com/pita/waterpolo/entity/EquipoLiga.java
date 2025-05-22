package com.pita.waterpolo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "equipo_liga")
public class EquipoLiga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_equipo", nullable = false)
    private Equipo equipo;

    @ManyToOne
    @JoinColumn(name = "id_liga", nullable = false)
    private Liga liga;

    @Column(name = "nombre_equipo_liga", nullable = false)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "equipoLiga")
    private List<JugadorEquipoLiga> jugadores;

    public EquipoLiga(Equipo equipo, Liga liga, String nombre, Boolean activo) {
        this.equipo = equipo;
        this.liga = liga;
        this.nombre = nombre;
        this.activo = activo;
    }

    public EquipoLiga(Equipo equipo, Liga liga, String nombre) {
        this.equipo = equipo;
        this.liga = liga;
        this.nombre = nombre;
    }
}


