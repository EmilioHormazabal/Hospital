package com.duoc.hospital.controller;

import com.duoc.hospital.model.Atencion;
import com.duoc.hospital.service.AtencionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/atenciones")
public class AtencionController {

    @Autowired
    private AtencionService atencionservice;

    @RequestMapping
    public ResponseEntity<List<Atencion>> Listar() {
        List<Atencion> atenciones = atencionservice.findAll();
        if (atenciones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        };
        return ResponseEntity.ok(atenciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atencion> Buscar(@PathVariable Integer id) {
        Optional<Atencion> atencion = atencionservice.findById(id);
        return atencion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Atencion> guardar(@RequestBody Atencion atencion) {
        Atencion newAtencion = atencionservice.save(atencion);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAtencion);
    }
}
