package com.pita.waterpolo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Equipo en Liga", description = "Representa la relación entre un equipo y una liga")
public record EquipoLigaResponse(
        @Schema(description = "Identificador de la relación", example = "0")
        Integer id,

        @Schema(description = "Identificador del equipo", example = "0")
        Integer idEquipo,

        @Schema(description = "Nombre del equipo", example = "Club Waterpolo Lugo")
        String nombreEquipo,

        @Schema(description = "Identificador de la liga", example = "0")
        Integer idLiga,

        @Schema(description = "Nombre de la liga", example = "Liga Absoluta Femenina")
        String nombreLiga,

        @Schema(description = "Nombre del equipo", example = "Club Waterpolo Lugo Absoluto Femenino")
        String nombre,

        @Schema(description = "Si el Equipo esta participando en la Liga", example = "True")
        Boolean activo
) {
}
