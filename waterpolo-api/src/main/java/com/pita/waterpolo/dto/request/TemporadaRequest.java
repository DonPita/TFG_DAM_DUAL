package com.pita.waterpolo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(name = "Temporada Request", description = "Datos para crear o actualizar una temporada de competición")
public record TemporadaRequest(
        @Schema(description = "Nombre de la temporada", example = "2025-2026")
        @NotNull(message = "El nombre de la temporada no puede ser nulo")
        @NotBlank(message = "El nombre de la temporada no puede estar vacío")
        String nombre,

        @Schema(description = "Fecha de inicio de la temporada", example = "01/09/2025")
        @NotNull(message = "La fecha de inicio de la temporada no puede ser nula")
        @JsonFormat(pattern = "dd/MM/yyyy") LocalDate fechaInicio,

        @Schema(description = "Fecha de fin de la temporada", example = "31/08/2026")
        @NotNull(message = "La fecha de fin de la temporada no puede ser nula")
        @JsonFormat(pattern = "dd/MM/yyyy") LocalDate fechaFin
) {
}
