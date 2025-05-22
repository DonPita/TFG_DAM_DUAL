package com.pita.waterpolo.controller;

import com.pita.waterpolo.dto.request.ClasificacionLigaTemporadaRequest;
import com.pita.waterpolo.dto.response.ClasificacionLigaTemporadaResponse;
import com.pita.waterpolo.entity.*;
import com.pita.waterpolo.exception.ResourceNotFoundException;
import com.pita.waterpolo.service.ClasificacionLigaTemporadaService;
import com.pita.waterpolo.service.EquipoLigaService;
import com.pita.waterpolo.service.LigaService;
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
@RequestMapping("/clasificacion-liga-temporada")
@Tag(name = "Clasificación Liga Temporada", description = "Endpoints para gestionar clasificaciones de ligas por temporada")
public class ClasificacionLigaTemporadaController {

    private final ClasificacionLigaTemporadaService clasificacionLigaTemporadaService;
    private final TemporadaService temporadaService;
    private final LigaService ligaService;
    private final EquipoLigaService equipoLigaService;

    public ClasificacionLigaTemporadaController(ClasificacionLigaTemporadaService clasificacionLigaTemporadaService,
                                                TemporadaService temporadaService,
                                                LigaService ligaService, EquipoLigaService equipoLigaService) {
        this.clasificacionLigaTemporadaService = clasificacionLigaTemporadaService;
        this.temporadaService = temporadaService;
        this.ligaService = ligaService;
        this.equipoLigaService = equipoLigaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las clasificaciones",
            description = "Devuelve una lista de todas las clasificaciones de ligas por temporada registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clasificaciones encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay clasificaciones disponibles")
    })
    public ResponseEntity<?> getAll() {
        List<ClasificacionLigaTemporadaResponse> clasificaciones = clasificacionLigaTemporadaService.findAll();

        return clasificaciones.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(clasificaciones);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener clasificación por ID",
            description = "Devuelve una clasificación específica según su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clasificación encontrada"),
            @ApiResponse(responseCode = "404", description = "Clasificación no encontrada")
    })
    public ResponseEntity<?> getById(
            @Parameter(description = "ID de la clasificación", required = true) @PathVariable Integer id) {

        Optional<ClasificacionLigaTemporadaResponse> clasificacion = clasificacionLigaTemporadaService.findById(id);

        return clasificacion.isPresent() ? ResponseEntity.ok(clasificacion.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/liga/{idLiga}/temporada/{idTemporada}/jornada/{jornada}")
    @Operation(summary = "Obtener clasificación por liga, temporada y jornada",
            description = "Devuelve la clasificación de una liga específica en una temporada y jornada dadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clasificación encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay datos para esa combinación"),
            @ApiResponse(responseCode = "404", description = "Liga o temporada no encontrada")
    })
    public ResponseEntity<?> getByLigaAndTemporadaAndJornada(
            @Parameter(description = "ID de la liga", required = true) @PathVariable Integer idLiga,
            @Parameter(description = "ID de la temporada", required = true) @PathVariable Integer idTemporada,
            @Parameter(description = "Número de jornada", required = true) @PathVariable Integer jornada) {

        Liga liga = ligaService.findEntityById(idLiga).orElseThrow(
                () -> new ResourceNotFoundException("Liga con ID: " + idLiga + " no encontrada"));
        Temporada temporada = temporadaService.findEntityById(idTemporada).orElseThrow(
                () -> new ResourceNotFoundException("Temporada con ID: " + idTemporada + " no encontrada"));

        List<ClasificacionLigaTemporadaResponse> lista = clasificacionLigaTemporadaService.findByLigaAndTemporadaAndJornada(liga, temporada, jornada);

        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @GetMapping("/liga/{idLiga}/temporada/{idTemporada}/global")
    @Operation(summary = "Obtener clasificación global por liga y temporada",
            description = "Devuelve la clasificación global de una liga en una temporada, basada en la última jornada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clasificación global encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay datos para esa combinación"),
            @ApiResponse(responseCode = "404", description = "Liga o temporada no encontrada")
    })
    public ResponseEntity<?> getByLigaAndTemporadaGlobal(
            @Parameter(description = "ID de la liga", required = true) @PathVariable Integer idLiga,
            @Parameter(description = "ID de la temporada", required = true) @PathVariable Integer idTemporada) {

        Liga liga = ligaService.findEntityById(idLiga).orElseThrow(
                () -> new ResourceNotFoundException("Liga con ID: " + idLiga + " no encontrada"));
        Temporada temporada = temporadaService.findEntityById(idTemporada).orElseThrow(
                () -> new ResourceNotFoundException("Temporada con ID: " + idTemporada + " no encontrada"));

        List<ClasificacionLigaTemporadaResponse> lista = clasificacionLigaTemporadaService.findClasificacionGlobalByLigaAndTemporada(liga, temporada);

        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @GetMapping("/equipo/{idEquipo}/temporada/{idTemporada}")
    @Operation(summary = "Obtener clasificación por equipo y temporada",
            description = "Devuelve la clasificación de un equipo específico en una temporada dada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clasificación encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay datos para esa combinación"),
            @ApiResponse(responseCode = "404", description = "Equipo o temporada no encontrada")
    })
    public ResponseEntity<?> findByEquipoLigaAndTemporada(
            @Parameter(description = "ID del equipo en liga", required = true) @PathVariable Integer idEquipo,
            @Parameter(description = "ID de la temporada", required = true) @PathVariable Integer idTemporada) {

        EquipoLiga equipoLiga = equipoLigaService.findEntityById(idEquipo).orElseThrow(
                () -> new ResourceNotFoundException("EquipoLiga con ID: " + idEquipo + " no encontrado"));
        Temporada temporada = temporadaService.findEntityById(idTemporada).orElseThrow(
                () -> new ResourceNotFoundException("Temporada con ID: " + idTemporada + " no encontrada"));

        List<ClasificacionLigaTemporadaResponse> lista = clasificacionLigaTemporadaService.findByEquipoLigaAndTemporada(equipoLiga, temporada);

        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva clasificación",
            description = "Registra una nueva clasificación para un equipo en una temporada y jornada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Clasificación creada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Temporada o equipo no encontrado")
    })
    public ResponseEntity<?> save(
            @Parameter(description = "Datos de la clasificación a crear", required = true)
            @Valid @RequestBody ClasificacionLigaTemporadaRequest clasificacionLigaTemporadaRequest) {

        Temporada temporada = temporadaService.findEntityById(clasificacionLigaTemporadaRequest.idTemporada()).orElseThrow(
                () -> new ResourceNotFoundException("Temporada con ID: " + clasificacionLigaTemporadaRequest.idTemporada() + " no encontrada"));
        EquipoLiga equipoLiga = equipoLigaService.findEntityById(clasificacionLigaTemporadaRequest.idEquipoLiga()).orElseThrow(
                () -> new ResourceNotFoundException("Equipo Liga con ID: " + clasificacionLigaTemporadaRequest.idEquipoLiga() + " no encontrado"));

        ClasificacionLigaTemporadaResponse clasificacion = clasificacionLigaTemporadaService.save(clasificacionLigaTemporadaRequest,
                temporada, equipoLiga);

        return ResponseEntity.created(URI.create("/api-v0/clasificacion-liga-temporada/" + clasificacion.id())).body(clasificacion);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar o crear una clasificación",
            description = "Actualiza una clasificación existente o crea una nueva si no existe. Nota: el trigger after_insert_partido suele manejar estas actualizaciones automáticamente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clasificación actualizada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Clasificación, temporada o equipo no encontrado")
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID de la clasificación a actualizar", required = true) @PathVariable Integer id,
            @Parameter(description = "Nuevos datos de la clasificación", required = true)
            @Valid @RequestBody ClasificacionLigaTemporadaRequest clasificacionLigaTemporadaRequest) {

        Optional<ClasificacionLigaTemporada> clasificacion = clasificacionLigaTemporadaService.findEntityById(id);

        if (clasificacion.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Temporada temporada = temporadaService.findEntityById(clasificacionLigaTemporadaRequest.idTemporada()).orElseThrow(
                () -> new ResourceNotFoundException("Temporada con ID: " + clasificacionLigaTemporadaRequest.idTemporada() + " no encontrada"));
        EquipoLiga equipoLiga = equipoLigaService.findEntityById(clasificacionLigaTemporadaRequest.idEquipoLiga()).orElseThrow(
                () -> new ResourceNotFoundException("Equipo Liga con ID: " + clasificacionLigaTemporadaRequest.idEquipoLiga() + " no encontrado"));

        ClasificacionLigaTemporadaResponse updatedClasificacion = clasificacionLigaTemporadaService.update(clasificacionLigaTemporadaRequest,
                temporada, equipoLiga, clasificacion);

        return ResponseEntity.ok(updatedClasificacion);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una clasificación",
            description = "Elimina una clasificación específica según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Clasificación eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Clasificación no encontrada")
    })
    public ResponseEntity<?> delete(
            @Parameter(description = "ID de la clasificación a eliminar", required = true) @PathVariable Integer id) {

        Optional<ClasificacionLigaTemporada> clasificacion = clasificacionLigaTemporadaService.findEntityById(id);

        if (clasificacion.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        clasificacionLigaTemporadaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}