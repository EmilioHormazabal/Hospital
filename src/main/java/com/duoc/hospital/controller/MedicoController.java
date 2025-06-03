package com.duoc.hospital.controller;

import com.duoc.hospital.model.Medico;
import com.duoc.hospital.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public ResponseEntity<List<Medico>> listar() {
        List<Medico> medicos = medicoService.findAll();
        if (medicos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> buscar(@PathVariable Integer id) {
        Optional<Medico> medico = medicoService.findById(id);
        return medico.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Medico> guardar(@RequestBody Medico medico) {
        Medico newMedico = medicoService.save(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMedico);
    }

    // Buscar por nombre y apellido
    @GetMapping("/buscar")
    public ResponseEntity<List<Medico>> buscarPorNombreApellido(
            @RequestParam String nombre,
            @RequestParam String apellido) {
        List<Medico> medicos = medicoService.findByNombreAndApellido(nombre, apellido);
        if (medicos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(medicos);
    }

    // Buscar por run
    @GetMapping("/run/{run}")
    public ResponseEntity<Medico> buscarPorRun(@PathVariable String run) {
        Optional<Medico> medico = medicoService.findByRun(run);
        return medico.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Médicos con menos de N años de antigüedad
    @GetMapping("/antiguedad/menor/{anios}")
    public ResponseEntity<List<Medico>> menoresAntiguedad(@PathVariable int anios) {
        List<Medico> medicos = medicoService.findMenoresAntiguedad(anios);
        if (medicos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(medicos);
    }

    // Médicos con más de N años de antigüedad
    @GetMapping("/antiguedad/mayor/{anios}")
    public ResponseEntity<List<Medico>> mayoresAntiguedad(@PathVariable int anios) {
        List<Medico> medicos = medicoService.findMayoresAntiguedad(anios);
        if (medicos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(medicos);
    }

    // Sueldo total de un médico
    @GetMapping("/{id}/sueldototal")
    public ResponseEntity<?> sueldoTotal(@PathVariable int id) {
        try {
            int sueldo = medicoService.calcularSueldoTotal(id);
            return ResponseEntity.ok(sueldo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    // Utilidad para calcular la fecha límite según años de antigüedad
    private Date calcularFechaLimite(int anios) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -anios);
        return cal.getTime();
    }

    // Actualizar un médico
    @PutMapping("/{id}")
    public ResponseEntity<Medico> actualizar(@PathVariable int id, @RequestBody Medico medico) {
        Optional<Medico> medicoExistente = medicoService.findById(id);
        if (medicoExistente.isPresent()) {
            medico.setId(id); // Asegurar que se actualice el médico con el ID correcto
            Medico medicoActualizado = medicoService.save(medico);
            return ResponseEntity.ok(medicoActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar un médico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        Optional<Medico> medicoExistente = medicoService.findById(id);
        if (medicoExistente.isPresent()) {
            medicoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/especialidad/{nombreEspecialidad}")
    public ResponseEntity<List<Medico>> buscarPorEspecialidad(@PathVariable String nombreEspecialidad) {
        List<Medico> medicos = medicoService.findByEspecialidad(nombreEspecialidad);
        if (medicos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(medicos);
    }
}