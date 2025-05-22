package com.pita.waterpolo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "Equipo", description = "Representa a un equipo de waterpolo")
public record EquipoResponse(
        @Schema(description = "Identificador del equipo", example = "0")
        Integer id,

        @Schema(description = "Nombre del equipo", example = "Club Waterpolo Lugo")
        String nombre,

        @Schema(description = "Ciudad de origen del equipo", example = "Lugo")
        String ciudad,

        @Schema(description = "Fecha de fundación del equipo", example = "01/09/2018")
        @JsonFormat(pattern = "dd/MM/yyyy") LocalDate fechaFundacion
) {
}
