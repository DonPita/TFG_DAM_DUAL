package com.pita.waterpolo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

@Schema(name = "Partido Request", description = "Modelo de datos para crear o actualizar un partido")
public record PartidoRequest(

        @Schema(description = "Identificador de la temporada", example = "2")
        @NotNull(message = "El ID de la temporada no puede ser nulo")
        @Positive(message = "El ID de la temporada debe ser número positivo")
        Integer idTemporada,

        @Schema(description = "Jornada del partido", example = "1")
        @NotNull(message = "La jornada no puede ser nula")
        @Positive(message = "La jornada debe ser número positivo")
        Integer jornada,

        @Schema(description = "Fecha y hora del partido", example = "13:00 01-01-2025")
        @NotNull(message = "La fecha no puede ser nula")
        @JsonFormat(pattern = "HH:mm dd-MM-yyyy") LocalDateTime fecha,

        @Schema(description = "Identificador del equipo local", example = "2")
        @NotNull(message = "El ID del equipo local no puede ser nulo")
        @Positive(message = "El ID del equipo local debe ser número positivo")
        Integer idEquipoLigaLocal,

        @Schema(description = "Identificador del equipo visitante", example = "9")
        @NotNull(message = "El ID del equipo visitante no puede ser nulo")
        @Positive(message = "El ID del equipo visitante debe ser número positivo")
        Integer idEquipoLigaVisitante,

        @Schema(description = "Goles del equipo local", example = "2")
        @NotNull(message = "Los goles del equipo local no pueden ser nulos")
        @PositiveOrZero(message = "Los goles del equipo local deben ser cero o positivos")
        Integer golesLocal,

        @Schema(description = "Goles del equipo visitante", example = "1")
        @NotNull(message = "Los goles del equipo visitante no pueden ser nulos")
        @PositiveOrZero(message = "Los goles del equipo visitante deben ser cero o positivos")
        Integer golesVisitante
) {


}
