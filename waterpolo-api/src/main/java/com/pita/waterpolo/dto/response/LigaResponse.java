package com.pita.waterpolo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Liga", description = "Liga de waterpolo")
public record LigaResponse(
        @Schema(description = "Identificador de la liga", example = "0")
        Integer id,

        @Schema(name = "nombre", description = "Nombre de la liga", example = "Liga Absoluta Masculina")
        String nombre,

        @Schema(name = "activo", description = "Indica si la liga está activa", example = "true")
        Boolean activo
) {
}
