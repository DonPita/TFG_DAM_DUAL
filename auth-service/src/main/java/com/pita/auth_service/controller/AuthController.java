package com.pita.auth_service.controller;

import com.pita.auth_service.dto.request.*;
import com.pita.auth_service.dto.response.AuthResponse;
import com.pita.auth_service.dto.response.ErrorResponse;
import com.pita.auth_service.dto.response.TokenInfo;
import com.pita.auth_service.entity.Usuario;
import com.pita.auth_service.service.AuthService;
import com.pita.auth_service.service.JwtService;
import com.pita.auth_service.service.UsuarioService;
import com.pita.auth_service.utils.Rol;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Controlador para registro y login de usuarios")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService, UsuarioService usuarioService, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar un nuevo usuario", description = "Registra un nuevo usuario en el sistema y devuelve un token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o usuario/email ya existen."),
    })
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        log.info("Peticion POST /auth/register recibida para nombreUsuario={}", registerRequest.nombreUsuario());

        if (usuarioService.existsByNombreUsuario(registerRequest.nombreUsuario())) {
            log.warn("El nombre de usuario ya está en uso: {}", registerRequest.nombreUsuario());
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("USER_EXISTS", "El nombre de usuario ya está en uso", null));
        }
        if (usuarioService.existsByEmail(registerRequest.email())) {
            log.warn("El email ya está en uso: {}", registerRequest.email());
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("EMAIL_EXISTS", "El email ya está en uso", null));
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(registerRequest.nombreUsuario());
        usuario.setEmail(registerRequest.email());
        usuario.setContrasena(registerRequest.contrasena());
        usuario.setNombreCompleto(registerRequest.nombreCompleto());
        usuario.setRol(Rol.USUARIO);

        AuthResponse authResponse = authService.register(usuario);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Inicia sesión con el nombre de usuario y contraseña y devuelve un token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
    })
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Peticion POST /auth/login recibida para email={}", loginRequest.email());
        Usuario usuario = usuarioService.findByEmail(loginRequest.email())
                .orElse(null);
        if (usuario == null) {
            log.warn("Intento de login fallido: usuario no encontrado: {}", loginRequest.email());
            return ResponseEntity.status(401)
                    .body(new ErrorResponse("USER_NOT_FOUND", "Usuario no encontrado", null));
        }

        try {
            AuthResponse authResponse = authService.login(usuario, loginRequest.contrasena());
            log.info("Intento de login exitoso para email={}", loginRequest.email());
            return ResponseEntity.ok(authResponse);
        } catch (IllegalArgumentException e) {
            log.warn("Intento de login fallido: contraseña incorrecta para email={}", loginRequest.email());
            return ResponseEntity.status(401)
                    .body(new ErrorResponse("INVALID_PASSWORD", "Contraseña incorrecta", null));
        }
    }

    @PostMapping("/validate")
    @Operation(summary = "Validar token JWT", description = "Valida el token JWT y devuelve información del usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token válido"),
            @ApiResponse(responseCode = "401", description = "Token inválido o expirado")
    })
    public ResponseEntity<?> validate(@RequestBody TokenRequest tokenRequest) {
        log.info("Peticion POST /auth/validate recibida para token={}", tokenRequest.token());

        try {
            TokenInfo tokenInfo = jwtService.validateAccessToken(tokenRequest.token());
            log.info("Token válido para nombreUsuario: {}", tokenInfo.nombreUsuario());
            return ResponseEntity.ok(tokenInfo);

        } catch (IllegalArgumentException e) {
            log.warn("Token inválido o expirado: {}", e.getMessage());
            return ResponseEntity.status(401)
                    .body(new ErrorResponse("INVALID_TOKEN", "Token invalido o expirado", e.getMessage()));
        }
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refrescar token JWT", description = "Refresca el token JWT utilizando un refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refrescado exitosamente"),
            @ApiResponse(responseCode = "401", description = "Refresh token inválido o expirado")
    })
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        log.info("Peticion POST /auth/refresh recibida para refreshToken={}", refreshTokenRequest.refreshToken());

        try {
            AuthResponse authResponse = authService.refreshToken(refreshTokenRequest.refreshToken());
            log.info("Token renovado exitosamente para refreshToken={}", refreshTokenRequest.refreshToken());
            return ResponseEntity.ok(authResponse);

        } catch (IllegalArgumentException e) {
            log.warn("Refresh token inválido o expirado: {}", e.getMessage());
            return ResponseEntity.status(401)
                    .body(new ErrorResponse("INVALID_REFRESH_TOKEN", "Refresh token inválido o expirado", e.getMessage()));
        }
    }

    @PutMapping("/me")
    @Operation(summary = "Actualizar datos del usuario autenticado", description = "Permite al usuario autenticado actualizar su email, contraseña o nombre completo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información del usuario actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o email ya en uso"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<?> updateMe(@Valid @RequestBody UpdateMeRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Peticion PUT /auth/me recibida para nombreUsuario={}", userDetails.getUsername());

        Usuario usuario = usuarioService.findByNombreUsuario(userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        if (!usuario.getEmail().equals(request.email()) && usuarioService.existsByEmail(request.email())) {
            log.warn("El email ya está en uso: {}", request.email());
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("EMAIL_EXISTS", "El email ya está en uso", null));
        }

        if (request.contrasena() != null && !request.contrasena().isEmpty()) {
            if (!authService.isValidPassword(request.contrasena())) {
                log.warn("Contraseña invalida para usuario: {}", userDetails.getUsername());
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("INVALID_PASSWORD", "La contraseña debe tener al menos 6 caracteres, incluyendo una mayúscula y un número", null));
            }
            usuario.setContrasena(passwordEncoder.encode(request.contrasena()));
        }

        usuario.setEmail(request.email());
        usuario.setNombreCompleto(request.nombreCompleto());
        usuarioService.save(usuario);

        log.info("Información del usuario actualizada exitosamente usuario_id={}", usuario.getId());
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Cierra la sesión del usuario y elimina el refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sesión cerrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Refresh token inválido o expirado")
    })
    public ResponseEntity<?> logout(@RequestBody LogoutRequest logoutRequest) {
        log.info("Peticion POST /auth/logout recibida para refreshToken={}", logoutRequest.refreshToken());

        try {
            authService.logout(logoutRequest.refreshToken());
            log.info("Sesión cerrada exitosamente para refreshToken={}", logoutRequest.refreshToken());
            return ResponseEntity.ok().build();

        } catch (IllegalArgumentException e) {
            log.warn("Cierre de sesión fallido: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("INVALID_REFRESH_TOKEN", "Refresh token no encontrado", e.getMessage()));
        }
    }
}
