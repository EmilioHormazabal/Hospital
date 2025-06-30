package com.duoc.hospital.controller;

import com.duoc.hospital.model.Paciente;
import com.duoc.hospital.service.PacienteService;
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

@Tag(name = "Pacientes", description = "Operaciones relacionadas con los pacientes")
@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    @Operation(summary = "Obtener todos los pacientes", description = "Devuelve la lista de pacientes registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes obtenida"),
            @ApiResponse(responseCode = "204", description = "No hay pacientes registrados")
    })
    public ResponseEntity<List<Paciente>> getAll() {
        List<Paciente> list = pacienteService.getAllPacientes();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener paciente por ID", description = "Busca un paciente específico por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<Paciente> getById(@Parameter(description = "ID del paciente a buscar") @PathVariable Integer id) {
        return pacienteService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo paciente", description = "Registra un nuevo paciente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (ej. RUN duplicado)")
    })
    public ResponseEntity<Paciente> create(@RequestBody Paciente paciente) {
        try {
            Paciente saved = pacienteService.save(paciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un paciente existente", description = "Actualiza los datos de un paciente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos (ej. RUN duplicado en otro paciente)")
    })
    public ResponseEntity<Paciente> update(@Parameter(description = "ID del paciente a actualizar") @PathVariable Integer id, @RequestBody Paciente paciente) {
        try {
            return pacienteService.update(id, paciente)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar paciente", description = "Elimina un paciente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paciente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado para eliminar")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "ID del paciente a eliminar") @PathVariable Integer id) {
        if (pacienteService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        pacienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // --- ENDPOINTS DE REPORTES ---

    @GetMapping("/reportes/mayores")
    @Operation(summary = "Buscar pacientes mayores de una edad", description = "Devuelve pacientes cuya edad es mayor a la especificada")
    public ResponseEntity<List<Paciente>> getMayoresDe(@Parameter(description = "Edad para el filtro") @RequestParam int edad) {
        List<Paciente> pacientes = pacienteService.findMayoresDeEdad(edad);
        if (pacientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/reportes/menores")
    @Operation(summary = "Buscar pacientes menores de una edad", description = "Devuelve pacientes cuya edad es menor a la especificada")
    public ResponseEntity<List<Paciente>> getMenoresDe(@Parameter(description = "Edad para el filtro") @RequestParam int edad) {
        List<Paciente> pacientes = pacienteService.findMenoresDeEdad(edad);
        if (pacientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/reportes/prevision")
    @Operation(summary = "Buscar pacientes por nombre de previsión", description = "Devuelve pacientes que pertenecen a una previsión (ej. FONASA, ISAPRE)")
    public ResponseEntity<List<Paciente>> getByPrevision(@Parameter(description = "Nombre de la previsión") @RequestParam String nombre) {
        List<Paciente> pacientes = pacienteService.findByPrevisionNombre(nombre);
        if (pacientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pacientes);
    }
    @GetMapping("/{id}/deuda")
    @Operation(
            summary = "Obtener deuda de paciente por ID",
            description = "Recupera el monto de deuda asociado a un paciente específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deuda obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    public ResponseEntity<Integer> getDeudaById(
            @Parameter(
                    description = "ID del paciente",
                    example = "1",
                    required = true
            )
            @PathVariable int id
    ) {
        return pacienteService.getPacienteById(id)
                .map(paciente -> ResponseEntity.ok(paciente.getDeuda()))
                .orElse(ResponseEntity.notFound().build());
    }
}