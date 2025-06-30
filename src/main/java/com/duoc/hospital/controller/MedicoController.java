package com.duoc.hospital.controller;

import com.duoc.hospital.model.Medico;
import com.duoc.hospital.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Medicos", description = "Gestión de médicos: CRUD y operaciones especializadas")
@RestController
@RequestMapping("/api/v1/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    @Operation(summary = "Obtener todos los médicos", description = "Recupera la lista completa de médicos registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de médicos obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay médicos registrados")
    })
    public ResponseEntity<List<Medico>> getAll() {
        List<Medico> list = medicoService.getAllMedicos();
        return list.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar médico por ID", description = "Recupera un médico específico usando su identificador único")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Médico encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Médico no encontrado")
    })
    public ResponseEntity<Medico> getById(
            @Parameter(description = "ID del médico", example = "1", required = true)
            @PathVariable Integer id
    ) {
        return medicoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Registrar nuevo médico", description = "Crea un nuevo registro de médico en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Médico creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o conflictos (RUN/correo/teléfono duplicados)")
    })
    public ResponseEntity<Medico> create(@RequestBody Medico medico) {
        Medico saved = medicoService.save(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar médico existente", description = "Actualiza los datos de un médico registrado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Médico actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Médico no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o conflictos (RUN/correo/teléfono duplicados)")
    })
    public ResponseEntity<Medico> update(
            @Parameter(description = "ID del médico a actualizar", example = "1", required = true)
            @PathVariable Integer id,
            @RequestBody Medico medico
    ) {
        try {
            return medicoService.update(id, medico)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar médico", description = "Elimina un médico del sistema usando su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Médico eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Médico no encontrado")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del médico a eliminar", example = "1", required = true)
            @PathVariable Integer id
    ) {
        if (medicoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        medicoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
