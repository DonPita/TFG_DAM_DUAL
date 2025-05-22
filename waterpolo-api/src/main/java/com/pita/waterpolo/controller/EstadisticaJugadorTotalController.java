package com.pita.waterpolo.controller;

import com.pita.waterpolo.dto.request.EstadisticaJugadorTotalRequest;
import com.pita.waterpolo.dto.response.EstadisticaJugadorTotalResponse;
import com.pita.waterpolo.entity.EstadisticaJugadorTotal;
import com.pita.waterpolo.entity.Jugador;
import com.pita.waterpolo.entity.Temporada;
import com.pita.waterpolo.exception.ResourceNotFoundException;
import com.pita.waterpolo.service.EstadisticaJugadorTotalService;
import com.pita.waterpolo.service.JugadorService;
import com.pita.waterpolo.service.TemporadaService;
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
@RequestMapping("/estadistica-jugador-total")
@Tag(name = "Estadísticas Jugador Total", description = "Endpoints para gestionar estadísticas totales de jugadores por temporada")
public class EstadisticaJugadorTotalController {

    private final EstadisticaJugadorTotalService estadisticaJugadorTotalService;
    private final JugadorService jugadorService;
    private final TemporadaService temporadaService;

    public EstadisticaJugadorTotalController(EstadisticaJugadorTotalService estadisticaJugadorTotalService,
                                             JugadorService jugadorService, TemporadaService temporadaService) {
        this.estadisticaJugadorTotalService = estadisticaJugadorTotalService;
        this.jugadorService = jugadorService;
        this.temporadaService = temporadaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las estadísticas totales",
            description = "Devuelve una lista de todas las estadísticas totales de jugadores por temporada registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estadísticas encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay estadísticas disponibles")
    })
    public ResponseEntity<?> findAll() {
        List<EstadisticaJugadorTotalResponse> estadisticas = estadisticaJugadorTotalService.findAll();

        return estadisticas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener estadística total por ID",
            description = "Devuelve una estadística total específica de un jugador en una temporada según su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadística encontrada"),
            @ApiResponse(responseCode = "404", description = "Estadística no encontrada")
    })
    public ResponseEntity<?> findById(
            @Parameter(description = "ID de la estadística", required = true) @PathVariable Integer id) {
        Optional<EstadisticaJugadorTotalResponse> estadisticaOptional = estadisticaJugadorTotalService.findById(id);

        return estadisticaOptional.isPresent() ? ResponseEntity.ok(estadisticaOptional.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/jugador/{idJugador}")
    @Operation(summary = "Obtener estadísticas totales por jugador",
            description = "Devuelve todas las estadísticas totales de un jugador específico en distintas temporadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estadísticas encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay estadísticas para ese jugador"),
            @ApiResponse(responseCode = "404", description = "Jugador no encontrado")
    })
    public ResponseEntity<?> findByJugador(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Integer idJugador) {

        Jugador jugador = jugadorService.findEntityById(idJugador).orElseThrow(
                () -> new ResourceNotFoundException("Jugador con ID: " + idJugador + " no encontrado"));

        List<EstadisticaJugadorTotalResponse> estadisticas = estadisticaJugadorTotalService.findByJugador(jugador);

        return estadisticas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/temporada/{idTemporada}")
    @Operation(summary = "Obtener estadísticas totales por temporada",
            description = "Devuelve todas las estadísticas totales de jugadores en una temporada específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estadísticas encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay estadísticas para esa temporada"),
            @ApiResponse(responseCode = "404", description = "Temporada no encontrada")
    })
    public ResponseEntity<?> findByTemporada(
            @Parameter(description = "ID de la temporada", required = true) @PathVariable Integer idTemporada) {

        Temporada temporada = temporadaService.findEntityById(idTemporada).orElseThrow(
                () -> new ResourceNotFoundException("Temporada con ID: " + idTemporada + " no encontrada"));

        List<EstadisticaJugadorTotalResponse> estadisticas = estadisticaJugadorTotalService.findByTemporada(temporada);

        return estadisticas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/jugador/{idJugador}/temporada/{idTemporada}")
    @Operation(summary = "Obtener estadística total por jugador y temporada",
            description = "Devuelve la estadística total específica de un jugador en una temporada dada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadística encontrada"),
            @ApiResponse(responseCode = "404", description = "Estadística, jugador o temporada no encontrada")
    })
    public ResponseEntity<?> findByJugadorAndTemporada(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Integer idJugador,
            @Parameter(description = "ID de la temporada", required = true) @PathVariable Integer idTemporada) {

        Jugador jugador = jugadorService.findEntityById(idJugador).orElseThrow(
                () -> new ResourceNotFoundException("Jugador con ID: " + idJugador + " no encontrado"));
        Temporada temporada = temporadaService.findEntityById(idTemporada).orElseThrow(
                () -> new ResourceNotFoundException("Temporada con ID: " + idTemporada + " no encontrada"));

        Optional<EstadisticaJugadorTotalResponse> estadistica = estadisticaJugadorTotalService.findByJugadorAndTemporada(jugador, temporada);

        return estadistica.isPresent() ? ResponseEntity.ok(estadistica.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear una nueva estadística total",
            description = "Registra una nueva estadística total para un jugador en una temporada con los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estadística creada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Jugador o temporada no encontrada")
    })
    public ResponseEntity<?> save(
            @Parameter(description = "Datos de la estadística a crear", required = true)
            @Valid @RequestBody EstadisticaJugadorTotalRequest request) {

        Jugador jugador = jugadorService.findEntityById(request.idJugador()).orElseThrow(
                () -> new ResourceNotFoundException("Jugador con ID: " + request.idJugador() + " no encontrado"));
        Temporada temporada = temporadaService.findEntityById(request.idTemporada()).orElseThrow(
                () -> new ResourceNotFoundException("Temporada con ID: " + request.idTemporada() + " no encontrada"));

        EstadisticaJugadorTotalResponse savedEstadistica = estadisticaJugadorTotalService.save(request, jugador, temporada);

        return ResponseEntity.created(URI.create("/api-v0/estadistica-jugador-total/" + savedEstadistica.id())).body(savedEstadistica);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una estadística total existente",
            description = "Actualiza los datos de una estadística total específica de un jugador en una temporada según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadística actualizada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Estadística, jugador o temporada no encontrada")
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID de la estadística a actualizar", required = true) @PathVariable Integer id,
            @Parameter(description = "Nuevos datos de la estadística", required = true)
            @Valid @RequestBody EstadisticaJugadorTotalRequest request) {

        Optional<EstadisticaJugadorTotal> estadisticaOptional = estadisticaJugadorTotalService.findEntityById(id);

        if (estadisticaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Jugador jugador = jugadorService.findEntityById(request.idJugador()).orElseThrow(
                () -> new ResourceNotFoundException("Jugador con ID: " + request.idJugador() + " no encontrado"));
        Temporada temporada = temporadaService.findEntityById(request.idTemporada()).orElseThrow(
                () -> new ResourceNotFoundException("Temporada con ID: " + request.idTemporada() + " no encontrada"));

        EstadisticaJugadorTotalResponse updatedEstadistica = estadisticaJugadorTotalService.update(request, jugador,
                temporada, estadisticaOptional);

        return ResponseEntity.ok(updatedEstadistica);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una estadística total",
            description = "Elimina una estadística total específica de un jugador en una temporada según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estadística eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Estadística no encontrada")
    })
    public ResponseEntity<?> delete(
            @Parameter(description = "ID de la estadística a eliminar", required = true) @PathVariable Integer id) {

        Optional<EstadisticaJugadorTotal> estadisticaOptional = estadisticaJugadorTotalService.findEntityById(id);

        if (estadisticaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        estadisticaJugadorTotalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}