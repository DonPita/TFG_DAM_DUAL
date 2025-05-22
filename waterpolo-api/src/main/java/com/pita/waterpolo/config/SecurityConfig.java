package com.pita.waterpolo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sesion ->
                        sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                                //Rutas públicas
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/v3/api-docs/**"
                                ).permitAll()
                                //Equipos
                                .requestMatchers(HttpMethod.GET, "/equipos", "/equipos/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/equipos").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/equipos/**").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/equipos/**").hasAnyAuthority("ROLE_ADMIN")
                                // Jugadores
                                .requestMatchers(HttpMethod.GET, "/jugadores", "/jugadores/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/jugadores").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/jugadores/**").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/jugadores/**").hasAnyAuthority("ROLE_ADMIN")
                                // Clasificación Liga Temporada
                                .requestMatchers(HttpMethod.GET, "/clasificacion-liga-temporada", "/clasificacion-liga-temporada/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/clasificacion-liga-temporada").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/clasificacion-liga-temporada/**").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/clasificacion-liga-temporada/**").hasAnyAuthority("ROLE_ADMIN")
                                // Equipos Liga
                                .requestMatchers(HttpMethod.GET, "/equipos-liga", "/equipos-liga/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/equipos-liga").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/equipos-liga/**").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/equipos-liga/**").hasAnyAuthority("ROLE_ADMIN")
                                // Estadísticas Jugador Partido
                                .requestMatchers(HttpMethod.GET, "/estadisticas-jugador-partido", "/estadisticas-jugador-partido/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/estadisticas-jugador-partido").hasAnyAuthority("ROLE_ADMIN", "ROLE_ARBITRO")
                                .requestMatchers(HttpMethod.PUT, "/estadisticas-jugador-partido/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_ARBITRO")
                                .requestMatchers(HttpMethod.DELETE, "/estadisticas-jugador-partido/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_ARBITRO")
                                // Estadísticas Jugador Total
                                .requestMatchers(HttpMethod.GET, "/estadistica-jugador-total", "/estadistica-jugador-total/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/estadistica-jugador-total").hasAnyAuthority("ROLE_ADMIN", "ROLE_ARBITRO")
                                .requestMatchers(HttpMethod.PUT, "/estadistica-jugador-total/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_ARBITRO")
                                .requestMatchers(HttpMethod.DELETE, "/estadistica-jugador-total/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_ARBITRO")
                                // Jugadores Equipos Ligas
                                .requestMatchers(HttpMethod.GET, "/jugadores-equipos-ligas", "/jugadores-equipos-ligas/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/jugadores-equipos-ligas").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/jugadores-equipos-ligas/**").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/jugadores-equipos-ligas/**").hasAnyAuthority("ROLE_ADMIN")
                                // Ligas
                                .requestMatchers(HttpMethod.GET, "/ligas", "/ligas/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/ligas").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/ligas/**").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/ligas/**").hasAnyAuthority("ROLE_ADMIN")
                                // Partidos
                                .requestMatchers(HttpMethod.GET, "/partidos", "/partidos/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/partidos").hasAnyAuthority("ROLE_ADMIN", "ROLE_ARBITRO")
                                .requestMatchers(HttpMethod.PUT, "/partidos/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_ARBITRO")
                                .requestMatchers(HttpMethod.DELETE, "/partidos/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_ARBITRO")
                                // Temporadas
                                .requestMatchers(HttpMethod.GET, "/temporadas", "/temporadas/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/temporadas").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/temporadas/**").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/temporadas/**").hasAnyAuthority("ROLE_ADMIN")
                                // Cualquier otra petición
                                .anyRequest().denyAll()
                        //Cualquier otra petición requiere autenticación
//                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
