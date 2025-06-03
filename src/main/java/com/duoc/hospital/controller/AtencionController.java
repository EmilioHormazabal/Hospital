package com.duoc.hospital.controller;

import com.duoc.hospital.model.Atencion;
import com.duoc.hospital.service.AtencionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

// Controlador para gestionar las atenciones médicas
@RestController
@RequestMapping("/api/v1/atenciones")
public class AtencionController {

    // Inyección del servicio de atenciones
    @Autowired
    private AtencionService atencionservice;

    // Formato de fecha para parsear strings a Date
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // Devuelve todas las atenciones registradas
    @GetMapping
    public ResponseEntity<List<Atencion>> listar() {
        List<Atencion> atenciones = atencionservice.findAll();
        if (atenciones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(atenciones);
    }

    // Busca una atención por su identificador único
    @GetMapping("/{id}")
    public ResponseEntity<Atencion> buscar(@PathVariable Integer id) {
        Optional<Atencion> atencion = atencionservice.findById(id);
        return atencion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Guarda una nueva atención médica
    @PostMapping
    public ResponseEntity<Atencion> guardar(@RequestBody Atencion atencion) {
        Atencion newAtencion = atencionservice.save(atencion);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAtencion);
    }

    // Busca atenciones por una fecha específica (formato yyyy-MM-dd)
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Atencion>> buscarPorFecha(@PathVariable String fecha) {
        try {
            Date fechaDate = dateFormat.parse(fecha);
            List<Atencion> atenciones = atencionservice.findByFecha(fechaDate);
            if (atenciones.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return ResponseEntity.ok(atenciones);
        } catch (ParseException e) {
            // Si la fecha no tiene el formato correcto
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Busca atenciones entre dos fechas dadas (incluye todo el día final)
    @GetMapping("/fecha")
    public ResponseEntity<List<Atencion>> buscarEntreFechas(
            @RequestParam String desde,
            @RequestParam String hasta) {
        try {
            Date desdeDate = dateFormat.parse(desde);
            Date hastaDate = dateFormat.parse(hasta);

            // Ajusta la fecha final para incluir todo el día
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(hastaDate);
            cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
            cal.set(java.util.Calendar.MINUTE, 59);
            cal.set(java.util.Calendar.SECOND, 59);
            cal.set(java.util.Calendar.MILLISECOND, 999);
            Date hastaFin = cal.getTime();

            List<Atencion> atenciones = atencionservice.findByFechaBetween(desdeDate, hastaFin);
            if (atenciones.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return ResponseEntity.ok(atenciones);
        } catch (ParseException e) {
            // Si alguna fecha no tiene el formato correcto
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}