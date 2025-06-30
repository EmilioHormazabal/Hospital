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
import java.util.Optional; // Importación necesaria para Optional

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
        try {
            Medico saved = medicoService.save(medico);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // O un DTO de error si lo tienes
        }
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

    // --- NUEVOS Y MODIFICADOS ENDPOINTS DE BÚSQUEDA Y SUELDO ---

    @GetMapping("/antiguedad/{years}")
    @Operation(summary = "Buscar médicos por años de antigüedad",
            description = "Recupera médicos con exactamente N años de antigüedad en la institución")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de médicos obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron médicos con esa antigüedad")
    })
    public ResponseEntity<List<Medico>> getMedicosByAntiguedad(
            @Parameter(description = "Años exactos de antigüedad", example = "5", required = true)
            @PathVariable int years
    ) {
        List<Medico> medicos = medicoService.findByAntiguedadExacta(years);
        return medicos.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(medicos);
    }

    @GetMapping("/especialidad/{nombreEspecialidad}") // Nuevo endpoint para buscar por especialidad
    @Operation(summary = "Buscar médicos por especialidad",
            description = "Recupera médicos que pertenecen a una especialidad específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de médicos obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron médicos para la especialidad especificada")
    })
    public ResponseEntity<List<Medico>> getMedicosByEspecialidad(
            @Parameter(description = "Nombre de la especialidad del médico", example = "Cardiología", required = true)
            @PathVariable String nombreEspecialidad
    ) {
        List<Medico> medicos = medicoService.findByEspecialidad(nombreEspecialidad);
        return medicos.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(medicos);
    }

    @GetMapping("/{id}/sueldo-base") // Endpoint para el sueldo base
    @Operation(summary = "Obtener sueldo base de médico",
            description = "Recupera el sueldo base de un médico específico usando su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sueldo base obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Médico no encontrado")
    })
    public ResponseEntity<Integer> getSueldoBaseById(
            @Parameter(description = "ID del médico", example = "1", required = true)
            @PathVariable int id
    ) {
        return medicoService.findById(id) // Usar findById para obtener el médico completo
                .map(medico -> ResponseEntity.ok(medico.getSueldoBase()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/sueldo-total") // Nuevo endpoint para el sueldo total (base + comisiones)
    @Operation(summary = "Calcular sueldo total de médico (base + comisión)",
            description = "Calcula y recupera el sueldo total de un médico, incluyendo comisiones por atenciones.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sueldo total calculado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Médico no encontrado"),
            @ApiResponse(responseCode = "400", description = "Error en el cálculo del sueldo total")
    })
    public ResponseEntity<Integer> getSueldoTotalById(
            @Parameter(description = "ID del médico", example = "1", required = true)
            @PathVariable int id
    ) {
        try {
            Integer sueldoTotal = medicoService.calcularSueldoTotal(id);
            return ResponseEntity.ok(sueldoTotal);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Médico no encontrado o error específico
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Otros errores en el cálculo
        }
    }
}