package com.duoc.hospital.controller;

import com.duoc.hospital.model.Paciente;
import com.duoc.hospital.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @RequestMapping
    public ResponseEntity<List<Paciente>> Listar() {
        List<Paciente> pacientes = pacienteService.findAll();
        if (pacientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        };
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> Buscar(@PathVariable Integer id) {
        Optional<Paciente> paciente = pacienteService.findById(id);
        return paciente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente) {
        Paciente newPaciente = pacienteService.save(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPaciente);
    }
}
