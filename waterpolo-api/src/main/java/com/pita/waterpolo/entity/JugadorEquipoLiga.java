package com.pita.waterpolo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jugador_equipo_liga")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JugadorEquipoLiga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_jugador", nullable = false)
    private Jugador jugador;

    @ManyToOne
    @JoinColumn(name = "id_equipo_liga", nullable = false)
    private EquipoLiga equipoLiga;

    @ManyToOne
    @JoinColumn(name = "id_temporada", nullable = false)
    private Temporada temporada;

    public JugadorEquipoLiga (Jugador jugador, EquipoLiga equipoLiga, Temporada temporada) {
        this.jugador = jugador;
        this.equipoLiga = equipoLiga;
        this.temporada = temporada;
    }
}
