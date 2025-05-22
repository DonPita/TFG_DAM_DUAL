package com.pita.auth_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email debe ser válido")
        @Size(max = 100, message = "El email no puede tener más de 100 caracteres")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, max = 255, message = "La contraseña debe tener entre 6 y 255 caracteres")
        String contrasena
) {
}
