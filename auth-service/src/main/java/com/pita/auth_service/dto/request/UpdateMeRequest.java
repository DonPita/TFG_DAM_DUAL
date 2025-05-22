package com.pita.auth_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateMeRequest (

        @NotBlank(message = "El campo email no puede estar vacío")
        @Email(message = "El email debe ser valido")
        @Size(max = 100, message = "El email no puede tener más de 100 caracteres")
        String email,

        @Size(min = 6, max = 255, message = "La contraseña debe tener entre 6 y 255 caracteres")
        String contrasena,

        @Size(max = 100, message = "El nombre completo no puede tener más de 100 caracteres")
        String nombreCompleto
) {
}
