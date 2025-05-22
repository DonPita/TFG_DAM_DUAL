package com.pita.auth_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
        String nombreUsuario,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email debe ser válido")
        @Size(max = 100, message = "El email no puede tener más de 100 caracteres")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, max = 255, message = "La contraseña debe tener entre 6 y 255 caracteres")
        String contrasena,

        @Size(max = 100, message = "El nombre completo no puede tener más de 100 caracteres")
        String nombreCompleto

) {
}
