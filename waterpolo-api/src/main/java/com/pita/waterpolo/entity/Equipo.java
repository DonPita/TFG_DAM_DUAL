package com.pita.waterpolo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "equipo")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "ciudad", nullable = false)
    private String ciudad;

    @Column(name = "fecha_fundacion", nullable = false)
    private LocalDate fechaFundacion;

    @OneToMany(mappedBy = "equipo")
    private List<EquipoLiga> equiposLiga;

    public Equipo(String nombre, String ciudad, LocalDate fechaFundacion) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.fechaFundacion = fechaFundacion;
    }

    public Equipo(Integer id, String nombre, String ciudad, LocalDate fechaFundacion) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.fechaFundacion = fechaFundacion;
    }
}
