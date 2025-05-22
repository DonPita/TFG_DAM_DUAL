package com.pita.auth_service.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String path = request.getRequestURI();
        log.debug("Procesando peticion para: {}", path);

        if (header == null || !header.startsWith("Bearer ")) {
            log.debug("No se encontró token para: {}", path);
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(jwtSecret))
                    .build()
                    .verify(token);

            String username = decodedJWT.getSubject();
            String rol = decodedJWT.getClaim("rol").asString();

            if (username == null || rol == null) {
                log.debug("Token inválido o expirado para: {}", path);
                SecurityContextHolder.clearContext();
                filterChain.doFilter(request, response);
                return;
            }

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + rol);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                    null, Collections.singletonList(authority));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.debug("Autenticado: {} con rol: {} para : {}", username, rol, path);
        } catch (JWTVerificationException e) {
            log.debug("Validacion de token fallida para {}: {}", path, e.getMessage());
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}