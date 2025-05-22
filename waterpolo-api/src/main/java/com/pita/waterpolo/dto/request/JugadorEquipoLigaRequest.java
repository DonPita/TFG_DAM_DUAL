package com.pita.waterpolo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(name = "Jugador Equipo Liga Request", description = "Modelo de datos para la creación o modificacion " +
        "de un jugador dentro de un equipo de una liga")
public record JugadorEquipoLigaRequest(

        @Schema(description = "Identificador del jugador", example = "0")
        @NotNull(message = "El ID del jugador no puede ser nulo")
        @Positive(message = "El ID del jugador debe ser número positivo")
        Integer idJugador,

        @Schema(description = "Identificador del equipo de la liga", example = "0")
        @NotNull(message = "El ID del equipo de la liga no puede ser nulo")
        @Positive(message = "El ID del equipo de la liga debe ser número positivo")
        Integer idEquipoLiga,

        @Schema(description = "Identificador de la temporada", example = "0")
        @NotNull(message = "El ID de la temporada no puede ser nulo")
        @Positive(message = "El ID de la temporada debe ser número positivo")
        Integer idTemporada
) {
}
