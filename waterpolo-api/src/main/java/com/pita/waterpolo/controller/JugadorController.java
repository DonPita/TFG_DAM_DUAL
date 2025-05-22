package com.pita.waterpolo.controller;

import com.pita.waterpolo.dto.request.JugadorRequest;
import com.pita.waterpolo.dto.response.JugadorResponse;
import com.pita.waterpolo.entity.Jugador;
import com.pita.waterpolo.service.JugadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jugadores")
@Tag(name = "Jugadores", description = "Endpoints para gestionar jugadores de waterpolo")
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los jugadores",
            description = "Devuelve una lista de todos los jugadores registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de jugadores encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay jugadores disponibles")
    })
    public ResponseEntity<?> getAll() {

        List<JugadorResponse> jugadores = jugadorService.findAll();

        return jugadores.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(jugadores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener jugador por ID",
            description = "Devuelve un jugador específico según su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Jugador encontrado"),
            @ApiResponse(responseCode = "404", description = "Jugador no encontrado")
    })
    public ResponseEntity<?> getById(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Integer id) {

        Optional<JugadorResponse> jugadorOptional = jugadorService.findById(id);

        return jugadorOptional.isPresent() ? ResponseEntity.ok(jugadorOptional.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo jugador",
            description = "Registra un nuevo jugador en el sistema con los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Jugador creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<?> save(
            @Parameter(description = "Datos del jugador a crear", required = true)
            @Valid @RequestBody JugadorRequest jugadorRequest) {

        JugadorResponse savedJugador = jugadorService.save(jugadorRequest);

        return ResponseEntity.created(URI.create("/api-v0/jugadores/" + savedJugador.id())).body(savedJugador);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un jugador existente",
            description = "Actualiza los datos de un jugador específico identificado por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Jugador actualizado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Jugador no encontrado")
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID del jugador a actualizar", required = true) @PathVariable Integer id,
            @Parameter(description = "Nuevos datos del jugador", required = true)
            @Valid @RequestBody JugadorRequest jugadorRequest) {

        Optional<Jugador> jugadorOptional = jugadorService.findEntityById(id);

        return jugadorOptional.isEmpty() ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(jugadorService.update(jugadorRequest, jugadorOptional));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un jugador",
            description = "Elimina un jugador específico según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Jugador eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Jugador no encontrado")
    })
    public ResponseEntity<?> delete(
            @Parameter(description = "ID del jugador a eliminar", required = true) @PathVariable Integer id) {

        Optional<Jugador> jugadorOptional = jugadorService.findEntityById(id);

        if (jugadorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        jugadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}