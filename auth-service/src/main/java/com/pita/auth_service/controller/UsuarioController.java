package com.pita.auth_service.controller;

import com.pita.auth_service.dto.request.UpdateUsuarioRequest;
import com.pita.auth_service.dto.response.ErrorResponse;
import com.pita.auth_service.entity.Usuario;
import com.pita.auth_service.service.AuthService;
import com.pita.auth_service.service.UsuarioService;
import com.pita.auth_service.utils.Rol;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Controlador para la gestión de usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    public UsuarioController(UsuarioService usuarioService, PasswordEncoder passwordEncoder, AuthService authService) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay usuarios disponibles"),
    })
    public ResponseEntity<?> getAll() {
        List<Usuario> usuarios = usuarioService.findAll();

        return usuarios.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario por ID", description = "Devuelve un usuario específico por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
    })
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        if (usuarioService.findById(id).isPresent()) {
            return ResponseEntity.ok(usuarioService.findById(id).get());
        } else {
            return ResponseEntity.status(404)
                    .body(new ErrorResponse("USER_NOT_FOUND", "Usuario no encontrado", null));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un usuario", description = "Actualiza la información de un usuario existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UpdateUsuarioRequest request) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        if (usuario.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(new ErrorResponse("USER_NOT_FOUND", "Usuario no encontrado", null));
        }

        Usuario usuarioToUpdate = usuario.get();

        if (!usuarioToUpdate.getNombreUsuario().equals(request.nombreUsuario()) &&
                usuarioService.existsByNombreUsuario(request.nombreUsuario())) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("USER_EXISTS", "El nombre de usuario ya está en uso", null));
        }

        if (!usuarioToUpdate.getEmail().equals(request.email()) &&
                usuarioService.existsByEmail(request.email())) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("EMAIL_EXISTS", "El email ya está en uso", null));
        }

        usuarioToUpdate.setNombreUsuario(request.nombreUsuario());
        usuarioToUpdate.setEmail(request.email());

        if (request.contrasena() != null && !request.contrasena().isEmpty()) {
            if(!authService.isValidPassword(request.contrasena())){
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("INVALID_PASSWORD", "La contraseña debe tener al menos 6 caracteres, incluyendo una mayúscula y un numero",
                                null));
            }
            usuarioToUpdate.setContrasena(passwordEncoder.encode(request.contrasena()));
        }

        try {
            Rol rol = Rol.valueOf(request.rol().toUpperCase());

            if (usuarioToUpdate.getRol() == Rol.ADMIN && rol != Rol.ADMIN) {
                long adminCount = usuarioService.findAll().stream()
                        .filter(u -> u.getRol() == Rol.ADMIN).count();
                if (adminCount <= 1) {
                    return ResponseEntity.badRequest()
                            .body(new ErrorResponse("LAST_ADMIN", "No se puede eliminar el último administrador", null));
                }
            }

            usuarioToUpdate.setRol(rol);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("INVALID_ROLE", "Rol inválido", "Debe ser ADMIN, ARBITRO, JUGADOR O USUARIO"));
        }

        usuarioToUpdate.setNombreCompleto(request.nombreCompleto());
        usuarioService.save(usuarioToUpdate);
        return ResponseEntity.ok(usuarioToUpdate);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario del sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
    })
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (usuarioService.findById(id).isPresent()) {

            if (usuarioService.findById(id).get().getRol() == Rol.ADMIN) {
                long adminCount = usuarioService.findAll().stream()
                        .filter(u -> u.getRol() == Rol.ADMIN).count();
                if (adminCount <= 1) {
                    return ResponseEntity.badRequest()
                            .body(new ErrorResponse("LAST_ADMIN", "No se puede eliminar el último administrador", null));
                }
            }

            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(404)
                    .body(new ErrorResponse("USER_NOT_FOUND", "Usuario no encontrado", null));
        }
    }
}
