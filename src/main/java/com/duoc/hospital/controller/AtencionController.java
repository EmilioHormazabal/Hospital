// src/main/java/com/duoc/hospital/controller/AtencionController.java
package com.duoc.hospital.controller;

import com.duoc.hospital.model.Atencion;
import com.duoc.hospital.service.AtencionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/atenciones")
@Tag(name = "Atenciones", description = "Operaciones relacionadas con las atenciones medicas")
public class AtencionController {

    @Autowired
    private AtencionService atencionService;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping
    @Operation(summary = "Obtener todas las atenciones", description = "Devuelve la lista de todas las atenciones registradas")
    public ResponseEntity<List<Atencion>> getAll() {
        List<Atencion> atenciones = atencionService.findAll();
        if (atenciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(atenciones);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener atención por ID", description = "Devuelve la atención especificada por su identificador")
    public ResponseEntity<Optional<Atencion>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(atencionService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crear nueva atención", description = "Registra una nueva atención médica en el sistema")
    public ResponseEntity<Atencion> create(@RequestBody Atencion atencion) {
        Atencion saved = atencionService.save(atencion);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/fecha/{fecha}")
    @Operation(summary = "Buscar atenciones por fecha", description = "Obtiene atenciones de una fecha específica (yyyy-MM-dd)")
    public ResponseEntity<List<Atencion>> findByFecha(@PathVariable String fecha) {
        try {
            Date date = dateFormat.parse(fecha);
            List<Atencion> result = atencionService.findByFecha(date);
            if (result.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(result);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/fecha")
    @Operation(summary = "Buscar atenciones entre fechas", description = "Obtiene atenciones entre dos fechas dadas (yyyy-MM-dd)")
    public ResponseEntity<List<Atencion>> findBetween(@RequestParam String desde, @RequestParam String hasta) {
        try {
            Date d1 = dateFormat.parse(desde);
            Date d2 = dateFormat.parse(hasta);
            List<Atencion> result = atencionService.findByFechaBetween(d1, d2);
            if (result.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(result);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}