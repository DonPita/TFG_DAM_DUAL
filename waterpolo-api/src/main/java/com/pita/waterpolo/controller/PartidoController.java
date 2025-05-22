package com.pita.waterpolo.controller;

import com.pita.waterpolo.dto.request.PartidoRequest;
import com.pita.waterpolo.dto.response.PartidoResponse;
import com.pita.waterpolo.entity.EquipoLiga;
import com.pita.waterpolo.entity.Partido;
import com.pita.waterpolo.entity.Temporada;
import com.pita.waterpolo.exception.ResourceNotFoundException;
import com.pita.waterpolo.service.EquipoLigaService;
import com.pita.waterpolo.service.PartidoService;
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
@RequestMapping("/partidos")
@Tag(name = "Partidos", description = "Endpoints para gestionar partidos de waterpolo")
public class PartidoController {

    private final PartidoService partidoService;
    private final TemporadaService temporadaService;
    private final EquipoLigaService equipoLigaService;

    public PartidoController(PartidoService partidoService, TemporadaService temporadaService, EquipoLigaService equipoLigaService) {
        this.partidoService = partidoService;
        this.temporadaService = temporadaService;
        this.equipoLigaService = equipoLigaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los partidos",
            description = "Devuelve una lista de todos los partidos registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de partidos encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay partidos disponibles")
    })
    public ResponseEntity<?> findAll() {

        List<PartidoResponse> partidos = partidoService.findAll();

        return partidos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(partidos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener partido por ID",
            description = "Devuelve un partido específico según su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partido encontrado"),
            @ApiResponse(responseCode = "404", description = "Partido no encontrado")
    })
    public ResponseEntity<?> findById(
            @Parameter(description = "ID del partido", required = true) @PathVariable Integer id) {

        Optional<PartidoResponse> partidoOptional = partidoService.findById(id);

        return partidoOptional.isPresent() ? ResponseEntity.ok(partidoOptional.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/temporada/{idTemporada}")
    @Operation(summary = "Obtener partidos por temporada",
            description = "Devuelve todos los partidos de una temporada específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de partidos encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay partidos para esa temporada"),
            @ApiResponse(responseCode = "404", description = "Temporada no encontrada")
    })
    public ResponseEntity<?> findByTemporada(
            @Parameter(description = "ID de la temporada", required = true) @PathVariable Integer idTemporada) {

        Temporada temporada = temporadaService.findEntityById(idTemporada).orElseThrow(
                () -> new ResourceNotFoundException("Temporada con id: " + idTemporada + " no encontrada"));

        List<PartidoResponse> partidos = partidoService.findByTemporada(temporada);

        return partidos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(partidos);
    }

    @GetMapping("/temporada/{idTemporada}/jornada/{jornada}")
    @Operation(summary = "Obtener partidos por temporada y jornada",
            description = "Devuelve todos los partidos de una temporada y jornada específicas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de partidos encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay partidos para esa temporada y jornada"),
            @ApiResponse(responseCode = "404", description = "Temporada no encontrada")
    })
    public ResponseEntity<?> findPartidosByTemporadaAndJornada(
            @Parameter(description = "ID de la temporada", required = true) @PathVariable Integer idTemporada,
            @Parameter(description = "Número de jornada", required = true) @PathVariable Integer jornada) {

        Temporada temporada = temporadaService.findEntityById(idTemporada).orElseThrow(
                () -> new ResourceNotFoundException("Temporada con id: " + idTemporada + " no encontrada"));

        List<PartidoResponse> partidos = partidoService.findPartidosByTemporadaAndJornada(temporada, jornada);

        return partidos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(partidos);
    }

    @GetMapping("/temporada/{idTemporada}/equipo/{idEquipo}")
    @Operation(summary = "Obtener partidos por equipo y temporada",
            description = "Devuelve todos los partidos de un equipo en una temporada específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de partidos encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay partidos para ese equipo y temporada"),
            @ApiResponse(responseCode = "404", description = "Temporada o equipo no encontrado")
    })
    public ResponseEntity<?> findPartidosByEquipoLigaAndTemporada(
            @Parameter(description = "ID de la temporada", required = true) @PathVariable Integer idTemporada,
            @Parameter(description = "ID del equipo en liga", required = true) @PathVariable Integer idEquipo) {

        Temporada temporada = temporadaService.findEntityById(idTemporada).orElseThrow(
                () -> new ResourceNotFoundException("Temporada con id: " + idTemporada + " no encontrada"));
        EquipoLiga equipoLiga = equipoLigaService.findEntityById(idEquipo).orElseThrow(
                () -> new ResourceNotFoundException("Equipo con id: " + idEquipo + " no encontrado"));

        List<PartidoResponse> partidos = partidoService.findPartidosByEquipoLigaAndTemporada(equipoLiga, temporada);

        return partidos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(partidos);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo partido",
            description = "Registra un nuevo partido en el sistema con los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Partido creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<?> save(
            @Parameter(description = "Datos del partido a crear", required = true)
            @Valid @RequestBody PartidoRequest partidoRequest) {

        PartidoResponse savedPartido = partidoService.save(partidoRequest);

        return ResponseEntity.created(URI.create("/api-v0/partidos/" + savedPartido.id())).body(savedPartido);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un partido existente",
            description = "Actualiza los datos de un partido específico identificado por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partido actualizado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Partido, temporada o equipo no encontrado")
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID del partido a actualizar", required = true) @PathVariable Integer id,
            @Parameter(description = "Nuevos datos del partido", required = true)
            @Valid @RequestBody PartidoRequest partidoRequest) {

        Optional<Partido> partidoOptional = partidoService.findEntityById(id);

        if (partidoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Temporada temporada = temporadaService.findEntityById(partidoRequest.idTemporada()).orElseThrow(
                () -> new ResourceNotFoundException("Temporada con id: " + partidoRequest.idTemporada() + " no encontrada"));
        EquipoLiga equipoLigaLocal = equipoLigaService.findEntityById(partidoRequest.idEquipoLigaLocal()).orElseThrow(
                () -> new ResourceNotFoundException("Equipo con id: " + partidoRequest.idEquipoLigaLocal() + " no encontrado"));
        EquipoLiga equipoLigaVisitante = equipoLigaService.findEntityById(partidoRequest.idEquipoLigaVisitante()).orElseThrow(
                () -> new ResourceNotFoundException("Equipo con id: " + partidoRequest.idEquipoLigaVisitante() + " no encontrado"));

        PartidoResponse partidoResponse = partidoService.update(partidoRequest, temporada, equipoLigaLocal,
                equipoLigaVisitante, partidoOptional);

        return ResponseEntity.ok(partidoResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un partido",
            description = "Elimina un partido específico según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Partido eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Partido no encontrado")
    })
    public ResponseEntity<?> delete(
            @Parameter(description = "ID del partido a eliminar", required = true) @PathVariable Integer id) {

        Optional<Partido> partidoOptional = partidoService.findEntityById(id);

        if (partidoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        partidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
