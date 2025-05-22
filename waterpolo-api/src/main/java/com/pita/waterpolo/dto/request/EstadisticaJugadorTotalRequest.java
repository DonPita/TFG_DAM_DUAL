package com.pita.waterpolo.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(name = "Estadistica Jugador Total Request",
        description = "Request para la estadística total de un jugador en una temporada")
public record EstadisticaJugadorTotalRequest(
        @Schema(description = "ID del jugador", example = "1")
        @NotNull(message = "El id del jugador no puede ser nulo")
        @Positive
        Integer idJugador,

        @Schema(description = "ID de la temporada", example = "1")
        @NotNull(message = "El id de la temporada no puede ser nulo")
        @Positive
        Integer idTemporada,

        @Schema(description = "Número total de goles", example = "10")
        @NotNull(message = "El número total de goles no puede ser nulo")
        @PositiveOrZero
        Integer golesTotales,

        @Schema(description = "Número total de goles de penalti", example = "5")
        @NotNull(message = "El número total de goles de penalti no puede ser nulo")
        @PositiveOrZero
        Integer golesPenaltiTotales,

        @Schema(description = "Número total de goles en tanda de penaltis", example = "2")
        @NotNull(message = "El número total de goles en tanda de penaltis no puede ser nulo")
        @PositiveOrZero
        Integer golesTandaPenaltiTotales,

        @Schema(description = "Número total de tarjetas amarillas", example = "3")
        @NotNull(message = "El número total de tarjetas amarillas no puede ser nulo")
        @PositiveOrZero
        Integer tarAmarillaTotales,

        @Schema(description = "Número total de tarjetas rojas", example = "1")
        @NotNull(message = "El número total de tarjetas rojas no puede ser nulo")
        @PositiveOrZero
        Integer tarRojaTotales,

        @Schema(description = "Número total de expulsiones", example = "2")
        @NotNull(message = "El número total de expulsiones no puede ser nulo")
        @PositiveOrZero
        Integer expulsionesTotales,

        @Schema(description = "Número total de expulsiones por sustitución definitiva", example = "1")
        @NotNull(message = "El número total de expulsiones por sustitución definitiva no puede ser nulo")
        @PositiveOrZero
        Integer expulsionesSustitucionDTotales,

        @Schema(description = "Número total de expulsiones por brutalidad", example = "0")
        @NotNull(message = "El número total de expulsiones por brutalidad no puede ser nulo")
        @PositiveOrZero
        Integer expulsionesBrutalidadTotales,

        @Schema(description = "Número total de expulsiones por sustitución no definitiva", example = "0")
        @NotNull(message = "El número total de expulsiones por sustitución no definitiva no puede ser nulo")
        @PositiveOrZero
        Integer expulsionesSustitucionNdTotales,

        @Schema(description = "Número total de expulsiones por penalti", example = "0")
        @NotNull(message = "El número total de expulsiones por penalti no puede ser nulo")
        @PositiveOrZero
        Integer expulsionesPenaltiTotales,

        @Schema(description = "Número total de faltas de penalti", example = "0")
        @NotNull(message = "El número total de faltas de penalti no puede ser nulo")
        @PositiveOrZero
        Integer faltasPenaltiTotales,

        @Schema(description = "Número total de penaltis fallados", example = "0")
        @NotNull(message = "El número total de penaltis fallados no puede ser nulo")
        @PositiveOrZero
        Integer penaltiFalladosTotales,

        @Schema(description = "Número total de otros", example = "0")
        @NotNull(message = "El número total de otros no puede ser nulo")
        @PositiveOrZero
        Integer otrosTotales,

        @Schema(description = "Número total de tiempos muertos pedidos", example = "0")
        @NotNull(message = "El número total de tiempos muertos no puede ser nulo")
        @PositiveOrZero
        Integer tiemposMuertosTotales,

        @Schema(description = "Número total de juego limpio", example = "0")
        @NotNull(message = "El número total de juego limpio no puede ser nulo")
        @PositiveOrZero
        Integer juegoLimpioTotales,

        @Schema(description = "Número total de MVP", example = "0")
        @NotNull(message = "El número total de MVP no puede ser nulo")
        @PositiveOrZero
        Integer mvpTotales
) {
}
