// `src/main/java/com/duoc/hospital/controller/PrevisionController.java`
package com.duoc.hospital.controller;

import com.duoc.hospital.model.Prevision;
import com.duoc.hospital.service.PrevisionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Previsiones", description = "Operaciones relacionadas con las previsiones de salud")
@RestController
@RequestMapping("/api/v1/previsiones")
public class PrevisionController {

    @Autowired
    private PrevisionService previsionService;

    @GetMapping
    @Operation(summary = "Obtener todas las previsiones", description = "Devuelve la lista de todas las previsiones")
    public ResponseEntity<List<Prevision>> getAll() {
        List<Prevision> list = previsionService.getAllPrevisiones();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener previsión por ID", description = "Busca una previsión específica por su identificador")
    public ResponseEntity<Prevision> getById(@PathVariable Integer id) {
        return previsionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nueva previsión", description = "Registra una nueva previsión en el sistema")
    public ResponseEntity<Prevision> create(@RequestBody Prevision prevision) {
        Prevision saved = previsionService.save(prevision);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar previsión", description = "Elimina una previsión por su ID")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        previsionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}