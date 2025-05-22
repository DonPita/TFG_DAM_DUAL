package com.pita.waterpolo.controller;

import com.pita.waterpolo.dto.request.JugadorEquipoLigaRequest;
import com.pita.waterpolo.dto.response.JugadorEquipoLigaResponse;
import com.pita.waterpolo.entity.EquipoLiga;
import com.pita.waterpolo.entity.Jugador;
import com.pita.waterpolo.entity.JugadorEquipoLiga;
import com.pita.waterpolo.entity.Temporada;
import com.pita.waterpolo.exception.ResourceNotFoundException;
import com.pita.waterpolo.service.*;
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
@RequestMapping("/jugadores-equipos-ligas")
@Tag(name = "Jugadores Equipos Ligas", description = "Endpoints para gestionar la relación entre jugadores, equipos y ligas por temporada")
public class JugadorEquipoLigaController {

    private final JugadorEquipoLigaService jugadorEquipoLigaService;
    private final JugadorService jugadorService;
    private final EquipoLigaService equipoLigaService;
    private final TemporadaService temporadaService;

    public JugadorEquipoLigaController(JugadorEquipoLigaService jugadorEquipoLigaService,
                                       JugadorService jugadorService,
                                       EquipoLigaService equipoLigaService,
                                       TemporadaService temporadaService) {
        this.jugadorEquipoLigaService = jugadorEquipoLigaService;
        this.jugadorService = jugadorService;
        this.equipoLigaService = equipoLigaService;
        this.temporadaService = temporadaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las relaciones",
            description = "Devuelve una lista de todas las relaciones entre jugadores, equipos y ligas registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de relaciones encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay relaciones disponibles")
    })
    public ResponseEntity<?> findAll() {
        List<JugadorEquipoLigaResponse> jugadores = jugadorEquipoLigaService.findAll();

        return jugadores.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(jugadores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener relación por ID",
            description = "Devuelve una relación específica entre un jugador, equipo y liga según su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relación encontrada"),
            @ApiResponse(responseCode = "404", description = "Relación no encontrada")
    })
    public ResponseEntity<?> findById(
            @Parameter(description = "ID de la relación", required = true) @PathVariable Integer id) {

        Optional<JugadorEquipoLigaResponse> jugadorEquipoLigaOptional = jugadorEquipoLigaService.findById(id);

        return jugadorEquipoLigaOptional.isPresent() ? ResponseEntity.ok(jugadorEquipoLigaOptional.get())
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/jugador/{idJugador}")
    @Operation(summary = "Obtener relaciones por jugador",
            description = "Devuelve todas las relaciones de un jugador específico con equipos y ligas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de relaciones encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay relaciones para ese jugador"),
            @ApiResponse(responseCode = "404", description = "Jugador no encontrado")
    })
    public ResponseEntity<?> findByJugador(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Integer idJugador) {

        Jugador jugador = jugadorService.findEntityById(idJugador).orElseThrow(
                () -> new ResourceNotFoundException("Jugador con id: " + idJugador + " no encontrado"));

        List<JugadorEquipoLigaResponse> equipos = jugadorEquipoLigaService.findByJugador(jugador);

        return equipos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(equipos);
    }

    @GetMapping("/equipo-liga/{idEquipoLiga}")
    @Operation(summary = "Obtener relaciones por equipo en liga",
            description = "Devuelve todas las relaciones de jugadores en un equipo y liga específicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de relaciones encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay relaciones para ese equipo en liga"),
            @ApiResponse(responseCode = "404", description = "Equipo en liga no encontrado")
    })
    public ResponseEntity<?> findByEquipoLiga(
            @Parameter(description = "ID del equipo en liga", required = true) @PathVariable Integer idEquipoLiga) {

        EquipoLiga equipoLiga = equipoLigaService.findEntityById(idEquipoLiga).orElseThrow(
                () -> new ResourceNotFoundException("EquipoLiga con id: " + idEquipoLiga + " no encontrado"));

        List<JugadorEquipoLigaResponse> jugadores = jugadorEquipoLigaService.findByEquipoLiga(equipoLiga);

        return jugadores.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(jugadores);
    }

    @GetMapping("/temporada/{idTemporada}")
    @Operation(summary = "Obtener relaciones por temporada",
            description = "Devuelve todas las relaciones de jugadores, equipos y ligas en una temporada específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de relaciones encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay relaciones para esa temporada"),
            @ApiResponse(responseCode = "404", description = "Temporada no encontrada")
    })
    public ResponseEntity<?> findByTemporada(
            @Parameter(description = "ID de la temporada", required = true) @PathVariable Integer idTemporada) {

        Temporada temporada = temporadaService.findEntityById(idTemporada).orElseThrow(
                () -> new ResourceNotFoundException("Temporada con id: " + idTemporada + " no encontrada"));

        List<JugadorEquipoLigaResponse> jugadores = jugadorEquipoLigaService.findByTemporada(temporada);

        return jugadores.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(jugadores);
    }

    @GetMapping("/jugador/{idJugador}/equipo-liga/{idEquipoLiga}")
    @Operation(summary = "Obtener relaciones por jugador y equipo en liga",
            description = "Devuelve las relaciones de un jugador específico con un equipo en una liga.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de relaciones encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay relaciones para esa combinación"),
            @ApiResponse(responseCode = "404", description = "Jugador o equipo en liga no encontrado")
    })
    public ResponseEntity<?> findByJugadorAndEquipoLiga(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Integer idJugador,
            @Parameter(description = "ID del equipo en liga", required = true) @PathVariable Integer idEquipoLiga) {
        Jugador jugador = jugadorService.findEntityById(idJugador).orElseThrow(

                () -> new ResourceNotFoundException("Jugador con id: " + idJugador + " no encontrado"));
        EquipoLiga equipoLiga = equipoLigaService.findEntityById(idEquipoLiga).orElseThrow(
                () -> new ResourceNotFoundException("EquipoLiga con id: " + idEquipoLiga + " no encontrado"));

        List<JugadorEquipoLigaResponse> jugadores = jugadorEquipoLigaService.findByJugadorAndEquipoLiga(jugador, equipoLiga);

        return jugadores.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(jugadores);
    }

    @GetMapping("/jugador/{idJugador}/temporada/{idTemporada}")
    @Operation(summary = "Obtener relaciones por jugador y temporada",
            description = "Devuelve las relaciones de un jugador específico en una temporada dada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de relaciones encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay relaciones para esa combinación"),
            @ApiResponse(responseCode = "404", description = "Jugador o temporada no encontrada")
    })
    public ResponseEntity<?> findAllByJugadorAndTemporada(
            @Parameter(description = "ID del jugador", required = true) @PathVariable Integer idJugador,
            @Parameter(description = "ID de la temporada", required = true) @PathVariable Integer idTemporada) {

        Jugador jugador = jugadorService.findEntityById(idJugador).orElseThrow(
                () -> new ResourceNotFoundException("Jugador con id: " + idJugador + " no encontrado"));
        Temporada temporada = temporadaService.findEntityById(idTemporada).orElseThrow(
                () -> new ResourceNotFoundException("Temporada con id: " + idTemporada + " no encontrada"));

        List<JugadorEquipoLigaResponse> jugadores = jugadorEquipoLigaService.findByJugadorAndTemporada(jugador, temporada);

        return jugadores.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(jugadores);
    }

    @GetMapping("/equipo-liga/{idEquipoLiga}/temporada/{idTemporada}")
    @Operation(summary = "Obtener relaciones por equipo en liga y temporada",
            description = "Devuelve las relaciones de jugadores en un equipo y liga específicos en una temporada dada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de relaciones encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay relaciones para esa combinación"),
            @ApiResponse(responseCode = "404", description = "Equipo en liga o temporada no encontrada")
    })
    public ResponseEntity<?> findAllByEquipoLigaAndTemporada(
            @Parameter(description = "ID del equipo en liga", required = true) @PathVariable Integer idEquipoLiga,
            @Parameter(description = "ID de la temporada", required = true) @PathVariable Integer idTemporada) {

        EquipoLiga equipoLiga = equipoLigaService.findEntityById(idEquipoLiga).orElseThrow(
                () -> new ResourceNotFoundException("EquipoLiga con id: " + idEquipoLiga + " no encontrado"));
        Temporada temporada = temporadaService.findEntityById(idTemporada).orElseThrow(
                () -> new ResourceNotFoundException("Temporada con id: " + idTemporada + " no encontrada"));

        List<JugadorEquipoLigaResponse> jugadores = jugadorEquipoLigaService.findByEquipoLigaAndTemporada(equipoLiga, temporada);

        return jugadores.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(jugadores);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva relación",
            description = "Registra una nueva relación entre un jugador, un equipo en una liga y una temporada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Relación creada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Jugador, equipo en liga o temporada no encontrada")
    })
    public ResponseEntity<?> save(
            @Parameter(description = "Datos de la relación a crear", required = true)
            @Valid @RequestBody JugadorEquipoLigaRequest jugadorEquipoLigaRequest) {

        Jugador jugador = jugadorService.findEntityById(jugadorEquipoLigaRequest.idJugador()).orElseThrow(
                () -> new ResourceNotFoundException("Jugador con id: " + jugadorEquipoLigaRequest.idJugador() + " no encontrado"));
        EquipoLiga equipoLiga = equipoLigaService.findEntityById(jugadorEquipoLigaRequest.idEquipoLiga()).orElseThrow(
                () -> new ResourceNotFoundException("EquipoLiga con id: " + jugadorEquipoLigaRequest.idEquipoLiga() + " no encontrado"));
        Temporada temporada = temporadaService.findEntityById(jugadorEquipoLigaRequest.idTemporada()).orElseThrow(
                () -> new ResourceNotFoundException("Temporada con id: " + jugadorEquipoLigaRequest.idTemporada() + " no encontrada"));

        JugadorEquipoLigaResponse savedJugadorEquipoLiga = jugadorEquipoLigaService.save(jugadorEquipoLigaRequest,
                jugador, equipoLiga, temporada);

        return ResponseEntity.created(URI.create("/api-v0/jugadores-equipos-ligas/" + savedJugadorEquipoLiga.id())).body(savedJugadorEquipoLiga);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una relación existente",
            description = "Actualiza los datos de una relación específica entre un jugador, equipo en liga y temporada según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relación actualizada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Relación, jugador, equipo en liga o temporada no encontrada")
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID de la relación a actualizar", required = true) @PathVariable Integer id,
            @Parameter(description = "Nuevos datos de la relación", required = true)
            @Valid @RequestBody JugadorEquipoLigaRequest jugadorEquipoLigaRequest) {

        Optional<JugadorEquipoLiga> jugadorEquipoLigaOptional = jugadorEquipoLigaService.findEntityById(id);

        if (jugadorEquipoLigaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Jugador jugador = jugadorService.findEntityById(jugadorEquipoLigaRequest.idJugador()).orElseThrow(
                () -> new ResourceNotFoundException("Jugador con id: " + jugadorEquipoLigaRequest.idJugador() + " no encontrado"));
        EquipoLiga equipoLiga = equipoLigaService.findEntityById(jugadorEquipoLigaRequest.idEquipoLiga()).orElseThrow(
                () -> new ResourceNotFoundException("EquipoLiga con id: " + jugadorEquipoLigaRequest.idEquipoLiga() + " no encontrado"));
        Temporada temporada = temporadaService.findEntityById(jugadorEquipoLigaRequest.idTemporada()).orElseThrow(
                () -> new ResourceNotFoundException("Temporada con id: " + jugadorEquipoLigaRequest.idTemporada() + " no encontrada"));

        JugadorEquipoLigaResponse updatedJugadorEquipoLiga = jugadorEquipoLigaService.update(jugadorEquipoLigaRequest,
                jugador, equipoLiga, temporada,
                jugadorEquipoLigaOptional);

        return ResponseEntity.ok(updatedJugadorEquipoLiga);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una relación",
            description = "Elimina una relación específica entre un jugador, equipo en liga y temporada según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Relación eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Relación no encontrada")
    })
    public ResponseEntity<?> delete(
            @Parameter(description = "ID de la relación a eliminar", required = true) @PathVariable Integer id) {

        Optional<JugadorEquipoLiga> jugadorEquipoLigaOptional = jugadorEquipoLigaService.findEntityById(id);

        if (jugadorEquipoLigaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        jugadorEquipoLigaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
