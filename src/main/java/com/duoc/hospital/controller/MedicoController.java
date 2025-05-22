package com.duoc.hospital.controller;

import com.duoc.hospital.model.Medico;
import com.duoc.hospital.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @RequestMapping
    public ResponseEntity<List<Medico>> Listar() {
        List<Medico> medicos = medicoService.findAll();
        if (medicos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        };
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> Buscar(@PathVariable Integer id) {
        Optional<Medico> medico = medicoService.findById(id);
        return medico.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Medico> guardar(@RequestBody Medico medico) {
        Medico newMedico = medicoService.save(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMedico);
    }
    
}
