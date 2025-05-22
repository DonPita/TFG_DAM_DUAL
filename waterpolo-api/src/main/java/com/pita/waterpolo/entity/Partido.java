package com.pita.waterpolo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "partido")
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_temporada", nullable = false)
    private Temporada temporada;

    @Column(name = "jornada", nullable = false)
    private Integer jornada;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "id_equipo_liga_local", nullable = false)
    private EquipoLiga equipoLocal;

    @ManyToOne
    @JoinColumn(name = "id_equipo_liga_visitante", nullable = false)
    private EquipoLiga equipoVisitante;

    @Column(name = "goles_local", nullable = false)
    private Integer golesLocal;

    @Column(name = "goles_visitante", nullable = false)
    private Integer golesVisitante;
}
