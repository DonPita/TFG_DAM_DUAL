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
@Table(name = "estadistica_jugador_total")
public class EstadisticaJugadorTotal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jugador", nullable = false)
    private Jugador jugador;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_temporada", nullable = false)
    private Temporada temporada;

    @Column(name = "goles_totales")
    private Integer golesTotales;

    @Column(name = "goles_penalti_totales")
    private Integer golesPenaltiTotales;

    @Column(name = "goles_tanda_penalti_totales")
    private Integer golesTandaPenaltiTotales;

    @Column(name = "tar_amarilla_totales")
    private Integer tarAmarillaTotales;

    @Column(name = "tar_roja_totales")
    private Integer tarRojaTotales;

    @Column(name = "expulsiones_totales")
    private Integer expulsionesTotales;

    @Column(name = "expulsiones_sustitucion_d_totales")
    private Integer expulsionesSustitucionDTotales;

    @Column(name = "expulsiones_brutalidad_totales")
    private Integer expulsionesBrutalidadTotales;

    @Column(name = "expulsiones_sustitucion_nd_totales")
    private Integer expulsionesSustitucionNdTotales;

    @Column(name = "expulsiones_penalti_totales")
    private Integer expulsionesPenaltiTotales;

    @Column(name = "faltas_penalti_totales")
    private Integer faltasPenaltiTotales;

    @Column(name = "penalti_fallados_totales")
    private Integer penaltiFalladosTotales;

    @Column(name = "otros_totales")
    private Integer otrosTotales;

    @Column(name = "tiempos_muertos_totales")
    private Integer tiemposMuertosTotales;

    @Column(name = "juego_limpio_totales")
    private Integer juegoLimpioTotales;

    @Column(name = "mvp_totales")
    private Integer mvpTotales;

}