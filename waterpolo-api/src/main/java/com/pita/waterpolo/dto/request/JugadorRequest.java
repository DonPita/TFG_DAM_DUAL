package com.pita.waterpolo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

@Schema(name = "Jugador Request", description = "Modelo de datos para crear o actualizar un jugador de waterpolo")
public record JugadorRequest(
        @Schema(description = "Nombre del jugador", example = "Manuel")
        @NotNull(message = "El nombre del jugador no puede ser nulo")
        @NotBlank(message = "El nombre del jugador no puede estar vacío")
        String nombre,

        @Schema(description = "Apellidos del jugador", example = "García García")
        @NotNull(message = "Los apellidos del jugador no pueden ser nulos")
        @NotBlank(message = "Los apellidos del jugador no pueden estar vacíos")
        String apellidos,

        @Schema(description = "Fecha de nacimiento del jugador", example = "01/01/2000")
        @NotNull(message = "La fecha de nacimiento del jugador no puede ser nula")
        @PastOrPresent(message = "La fecha de nacimiento del jugador no puede ser futura")
        @JsonFormat(pattern = "dd/MM/yyyy") LocalDate fechaNacimiento

) {
}
