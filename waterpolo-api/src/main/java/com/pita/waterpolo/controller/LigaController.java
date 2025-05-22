package com.pita.waterpolo.controller;

import com.pita.waterpolo.dto.request.LigaRequest;
import com.pita.waterpolo.dto.response.LigaResponse;
import com.pita.waterpolo.entity.Liga;
import com.pita.waterpolo.service.LigaService;
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
@RequestMapping("/ligas")
@Tag(name = "Ligas", description = "Endpoints para gestionar ligas de waterpolo")
public class LigaController {

    private final LigaService ligaService;

    public LigaController(LigaService ligaService) {
        this.ligaService = ligaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las ligas",
            description = "Devuelve una lista de todas las ligas registradas en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ligas encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay ligas disponibles")
    })
    public ResponseEntity<?> findAll() {

        List<LigaResponse> ligas = ligaService.findAll();

        return ligas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ligas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener liga por ID",
            description = "Devuelve una liga específica según su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liga encontrada"),
            @ApiResponse(responseCode = "404", description = "Liga no encontrada")
    })
    public ResponseEntity<?> findById(
            @Parameter(description = "ID de la liga", required = true) @PathVariable Integer id) {

        Optional<LigaResponse> ligaOptional = ligaService.findById(id);

        return ligaOptional.isPresent() ? ResponseEntity.ok(ligaOptional.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear una nueva liga",
            description = "Registra una nueva liga en el sistema con los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Liga creada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<?> save(
            @Parameter(description = "Datos de la liga a crear", required = true)
            @Valid @RequestBody LigaRequest ligaRequest) {

        LigaResponse savedLiga = ligaService.save(ligaRequest);

        return ResponseEntity.created(URI.create("/api-v0/ligas/" + savedLiga.id())).body(savedLiga);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una liga existente",
            description = "Actualiza los datos de una liga específica identificada por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liga actualizada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Liga no encontrada")
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID de la liga a actualizar", required = true) @PathVariable Integer id,
            @Parameter(description = "Nuevos datos de la liga", required = true)
            @Valid @RequestBody LigaRequest ligaRequest) {

        Optional<Liga> ligaOptional = ligaService.findEntityById(id);

        return ligaOptional.isEmpty() ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(ligaService.update(ligaRequest, ligaOptional));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una liga",
            description = "Elimina una liga específica según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Liga eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Liga no encontrada")
    })
    public ResponseEntity<?> delete(
            @Parameter(description = "ID de la liga a eliminar", required = true) @PathVariable Integer id) {

        Optional<Liga> ligaOptional = ligaService.findEntityById(id);

        if (ligaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ligaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}