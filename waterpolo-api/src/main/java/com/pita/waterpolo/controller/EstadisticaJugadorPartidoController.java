package com.pita.waterpolo.controller;

import com.pita.waterpolo.dto.request.EstadisticaJugadorPartidoRequest;
import com.pita.waterpolo.dto.response.EstadisticaJugadorPartidoResponse;
import com.pita.waterpolo.entity.EstadisticaJugadorPartido;
import com.pita.waterpolo.entity.Jugador;
import com.pita.waterpolo.entity.Partido;
import com.pita.waterpolo.exception.ResourceNotFoundException;
import com.pita.waterpolo.service.EstadisticaJugadorPartidoService;
import com.pita.waterpolo.service.JugadorService;
import com.pita.waterpolo.service.PartidoService;
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
@RequestMapping("/estadisticas-jugador-partido")
@Tag(name = "Estadísticas Jugador Partido", description = "Endpoints para gestionar estadísticas de jugadores en los partidos que participan")
public class EstadisticaJugadorPartidoController {

    private final EstadisticaJugadorPartidoService estadisticaJugadorPartidoService;
    private final JugadorService jugadorService;
    private final PartidoService partidoService;

    public EstadisticaJugadorPartidoController(EstadisticaJugadorPartidoService estadisticaJugadorPartidoService,
                                               JugadorService jugadorService, PartidoService partidoService) {
        this.estadisticaJugadorPartidoService = estadisticaJugadorPartidoService;
        this.jugadorService = jugadorService;
        this.partidoService = partidoService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las estadísticas",
            description = "Devuelve una lista de todas las estadísticas de jugadores en partidos registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estadísticas encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay estadísticas disponibles")
    })
    public ResponseEntity<?> findAll() {
        List<EstadisticaJugadorPartidoResponse> estadisticas = estadisticaJugadorPartidoService.findAll();

        return estadisticas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener estadística por ID",
            description = "Devuelve una estadística específica de un jugador en un partido según su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadística encontrada"),
            @ApiResponse(responseCode = "404", description = "Estadística no encontrada")
    })
    public ResponseEntity<?> findById(
            @Parameter(description = "ID de la estadística", required = true) @PathVariable Integer id) {
        Optional<EstadisticaJugadorPartidoResponse> estadisticaOptional = estadisticaJugadorPartidoService.findById(id);

        return estadisticaOptional.isPresent() ? ResponseEntity.ok(estadisticaOptional.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/jugador/{idJugador}")
    @Operation(summary = "Obtener estadísticas por jugador",
            description = "Devuelve todas las estadísticas de un jugador específico en distintos partidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estadísticas encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay estadísticas para ese jugador"),
            @ApiResponse(responseCode = "404", description = "Jugador no encontrado")
    })
    public ResponseEntity<?> findByJugador(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Integer idJugador) {

        Jugador jugador = jugadorService.findEntityById(idJugador).orElseThrow(
                () -> new ResourceNotFoundException("Jugador con ID: " + idJugador + " no encontrado"));

        List<EstadisticaJugadorPartidoResponse> estadisticas = estadisticaJugadorPartidoService.findByJugador(jugador);

        return estadisticas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/partido/{idPartido}")
    @Operation(summary = "Obtener estadísticas por partido",
            description = "Devuelve todas las estadísticas de jugadores en un partido específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estadísticas encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay estadísticas para ese partido"),
            @ApiResponse(responseCode = "404", description = "Partido no encontrado")
    })
    public ResponseEntity<?> findByPartido(
            @Parameter(description = "ID del partido", required = true) @PathVariable Integer idPartido) {

        Partido partido = partidoService.findEntityById(idPartido).orElseThrow(
                () -> new ResourceNotFoundException("Partido con ID: " + idPartido + " no encontrado"));

        List<EstadisticaJugadorPartidoResponse> estadisticas = estadisticaJugadorPartidoService.findByPartido(partido);

        return estadisticas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/jugador/{idJugador}/partido/{idPartido}")
    @Operation(summary = "Obtener estadística por jugador y partido",
            description = "Devuelve la estadística específica de un jugador en un partido dado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadística encontrada"),
            @ApiResponse(responseCode = "404", description = "Estadística, jugador o partido no encontrado")
    })
    public ResponseEntity<?> findByJugadorAndPartido(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Integer idJugador,
            @Parameter(description = "ID del partido", required = true) @PathVariable Integer idPartido) {

        Jugador jugador = jugadorService.findEntityById(idJugador).orElseThrow(
                () -> new ResourceNotFoundException("Jugador con ID: " + idJugador + " no encontrado"));
        Partido partido = partidoService.findEntityById(idPartido).orElseThrow(
                () -> new ResourceNotFoundException("Partido con ID: " + idPartido + " no encontrado"));

        Optional<EstadisticaJugadorPartidoResponse> estadistica = estadisticaJugadorPartidoService.findByJugadorAndPartido(jugador, partido);

        return estadistica.isPresent() ? ResponseEntity.ok(estadistica.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear una nueva estadística",
            description = "Registra una nueva estadística para un jugador en un partido con los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estadística creada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Jugador o partido no encontrado")
    })
    public ResponseEntity<?> save(
            @Parameter(description = "Datos de la estadística a crear", required = true)
            @Valid @RequestBody EstadisticaJugadorPartidoRequest estadisticaJugadorPartidoRequest) {

        Jugador jugador = jugadorService.findEntityById(estadisticaJugadorPartidoRequest.idJugador()).orElseThrow(
                () -> new ResourceNotFoundException("Jugador con ID: " + estadisticaJugadorPartidoRequest.idJugador() + " no encontrado"));
        Partido partido = partidoService.findEntityById(estadisticaJugadorPartidoRequest.idPartido()).orElseThrow(
                () -> new ResourceNotFoundException("Partido con ID: " + estadisticaJugadorPartidoRequest.idPartido() + " no encontrado"));

        EstadisticaJugadorPartidoResponse savedEstadistica = estadisticaJugadorPartidoService.save(estadisticaJugadorPartidoRequest, jugador, partido);

        return ResponseEntity.created(URI.create("/api-v0/estadisticas-jugador-partido/" + savedEstadistica.id())).body(savedEstadistica);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una estadística existente",
            description = "Actualiza los datos de una estadística específica de un jugador en un partido según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadística actualizada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Estadística, jugador o partido no encontrado")
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID de la estadística a actualizar", required = true) @PathVariable Integer id,
            @Parameter(description = "Nuevos datos de la estadística", required = true)
            @Valid @RequestBody EstadisticaJugadorPartidoRequest estadisticaJugadorPartidoRequest) {

        Optional<EstadisticaJugadorPartido> estadisticaOptional = estadisticaJugadorPartidoService.findEntityById(id);

        if (estadisticaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Jugador jugador = jugadorService.findEntityById(estadisticaJugadorPartidoRequest.idJugador()).orElseThrow(
                () -> new ResourceNotFoundException("Jugador con ID: " + estadisticaJugadorPartidoRequest.idJugador() + " no encontrado"));
        Partido partido = partidoService.findEntityById(estadisticaJugadorPartidoRequest.idPartido()).orElseThrow(
                () -> new ResourceNotFoundException("Partido con ID: " + estadisticaJugadorPartidoRequest.idPartido() + " no encontrado"));

        EstadisticaJugadorPartidoResponse updatedEstadistica = estadisticaJugadorPartidoService.update(estadisticaJugadorPartidoRequest,
                jugador, partido, estadisticaOptional);

        return ResponseEntity.ok(updatedEstadistica);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una estadística",
            description = "Elimina una estadística específica de un jugador en un partido según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estadística eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Estadística no encontrada")
    })
    public ResponseEntity<?> delete(
            @Parameter(description = "ID de la estadística a eliminar", required = true) @PathVariable Integer id) {

        Optional<EstadisticaJugadorPartido> estadisticaOptional = estadisticaJugadorPartidoService.findEntityById(id);

        if (estadisticaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        estadisticaJugadorPartidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}