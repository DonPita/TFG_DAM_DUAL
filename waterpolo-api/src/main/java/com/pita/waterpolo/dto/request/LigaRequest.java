package com.pita.waterpolo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Liga Request", description = "Modelo de datos para crear o actualizar una liga")
public record LigaRequest(
        @Schema(description = "Nombre de la liga", example = "Liga Absoluta Masculina")
        @NotNull(message = "El nombre de la liga no puede ser nulo")
        @NotBlank(message = "El nombre de la liga no puede estar vacío")
        String nombre,

        @Schema(description = "True si la liga esta activa, false si no", example = "true")
        Boolean activo
) {
}
