package com.duoc.hospital.controller;

import com.duoc.hospital.model.Atencion;
import com.duoc.hospital.service.AtencionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Atenciones", description = "Operaciones relacionadas con las atenciones médicas")
public class AtencionController {

    @Autowired
    private AtencionService atencionService;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping
    @Operation(summary = "Obtener todas las atenciones", description = "Devuelve la lista de todas las atenciones registradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de atenciones obtenida exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay atenciones registradas")
    })
    public ResponseEntity<List<Atencion>> getAll() {
        List<Atencion> atenciones = atencionService.findAll();
        if (atenciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(atenciones);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener atención por ID", description = "Devuelve la atención especificada por su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atención obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "Atención no encontrada")
    })
    public ResponseEntity<Optional<Atencion>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(atencionService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crear nueva atención", description = "Registra una nueva atención médica en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Atención creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para la creación de la atención")
    })
    public ResponseEntity<Atencion> create(@RequestBody Atencion atencion) {
        Atencion saved = atencionService.save(atencion);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/fecha/{fecha}")
    @Operation(summary = "Buscar atenciones por fecha", description = "Obtiene atenciones de una fecha específica (yyyy-MM-dd)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atenciones obtenidas exitosamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron atenciones para la fecha especificada"),
            @ApiResponse(responseCode = "400", description = "Formato de fecha inválido")
    })
    public ResponseEntity<List<Atencion>> findByFecha(
            @Parameter(
                    name = "fecha",
                    description = "Fecha en formato yyyy-MM-dd",
                    example = "2025-06-01",
                    required = true
            )
            @PathVariable String fecha) {
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atenciones obtenidas exitosamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron atenciones en el rango de fechas especificado"),
            @ApiResponse(responseCode = "400", description = "Formato de fecha inválido")
    })
    public ResponseEntity<List<Atencion>> findBetween(
            @Parameter(
                    name = "desde",
                    description = "Fecha de inicio (yyyy-MM-dd)",
                    example = "2025-06-01",
                    required = true
            )
            @RequestParam String desde,
            @Parameter(
                    name = "hasta",
                    description = "Fecha de fin (yyyy-MM-dd)",
                    example = "2025-06-30",
                    required = true
            )
            @RequestParam String hasta) {
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

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar atención", description = "Actualiza los datos de una atención médica existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atención actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Atención no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Atencion> update(@PathVariable Integer id, @RequestBody Atencion atencionActualizada) {
        Optional<Atencion> updated = atencionService.update(id, atencionActualizada);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar atención", description = "Elimina una atención médica por su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atención eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Atención no encontrada")
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (atencionService.findById(id).isPresent()) {
            atencionService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
