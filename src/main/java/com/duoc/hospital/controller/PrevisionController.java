package com.duoc.hospital.controller;

import com.duoc.hospital.model.Prevision;
import com.duoc.hospital.service.PrevisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/previsiones")
public class PrevisionController {

    @Autowired
    private PrevisionService previsionService;

    @RequestMapping
    public ResponseEntity<List<Prevision>> Listar() {
        List<Prevision> previsiones = previsionService.findAll();
        if (previsiones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(previsiones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prevision> Buscar(@PathVariable Integer id) {
        Optional<Prevision> prevision = previsionService.findById(id);
        return prevision.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Prevision> guardar(@RequestBody Prevision prevision) {
        Prevision newPrevision = previsionService.save(prevision);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPrevision);
    }
}