package com.pita.waterpolo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(
        description = "La Clasificación de una Liga en una Temporada específica.",
        title = "Clasificacion LigaTemporada Request")
public record ClasificacionLigaTemporadaRequest(
        @Schema(description = "ID del equipo en la liga", example = "1")
        @NotNull(message = "El ID del Equipo Liga no puede ser nulo")
        @Positive(message = "El ID del Equipo Liga debe ser un número positivo")
        Integer idEquipoLiga,

        @Schema(description = "ID de la temporada", example = "1")
        @NotNull(message = "El ID de la Temporada no puede ser nulo")
        @Positive(message = "El ID de la Temporada debe ser un número positivo")
        Integer idTemporada,

        @Schema(description = "Número de jornada", example = "1")
        @NotNull(message = "La jornada no puede ser nula")
        @Positive(message = "La jornada debe ser un número positivo")
        Integer jornada,

        @Schema(description = "Puntos obtenidos", example = "10")
        @PositiveOrZero(message = "Los puntos deben ser cero o positivos")
        Integer puntos,

        @Schema(description = "Partidos jugados", example = "5")
        @PositiveOrZero(message = "Los partidos jugados deben ser cero o positivos")
        Integer partidosJugados,

        @Schema(description = "Victorias obtenidas", example = "3")
        @PositiveOrZero(message = "Las victorias deben ser cero o positivas")
        Integer victorias,

        @Schema(description = "Derrotas sufridas", example = "2")
        @PositiveOrZero(message = "Las derrotas deben ser cero o positivas")
        Integer derrotas,

        @Schema(description = "Empates obtenidos", example = "0")
        @PositiveOrZero(message = "Los empates deben ser cero o positivos")
        Integer empates,

        @Schema(description = "Goles a favor", example = "15")
        @PositiveOrZero(message = "Los goles a favor deben ser cero o positivos")
        Integer golesAFavor,

        @Schema(description = "Goles en contra", example = "10")
        @PositiveOrZero(message = "Los goles en contra deben ser cero o positivos")
        Integer golesEnContra,

        @Schema(description = "Diferencia de goles", example = "5")
        @PositiveOrZero(message = "La diferencia de goles debe ser cero o positiva")
        Integer diferenciaGoles
) {
}
