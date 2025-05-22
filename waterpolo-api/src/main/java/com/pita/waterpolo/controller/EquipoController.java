package com.pita.waterpolo.controller;

import com.pita.waterpolo.dto.request.EquipoRequest;
import com.pita.waterpolo.dto.response.EquipoResponse;
import com.pita.waterpolo.entity.Equipo;
import com.pita.waterpolo.service.EquipoService;
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
@RequestMapping("/equipos")
@Tag(name = "Equipos", description = "Endpoints para gestionar equipos de waterpolo")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los equipos",
            description = "Devuelve una lista de todos los equipos registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de equipos encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay equipos disponibles")
    })
    public ResponseEntity<?> findAll() {

        List<EquipoResponse> equipos = equipoService.findAll();

        return equipos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(equipos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener equipo por ID",
            description = "Devuelve un equipo específico según su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo encontrado"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    public ResponseEntity<?> findById(
            @Parameter(description = "ID del equipo", required = true) @PathVariable Integer id) {

        Optional<EquipoResponse> equipoOptional = equipoService.findById(id);

        return equipoOptional.isPresent() ? ResponseEntity.ok(equipoOptional.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo equipo",
            description = "Registra un nuevo equipo en el sistema con los datos proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Equipo creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<?> save(
            @Parameter(description = "Datos del equipo a crear", required = true)
            @Valid @RequestBody EquipoRequest equipoRequest) {

        EquipoResponse savedEquipo = equipoService.save(equipoRequest);

        return ResponseEntity.created(URI.create("/api-v0/equipos/" + savedEquipo.id())).body(savedEquipo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un equipo existente",
            description = "Actualiza los datos de un equipo específico identificado por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo actualizado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID del equipo a actualizar", required = true) @PathVariable Integer id,
            @Parameter(description = "Nuevos datos del equipo", required = true)
            @Valid @RequestBody EquipoRequest equipoRequest) {

        Optional<Equipo> equipoOptional = equipoService.findEntityById(id);

        return equipoOptional.isEmpty() ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(equipoService.update(equipoRequest, equipoOptional));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un equipo",
            description = "Elimina un equipo específico según su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Equipo eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    public ResponseEntity<?> delete(
            @Parameter(description = "ID del equipo a eliminar", required = true) @PathVariable Integer id) {

        Optional<Equipo> equipoOptional = equipoService.findEntityById(id);

        if (equipoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        equipoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}