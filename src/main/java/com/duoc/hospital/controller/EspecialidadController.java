// src/main/java/com/duoc/hospital/controller/EspecialidadController.java
package com.duoc.hospital.controller;

import com.duoc.hospital.model.Especialidad;
import com.duoc.hospital.service.EspecialidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Especialidades", description = "Operaciones relacionadas con las especialidades médicas")
@RestController
@RequestMapping("/api/v1/especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    @GetMapping
    @Operation(summary = "Obtener todas las especialidades",
            description = "Devuelve la lista de todas las especialidades registradas")
    public ResponseEntity<List<Especialidad>> getAll() {
        List<Especialidad> list = especialidadService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener especialidad por ID",
            description = "Busca una especialidad específica por su identificador")
    public ResponseEntity<Especialidad> getById(@PathVariable Integer id) {
        return especialidadService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nueva especialidad",
            description = "Registra una nueva especialidad médica en el sistema")
    public ResponseEntity<Especialidad> create(@RequestBody Especialidad especialidad) {
        Especialidad saved = especialidadService.save(especialidad);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar especialidad",
            description = "Elimina una especialidad del sistema por su ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        especialidadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}