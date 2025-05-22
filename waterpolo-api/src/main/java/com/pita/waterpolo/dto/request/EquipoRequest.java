package com.pita.waterpolo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

@Schema(name = "Equipo Request", description = "Modelo de datos para crear o actualizar un equipo de waterpolo")
public record EquipoRequest(
        @Schema(description = "Nombre del equipo", example = "Club Waterpolo Lugo")
        @NotNull(message = "El nombre del equipo no puede ser nulo")
        @NotBlank(message = "El nombre del equipo no puede estar vacío")
        String nombre,

        @Schema(description = "Ciudad del equipo", example = "Lugo")
        @NotNull(message = "La ciudad del equipo no puede ser nula")
        @NotBlank(message = "La ciudad del equipo no puede estar vacía")
        String ciudad,

        @Schema(description = "Fecha de fundación del equipo", example = "01/09/2018")
        @NotNull(message = "La fecha de fundación del equipo no puede ser nula")
        @PastOrPresent(message = "La fecha de fundación del equipo no puede ser futura")
        @JsonFormat(pattern = "dd/MM/yyyy") LocalDate fechaFundacion
) {


}
