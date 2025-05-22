package com.pita.waterpolo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "estadistica_jugador_partido")
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticaJugadorPartido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jugador", nullable = false)
    private Jugador jugador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_partido", nullable = false)
    private Partido partido;

    private Integer goles;

    @Column(name = "goles_penalti")
    private Integer golesPenalti;

    @Column(name = "goles_tanda_penalti")
    private Integer golesTandaPenalti;

    @Column(name = "tar_amarilla")
    private Integer amarillas;

    @Column(name = "tar_roja")
    private Integer rojas;

    private Integer expulsiones;

    @Column(name = "expulsiones_sustitucion_d")
    private Integer expulsionSustitucionDefinitiva;

    @Column(name = "expulsiones_brutalidad")
    private Integer expulsionBrutalidad;

    @Column(name = "expulsiones_sustitucion_nd")
    private Integer expulsionSustitucionNoDefinitiva;

    @Column(name = "expulsiones_penalti")
    private Integer expulsionesPenalti;

    @Column(name = "faltas_penalti")
    private Integer faltasPenalti;

    @Column(name = "penalti_fallados")
    private Integer penaltisFallados;

    private Integer otros;

    @Column(name = "tiempos_muertos")
    private Integer tiemposMuertos;

    @Column(name = "juego_limpio")
    private Integer juegoLimpio;

    private Integer mvp;
}










