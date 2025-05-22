package com.pita.auth_service.service;

import com.pita.auth_service.dto.response.AuthResponse;
import com.pita.auth_service.entity.RefreshToken;
import com.pita.auth_service.entity.Usuario;
import com.pita.auth_service.repository.RefreshTokensRepository;
import com.pita.auth_service.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UsuarioRepository usuarioRepository;
    private final RefreshTokensRepository refreshTokensRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
                       RefreshTokensRepository refreshTokensRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokensRepository = refreshTokensRepository;
    }

    public AuthResponse register(Usuario usuario) {
        log.info("Registrando usuario: nombreUsuario={}", usuario.getNombreUsuario());
        if (!isValidPassword(usuario.getContrasena())) {
            log.warn("Contraseña inválida para usuario: nombreUsuario={}", usuario.getNombreUsuario());
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres, incluyendo una mayuscula y un número");
        }

        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usuario.setActivo(true);
        usuario.setFechaCreacion(LocalDateTime.now());

        usuarioRepository.save(usuario);

        String accessToken = jwtService.generateAccessToken(usuario);
        String refreshToken = generateAndSaveRefreshToken(usuario);
        log.info("Usuario registrado exitosamente: usuario_id={}, nombreUsuario={}", usuario.getId(), usuario.getNombreUsuario());
        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse login(Usuario usuario, String contrasena) {
        log.info("Iniciando sesión para usuario: nombreUsuario={}", usuario.getNombreUsuario());
        if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
            log.warn("Login fallido para usuario_id={}: Contraseña incorrecta", usuario.getId());
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        usuario.setUltimoAcceso(LocalDateTime.now());
        usuarioRepository.save(usuario);

        String accessToken = jwtService.generateAccessToken(usuario);
        String refreshToken = getOrGenerateRefreshToken(usuario);
        log.info("Login exitoso para usuario_id={}: nombreUsuario={}", usuario.getId(), usuario.getNombreUsuario());
        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse refreshToken(String refreshToken) {
        log.info("Renovando token con refreshToken={}", refreshToken);
        RefreshToken token = refreshTokensRepository.findByToken(refreshToken)
                .orElseThrow(() -> {
                    log.warn("RefreshToken no encontrado: {}", refreshToken);
                    return new IllegalArgumentException("RefreshToken no encontrado");
                });

        if (!token.isValido() || token.getExpiracion().isBefore(LocalDateTime.now())) {
            log.warn("RefreshToken inválido o expirado para usuario_id={}: {}", token.getUsuario().getId(), refreshToken);
            throw new IllegalArgumentException("RefreshToken inválido o expirado");
        }

        String accessToken = jwtService.generateAccessToken(token.getUsuario());
        log.info("Token renovado exitosamente para usuario_id={}: nuevoAccessToken={}", token.getUsuario().getId(), accessToken);
        return new AuthResponse(accessToken, refreshToken);
    }

    public void logout(String refreshToken) {
        log.info("Cerrando sesión para refreshToken={}", refreshToken);
        RefreshToken token = refreshTokensRepository.findByToken(refreshToken)
                .orElseThrow(() -> {
                    log.warn("RefreshToken no encontrado para logout: {}", refreshToken);
                    return new IllegalArgumentException("RefreshToken no encontrado");
                });

        refreshTokensRepository.delete(token);
        log.info("Sesión cerrada exitosamente para usuario_id={}: refreshToken eliminado", token.getUsuario().getId());
        // En caso de querer invalidarlo, pero mantenerlo en BBDD
        // token.setValido(false);
        // refreshTokensRepository.save(token);
    }

    private String getOrGenerateRefreshToken(Usuario usuario) {

        List<RefreshToken> activeTokens = refreshTokensRepository.findAllByUsuarioAndValidoTrueAndExpiracionAfter(usuario,
                LocalDateTime.now());

        if (!activeTokens.isEmpty()) {
            RefreshToken latestToken = activeTokens.stream()
                    .max(Comparator.comparing(RefreshToken::getExpiracion))
                    .get();

            List<RefreshToken> allTokens = refreshTokensRepository.findByUsuario(usuario);
            allTokens.remove(latestToken);
            refreshTokensRepository.deleteAll(allTokens);
            log.debug("Eliminados {} refreshtoken antiguos para usuario_id={}", allTokens.size(), usuario.getId());
            log.debug("Usando refreshtoken existente: {} para usuario_id={}", latestToken.getToken(), usuario.getId());

            return latestToken.getToken();
        }

        return generateAndSaveRefreshToken(usuario);
    }

    private String generateAndSaveRefreshToken(Usuario usuario) {
        String refreshToken = jwtService.generateRefreshToken();
        LocalDateTime expiracion = LocalDateTime.now().plusSeconds(jwtService.getRefreshTokenExpiration() / 1000);

        RefreshToken token = new RefreshToken(refreshToken, usuario, expiracion);
        refreshTokensRepository.save(token);
        log.debug("Generado refreshToken={} para usuario_id={}", refreshToken, usuario.getId());

        return refreshToken;
    }

    public boolean isValidPassword(String password) {
        return password != null &&
                password.length() >= 6 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*");
    }
}
