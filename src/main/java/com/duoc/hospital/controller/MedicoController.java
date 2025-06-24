// `src/main/java/com/duoc/hospital/controller/MedicoController.java`
package com.duoc.hospital.controller;

import com.duoc.hospital.model.Medico;
import com.duoc.hospital.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Medicos", description = "Operaciones relacionadas con los médicos")
@RestController
@RequestMapping("/api/v1/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    @Operation(summary = "Obtener todos los médicos", description = "Devuelve la lista de médicos registrados")
    public ResponseEntity<List<Medico>> getAll() {
        List<Medico> list = medicoService.getAllMedicos();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener médico por ID", description = "Busca un médico específico por su identificador")
    public ResponseEntity<Medico> getById(@PathVariable Integer id) {
        return medicoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo médico", description = "Registra un nuevo médico en el sistema")
    public ResponseEntity<Medico> create(@RequestBody Medico medico) {
        Medico saved = medicoService.save(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar médico", description = "Elimina un médico por su ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        medicoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}