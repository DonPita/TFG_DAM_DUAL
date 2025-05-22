package com.pita.waterpolo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(name = "Estadistica Jugador Partido Request",
        description = "Request para la estadística de un jugador en un partido")
public record EstadisticaJugadorPartidoRequest(
        @Schema(description = "ID del jugador", example = "1")
        @NotNull(message = "El id del jugador no puede ser nulo")
        @Positive(message = "El id del jugador debe ser positivo")
        Integer idJugador,

        @Schema(description = "ID del partido", example = "1")
        @NotNull(message = "El id del partido no puede ser nulo")
        @Positive(message = "El id del partido debe ser positivo")
        Integer idPartido,

        @Schema(description = "Número de goles", example = "2")
        @NotNull(message = "El número de goles no puede ser nulo")
        @PositiveOrZero(message = "El número de goles debe ser positivo o cero")
        Integer goles,

        @Schema(description = "Número de goles de penalti", example = "1")
        @NotNull(message = "El número de goles de penalti no puede ser nulo")
        @PositiveOrZero(message = "El número de goles de penalti debe ser positivo o cero")
        Integer golesPenalti,

        @Schema(description = "Número de goles en tanda de penaltis", example = "0")
        @NotNull(message = "El número de goles en tanda de penaltis no puede ser nulo")
        @PositiveOrZero(message = "El número de goles en tanda de penaltis debe ser positivo o cero")
        Integer golesTandaPenalti,

        @Schema(description = "Número de tarjetas amarillas", example = "1")
        @NotNull(message = "El número de tarjetas amarillas no puede ser nulo")
        @PositiveOrZero(message = "El número de tarjetas amarillas debe ser positivo o cero")
        Integer amarillas,

        @Schema(description = "Número de tarjetas rojas", example = "0")
        @NotNull(message = "El número de tarjetas rojas no puede ser nulo")
        @PositiveOrZero(message = "El número de tarjetas rojas debe ser positivo o cero")
        Integer rojas,

        @Schema(description = "Número de expulsiones", example = "0")
        @NotNull(message = "El número de expulsiones no puede ser nulo")
        @PositiveOrZero(message = "El número de expulsiones debe ser positivo o cero")
        Integer expulsiones,

        @Schema(description = "Número de expulsiones por sustitución definitiva", example = "0")
        @NotNull(message = "El número de expulsiones por sustitución definitiva no puede ser nulo")
        @PositiveOrZero(message = "El número de expulsiones por sustitución definitiva debe ser positivo o cero")
        Integer expulsionSustitucionDefinitiva,

        @Schema(description = "Número de expulsiones por brutalidad", example = "0")
        @NotNull(message = "El número de expulsiones por brutalidad no puede ser nulo")
        @PositiveOrZero(message = "El número de expulsiones por brutalidad debe ser positivo o cero")
        Integer expulsionBrutalidad,

        @Schema(description = "Número de expulsiones por sustitución no definitiva", example = "0")
        @NotNull(message = "El número de expulsiones por sustitución no definitiva no puede ser nulo")
        @PositiveOrZero(message = "El número de expulsiones por sustitución no definitiva debe ser positivo o cero")
        Integer expulsionSustitucionNoDefinitiva,

        @Schema(description = "Número de expulsiones por penalti", example = "0")
        @NotNull(message = "El número de expulsiones por penalti no puede ser nulo")
        @PositiveOrZero(message = "El número de expulsiones por penalti debe ser positivo o cero")
        Integer expulsionesPenalti,

        @Schema(description = "Número de faltas por penalti", example = "0")
        @NotNull(message = "El número de faltas por penalti no puede ser nulo")
        @PositiveOrZero(message = "El número de faltas por penalti debe ser positivo o cero")
        Integer faltasPenalti,

        @Schema(description = "Número de penaltis fallados", example = "0")
        @NotNull(message = "El número de penaltis fallados no puede ser nulo")
        @PositiveOrZero(message = "El número de penaltis fallados debe ser positivo o cero")
        Integer penaltisFallados,

        @Schema(description = "Otros", example = "0")
        @NotNull(message = "El número de otros no puede ser nulo")
        @PositiveOrZero(message = "El número de otros debe ser positivo o cero")
        Integer otros,

        @Schema(description = "Número de tiempos muertos", example = "0")
        @PositiveOrZero(message = "El número de tiempos muertos debe ser positivo o cero")
        Integer tiemposMuertos,

        @Schema(description = "Número de premios de juego limpio", example = "0")
        @PositiveOrZero(message = "El número de premios de juego limpio debe ser positivo o cero")
        Integer juegoLimpio,

        @Schema(description = "Número de MVP", example = "0")
        @PositiveOrZero(message = "El número de MVP debe ser positivo o cero")
        Integer mvp
) {
}
