// `src/main/java/com/duoc/hospital/controller/EstadoController.java`
package com.duoc.hospital.controller;

import com.duoc.hospital.model.Estado;
import com.duoc.hospital.service.EstadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Estados", description = "Operaciones relacionadas con los estados de las solicitudes")
@RestController
@RequestMapping("/api/v1/estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @GetMapping
    @Operation(summary = "Obtener todos los estados", description = "Devuelve la lista de todos los estados registrados")
    public ResponseEntity<List<Estado>> getAll() {
        List<Estado> list = estadoService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener estado por ID", description = "Busca un estado específico por su identificador")
    public ResponseEntity<Estado> getById(@PathVariable Integer id) {
        return estadoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo estado", description = "Registra un nuevo estado en el sistema")
    public ResponseEntity<Estado> create(@RequestBody Estado estado) {
        Estado saved = estadoService.save(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar estado", description = "Elimina un estado por su ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        estadoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}