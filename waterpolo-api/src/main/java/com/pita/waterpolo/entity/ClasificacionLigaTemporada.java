package com.pita.waterpolo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clasificacion_liga_temporada")
public class ClasificacionLigaTemporada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_equipo_liga", nullable = false)
    private EquipoLiga equipoLiga;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_temporada", nullable = false)
    private Temporada temporada;

    @NotNull
    @Column(name = "jornada", nullable = false)
    private Integer jornada;


    @Column(name = "puntos")
    private Integer puntos;

    @Column(name = "partidos_jugados")
    private Integer partidosJugados;

    @Column(name = "victorias")
    private Integer victorias;

    @Column(name = "derrotas")
    private Integer derrotas;

    @Column(name = "empates")
    private Integer empates;

    @Column(name = "goles_a_favor")
    private Integer golesAFavor;

    @Column(name = "goles_en_contra")
    private Integer golesEnContra;

    @Column(name = "diferencia_goles")
    private Integer diferenciaGoles;

    public ClasificacionLigaTemporada(
            Integer id,
            EquipoLiga equipoLiga,
            Integer idTemporada, // Cambiado de Temporada a Integer
            Integer jornada,
            Integer puntos,
            Integer partidosJugados,
            Integer victorias,
            Integer derrotas,
            Integer empates,
            Integer golesAFavor,
            Integer golesEnContra,
            Integer diferenciaGoles
    ) {
        this.id = id;
        this.equipoLiga = equipoLiga;
        this.temporada = new Temporada(); // Crear un objeto Temporada temporal
        this.temporada.setId(idTemporada); // Asignar el ID
        this.jornada = jornada;
        this.puntos = puntos;
        this.partidosJugados = partidosJugados;
        this.victorias = victorias;
        this.derrotas = derrotas;
        this.empates = empates;
        this.golesAFavor = golesAFavor;
        this.golesEnContra = golesEnContra;
        this.diferenciaGoles = diferenciaGoles;
    }
}