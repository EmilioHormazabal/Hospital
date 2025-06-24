// `src/main/java/com/duoc/hospital/controller/PacienteController.java`
package com.duoc.hospital.controller;

import com.duoc.hospital.model.Paciente;
import com.duoc.hospital.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
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
    public ResponseEntity<List<Paciente>> getAll() {
        List<Paciente> list = pacienteService.getAllPacientes();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener paciente por ID", description = "Busca un paciente específico por su identificador")
    public ResponseEntity<Paciente> getById(@PathVariable Integer id) {
        return pacienteService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo paciente", description = "Registra un nuevo paciente en el sistema")
    public ResponseEntity<Paciente> create(@RequestBody Paciente paciente) {
        Paciente saved = pacienteService.save(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar paciente", description = "Elimina un paciente por su ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        pacienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}