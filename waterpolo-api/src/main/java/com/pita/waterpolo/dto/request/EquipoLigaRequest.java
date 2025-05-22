package com.pita.waterpolo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(name = "Equipo-Liga Request", description = "Representa la participación de un equipo en una liga")
public record EquipoLigaRequest(
        @Schema(description = "Identificador del equipo", example = "0")
        @NotNull(message = "El ID del equipo no puede ser nulo")
        @Positive(message = "El ID del equipo debe ser número positivo")
        Integer idEquipo,

        @Schema(description = "Identificador de la liga", example = "0")
        @NotNull(message = "El ID de la liga no puede ser nulo")
        @Positive(message = "El ID de la liga debe ser número positivo")
        Integer idLiga,

        @Schema(description = "Nombre del equipo", example = "Club Waterpolo Lugo Absoluto Femenino")
        @NotNull(message = "El nombre del equipo no puede ser nulo")
        @NotBlank(message = "El nombre del equipo no puede estar vacío")
        String nombre,

        @Schema(description = "Si el Equipo esta participando en la Liga", example = "True")
        Boolean activo
) {
}
