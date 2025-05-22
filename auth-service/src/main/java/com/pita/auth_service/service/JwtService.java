package com.pita.auth_service.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pita.auth_service.dto.response.TokenInfo;
import com.pita.auth_service.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private static final long REFRESH_TOKEN_EXPIRATION = 604800000L; // 7 días en milisegundos

    public String generateAccessToken(Usuario usuario) {
        log.debug("Generando access token para usuario: {}", usuario.getNombreUsuario());
        String token = JWT.create()
                .withSubject(usuario.getNombreUsuario())
                .withClaim("rol", usuario.getRol().name())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC256(secret));
        log.info("Access token generado para usuario_id: {}", usuario.getId());
        return token;
    }

    public String generateRefreshToken() {
        String refreshToken = UUID.randomUUID().toString();
        log.debug("Generado refresh token: {}", refreshToken);
        return refreshToken;
    }

    public TokenInfo validateAccessToken(String token) {
        log.debug("Validando access token: {}", token);
        try{
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token);
            String nombreUsuario = decodedJWT.getSubject();
            String rol = decodedJWT.getClaim("rol").asString();
            log.info("Token validado exitosamente para usuario: {}", nombreUsuario);
            return new TokenInfo(nombreUsuario, rol);
        } catch (JWTVerificationException ex) {
            log.warn("Validación de token fallida: {}", ex.getMessage());
            throw new IllegalArgumentException("Token inválido o expirado");
        }
    }

    public long getRefreshTokenExpiration() {
        return REFRESH_TOKEN_EXPIRATION;
    }
}
