// src/main/java/com/duoc/hospital/controller/PacienteController.java
package com.duoc.hospital.controller;

import com.duoc.hospital.model.Paciente;
import com.duoc.hospital.model.Prevision;
import com.duoc.hospital.service.PacienteService;
import com.duoc.hospital.service.PrevisionService;
import com.duoc.hospital.service.AtencionService;
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

    @Autowired
    private PrevisionService previsionService;

    @Autowired
    private AtencionService atencionService;

    @RequestMapping
    public ResponseEntity<List<Paciente>> Listar() {
        List<Paciente> pacientes = pacienteService.getAllPacientes();
        if (pacientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> Buscar(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Optional<Paciente> paciente = pacienteService.findById(id);
        return paciente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/run/{run}")
    public ResponseEntity<Paciente> buscarPorRun(@PathVariable String run) {
        if (run == null || run.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Optional<Paciente> paciente = pacienteService.findByRun(run);
        return paciente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Paciente paciente) {
        try {
            if (paciente.getPrevision() != null && paciente.getPrevision().getId() != 0) {
                Prevision prevision = previsionService.findById(paciente.getPrevision().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Previsión no encontrada"));
                paciente.setPrevision(prevision);
            } else {
                throw new IllegalArgumentException("Debe especificar una prevision válida.");
            }
            Paciente newPaciente = pacienteService.save(paciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPaciente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Paciente paciente) {
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID inválido");
        }
        Optional<Paciente> existente = pacienteService.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        try {
            if (paciente.getPrevision() != null && paciente.getPrevision().getId() != 0) {
                Prevision prevision = previsionService.findById(paciente.getPrevision().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Previsión no encontrada"));
                paciente.setPrevision(prevision);
            } else {
                throw new IllegalArgumentException("Debe especificar una prevision válida.");
            }
            paciente.setId(id);
            Paciente actualizado = pacienteService.save(paciente);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        Optional<Paciente> pacienteExistente = pacienteService.findById(id);
        if (pacienteExistente.isPresent()) {
            pacienteService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/paciente/buscar")
    public ResponseEntity<List<Paciente>> BuscarNombreyApellido(@RequestParam String nombre, @RequestParam String apellido) {
        if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Paciente> pacientes = pacienteService.findByNombreAndApellido(nombre, apellido);
        if (pacientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/prevision/{prevision}")
    public ResponseEntity<List<Paciente>> BuscarPorPrevision(@PathVariable String prevision) {
        if (prevision == null || prevision.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Paciente> pacientes = pacienteService.findByPrevisionNombre(prevision);
        if (pacientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/especialidad/{nombreEspecialidad}")
    public ResponseEntity<List<Paciente>> buscarPorEspecialidad(@PathVariable String nombreEspecialidad) {
        if (nombreEspecialidad == null || nombreEspecialidad.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Paciente> pacientes = pacienteService.findByEspecialidadNombre(nombreEspecialidad);
        if (pacientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/menores/{edad}")
    public ResponseEntity<List<Paciente>> menoresDeEdad(@PathVariable int edad) {
        if (edad < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Paciente> pacientes = pacienteService.findMenoresDeEdad(edad);
        if (pacientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/mayores/{edad}")
    public ResponseEntity<List<Paciente>> mayoresDeEdad(@PathVariable int edad) {
        if (edad < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Paciente> pacientes = pacienteService.findMayoresDeEdad(edad);
        if (pacientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}/costototal")
    public ResponseEntity<Integer> costoTotalPaciente(@PathVariable int id) {
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        int costo = atencionService.calcularCostoTotalPaciente(id);
        return ResponseEntity.ok(costo);
    }
}