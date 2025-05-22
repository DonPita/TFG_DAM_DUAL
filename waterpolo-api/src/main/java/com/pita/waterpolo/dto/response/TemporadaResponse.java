package com.pita.waterpolo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "Temporada Response", description = "Temporada de competición")
public record TemporadaResponse(
        @Schema(description = "Identificador de la temporada", example = "0")
        Integer id,

        @Schema(description = "Nombre de la temporada", example = "2023-2024")
        String nombre,

        @Schema(description = "Fecha de inicio de la temporada", example = "01/09/2023")
        @JsonFormat(pattern = "dd/MM/yyyy") LocalDate fechaInicio,

        @Schema(description = "Fecha de fin de la temporada", example = "31/08/2024")
        @JsonFormat(pattern = "dd/MM/yyyy") LocalDate fechaFin) {
}
