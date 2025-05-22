package com.pita.waterpolo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "Jugador", description = "Jugador")
public record JugadorResponse(
        @Schema(description = "Identificador del jugador", example = "0")
        Integer id,

        @Schema(description = "Nombre del jugador", example = "Manuel")
        String nombre,

        @Schema(description = "Apellidos del jugador", example = "García García")
        String apellidos,

        @Schema(description = "Fecha de nacimiento del jugador", example = "01/01/2000")
        @JsonFormat(pattern = "dd/MM/yyyy") LocalDate fechaNacimiento
) {
}
