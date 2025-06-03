package com.duoc.hospital.controller;

import com.duoc.hospital.model.Especialidad;
import com.duoc.hospital.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Controlador REST para especialidades médicas
@RestController
@RequestMapping("/api/v1/especialidades")
public class EspecialidadController {

    // Servicio para manejar la lógica de especialidades
    @Autowired
    private EspecialidadService especialidadservice;

    // Lista todas las especialidades
    @RequestMapping
    public ResponseEntity<List<Especialidad>> Listar() {
        List<Especialidad> especialidades = especialidadservice.findAll();
        if (especialidades.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(especialidades);
    }

    // Busca una especialidad por su ID
    @GetMapping("/api/v1/especialidades{id}")
    public ResponseEntity<Especialidad> Buscar(@PathVariable Integer id) {
        Optional<Especialidad> especialidad = especialidadservice.findById(id);
        return especialidad.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Guarda una nueva especialidad
    @PostMapping
    public ResponseEntity<Especialidad> guardar(@RequestBody Especialidad especialidad) {
        Especialidad newEspecialidad = especialidadservice.save(especialidad);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEspecialidad);
    }
}