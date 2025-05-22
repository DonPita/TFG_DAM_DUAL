package com.pita.waterpolo.controller;

import com.pita.waterpolo.dto.request.TemporadaRequest;
import com.pita.waterpolo.dto.response.TemporadaResponse;
import com.pita.waterpolo.entity.Temporada;
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
@RequestMapping("/temporadas")
@Tag(name = "Temporadas", description = "Endpoints para gestionar temporadas de waterpolo")
public class TemporadaController {

    private final TemporadaService temporadaService;

    public TemporadaController(TemporadaService temporadaService) {
        this.temporadaService = temporadaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las temporadas",
            description = "Devuelve una lista de todas las temporadas registradas en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de temporadas encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay temporadas disponibles")
    })
    public ResponseEntity<?> findAll() {

        List<TemporadaResponse> temporadas = temporadaService.findAll();

        return temporadas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(temporadas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener temporada por ID",
            description = "Devuelve una temporada específica según su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Temporada encontrada"),
            @ApiResponse(responseCode = "404", description = "Temporada no encontrada")
    })
    public ResponseEntity<?> findById(
            @Parameter(description = "ID de la temporada", required = true) @PathVariable Integer id) {

        Optional<TemporadaResponse> temporadaOptional = temporadaService.findById(id);

        return temporadaOptional.isPresent() ? ResponseEntity.ok(temporadaOptional.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear una nueva temporada",
            description = "Registra una nueva temporada en el sistema con los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Temporada creada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<?> save(
            @Parameter(description = "Datos de la temporada a crear", required = true)
            @Valid @RequestBody TemporadaRequest temporadaRequest) {

        TemporadaResponse savedTemporada = temporadaService.save(temporadaRequest);

        return ResponseEntity.created(URI.create("/api-v0/temporadas/" + savedTemporada.id())).body(savedTemporada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una temporada existente",
            description = "Actualiza los datos de una temporada específica identificada por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Temporada actualizada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Temporada no encontrada")
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID de la temporada a actualizar", required = true) @PathVariable Integer id,
            @Parameter(description = "Nuevos datos de la temporada", required = true)
            @Valid @RequestBody TemporadaRequest temporadaRequest) {

        Optional<Temporada> temporadaOptional = temporadaService.findEntityById(id);

        return temporadaOptional.isEmpty() ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(temporadaService.update(temporadaRequest, temporadaOptional));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una temporada",
            description = "Elimina una temporada específica según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Temporada eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Temporada no encontrada")
    })
    public ResponseEntity<?> delete(
            @Parameter(description = "ID de la temporada a eliminar", required = true) @PathVariable Integer id) {
        Optional<Temporada> temporadaOptional = temporadaService.findEntityById(id);

        if (temporadaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        temporadaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}