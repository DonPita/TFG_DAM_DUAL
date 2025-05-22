package com.pita.waterpolo.controller;

import com.pita.waterpolo.dto.request.EquipoLigaRequest;
import com.pita.waterpolo.dto.response.EquipoLigaResponse;
import com.pita.waterpolo.entity.Equipo;
import com.pita.waterpolo.entity.EquipoLiga;
import com.pita.waterpolo.entity.Liga;
import com.pita.waterpolo.exception.ResourceNotFoundException;
import com.pita.waterpolo.service.EquipoLigaService;
import com.pita.waterpolo.service.EquipoService;
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
@RequestMapping("/equipos-liga")
@Tag(name = "Equipos Liga", description = "Endpoints para gestionar la relación entre los equipos y las ligas en las que participan")
public class EquipoLigaController {

    private final EquipoLigaService equipoLigaService;
    private final EquipoService equipoService;
    private final LigaService ligaService;

    public EquipoLigaController(EquipoLigaService equipoLigaService, EquipoService equipoService, LigaService ligaService) {
        this.equipoLigaService = equipoLigaService;
        this.equipoService = equipoService;
        this.ligaService = ligaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los equipos en ligas",
            description = "Devuelve una lista de todas las relaciones entre equipos y ligas registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de equipos en ligas encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay relaciones disponibles")
    })
    public ResponseEntity<?> getAll() {
        List<EquipoLigaResponse> equiposLiga = equipoLigaService.findAll();

        return equiposLiga.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(equiposLiga);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener relación equipo-liga por ID",
            description = "Devuelve una relación específica entre un equipo y una liga según su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relación encontrada"),
            @ApiResponse(responseCode = "404", description = "Relación no encontrada")
    })
    public ResponseEntity<?> getById(
            @Parameter(description = "ID de la relación equipo-liga", required = true) @PathVariable Integer id) {
        Optional<EquipoLigaResponse> equipoLigaOptional = equipoLigaService.findById(id);

        return equipoLigaOptional.isPresent() ? ResponseEntity.ok(equipoLigaOptional.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/equipo/{idEquipo}")
    @Operation(summary = "Obtener relaciones por equipo",
            description = "Devuelve todas las relaciones de un equipo específico con distintas ligas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de relaciones encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay relaciones para ese equipo")
    })
    public ResponseEntity<?> getByEquipo(
            @Parameter(description = "ID del equipo", required = true) @PathVariable Integer idEquipo) {
        List<EquipoLigaResponse> equiposLigas = equipoLigaService.findByEquipo(idEquipo);

        return equiposLigas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(equiposLigas);
    }

    @GetMapping("/liga/{idLiga}")
    @Operation(summary = "Obtener relaciones por liga",
            description = "Devuelve todas las relaciones de equipos en una liga específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de relaciones encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay relaciones para esa liga")
    })
    public ResponseEntity<?> getByLiga(
            @Parameter(description = "ID de la liga", required = true) @PathVariable Integer idLiga) {
        List<EquipoLigaResponse> equiposLiga = equipoLigaService.findByLiga(idLiga);

        return equiposLiga.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(equiposLiga);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva relación equipo-liga",
            description = "Registra una nueva relación entre un equipo y una liga con los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Relación creada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Equipo o liga no encontrado")
    })
    public ResponseEntity<?> save(
            @Parameter(description = "Datos de la relación equipo-liga a crear", required = true)
            @Valid @RequestBody EquipoLigaRequest equipoLigaRequest) {

        Equipo equipo = equipoService.findEntityById(equipoLigaRequest.idEquipo())
                .orElseThrow(() -> new ResourceNotFoundException("Equipo con ID " + equipoLigaRequest.idEquipo() + " no encontrado"));
        Liga liga = ligaService.findEntityById(equipoLigaRequest.idLiga())
                .orElseThrow(() -> new ResourceNotFoundException("Liga con ID " + equipoLigaRequest.idLiga() + " no encontrada"));

        EquipoLigaResponse savedEquipoLiga = equipoLigaService.save(equipoLigaRequest, equipo, liga);

        return ResponseEntity.created(URI.create("/api-v0/equipos-liga/" + savedEquipoLiga.id())).body(savedEquipoLiga);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una relación equipo-liga existente",
            description = "Actualiza los datos de una relación específica entre un equipo y una liga según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relación actualizada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Relación, equipo o liga no encontrada")
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID de la relación equipo-liga a actualizar", required = true) @PathVariable Integer id,
            @Parameter(description = "Nuevos datos de la relación equipo-liga", required = true)
            @Valid @RequestBody EquipoLigaRequest equipoLigaRequest) {

        Optional<EquipoLiga> equipoLigaOptional = equipoLigaService.findEntityById(id);

        if (equipoLigaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Equipo equipo = equipoService.findEntityById(equipoLigaRequest.idEquipo())
                .orElseThrow(() -> new ResourceNotFoundException("Equipo con ID " + equipoLigaRequest.idEquipo() + " no encontrado"));
        Liga liga = ligaService.findEntityById(equipoLigaRequest.idLiga())
                .orElseThrow(() -> new ResourceNotFoundException("Liga con ID " + equipoLigaRequest.idLiga() + " no encontrada"));

        EquipoLigaResponse updatedEquipoLiga = equipoLigaService.update(equipoLigaRequest, equipo, liga, equipoLigaOptional);

        return ResponseEntity.ok(updatedEquipoLiga);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una relación equipo-liga",
            description = "Elimina una relación específica entre un equipo y una liga según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Relación eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Relación no encontrada")
    })
    public ResponseEntity<?> delete(
            @Parameter(description = "ID de la relación equipo-liga a eliminar", required = true) @PathVariable Integer id) {

        Optional<EquipoLiga> equipoLigaOptional = equipoLigaService.findEntityById(id);

        if (equipoLigaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        equipoLigaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}