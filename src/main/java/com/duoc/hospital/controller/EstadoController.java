package com.duoc.hospital.controller;

import com.duoc.hospital.model.Estado;
import com.duoc.hospital.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/v1/Estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoservice;

    @RequestMapping
    public ResponseEntity<List<Estado>> Listar() {
        List<Estado> estados = estadoservice.findAll();
        if (estados.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(estados);
    }

    @GetMapping("/api/v1/Estados{id}")
    public ResponseEntity<Estado> Buscar(@PathVariable Integer id) {
        Optional<Estado> estado = estadoservice.findById(id);
        return estado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Estado> guardar(@RequestBody Estado estado) {
        Estado newEstado = estadoservice.save(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEstado);
    }
}
