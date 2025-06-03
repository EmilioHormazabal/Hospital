package com.duoc.hospital.service;

import com.duoc.hospital.model.Paciente;
import com.duoc.hospital.model.Prevision;
import com.duoc.hospital.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PrevisionService previsionService;

    // Obtener todos los pacientes
    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    // Buscar paciente por ID
    public Optional<Paciente> findById(int id) {
        return pacienteRepository.findById(id);
    }

    // Guardar paciente con validaciones de negocio
    public Paciente save(Paciente paciente) {
        // Validar campos obligatorios (NN)
        if (paciente.getRun() == null || paciente.getRun().isEmpty()
                || paciente.getCorreo() == null || paciente.getCorreo().isEmpty()
                || paciente.getTelefono() == null || paciente.getTelefono().isEmpty()
                || paciente.getNombre() == null || paciente.getNombre().isEmpty()
                || paciente.getApellido() == null || paciente.getApellido().isEmpty()
                || paciente.getFechaNacimiento() == null
                || paciente.getPrevision() == null) {
            throw new IllegalArgumentException("Todos los campos obligatorios deben estar completos.");
        }

        // Validar unicidad
        if (pacienteRepository.findByRun(paciente.getRun()).isPresent()) {
            throw new IllegalArgumentException("El run ya está registrado para otro paciente.");
        }
        if (pacienteRepository.findByCorreo(paciente.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado para otro paciente.");
        }
        if (pacienteRepository.findByTelefono(paciente.getTelefono()).isPresent()) {
            throw new IllegalArgumentException("El teléfono ya está registrado para otro paciente.");
        }

        // Validar previsión solo FONASA o ISAPRE
        Prevision prevision = previsionService.findById(paciente.getPrevision().getId())
                .orElseThrow(() -> new IllegalArgumentException("Previsión no encontrada"));
        String nombrePrevision = prevision.getNombre();
        if (!nombrePrevision.equalsIgnoreCase("FONASA") && !nombrePrevision.equalsIgnoreCase("ISAPRE")) {
            throw new IllegalArgumentException("La previsión solo puede ser FONASA o ISAPRE.");
        }
        paciente.setPrevision(prevision);

        return pacienteRepository.save(paciente);
    }

    // Eliminar paciente por ID
    public void deleteById(int id) {
        pacienteRepository.deleteById(id);
    }

    // Buscar pacientes por nombre y apellido
    public List<Paciente> findByNombreAndApellido(String nombre, String apellido) {
        return pacienteRepository.findByNombreAndApellido(nombre, apellido);
    }

    // Buscar pacientes por previsión
    public List<Paciente> findByPrevisionNombre(String nombre) {
        return pacienteRepository.findByPrevisionNombre(nombre);
    }

    // Buscar paciente por run
    public Optional<Paciente> findByRun(String run) {
        return pacienteRepository.findByRun(run);
    }

    // Buscar pacientes menores de cierta edad (nombre original)
    public List<Paciente> findMenoresDe(int edad) {
        return pacienteRepository.findMenoresDe(edad);
    }

    // Buscar pacientes mayores de cierta edad (nombre original)
    public List<Paciente> findMayoresDe(int edad) {
        return pacienteRepository.findMayoresDe(edad);
    }

    // Métodos agregados para compatibilidad con el controller
    public List<Paciente> findMenoresDeEdad(int edad) {
        return pacienteRepository.findMenoresDe(edad);
    }

    public List<Paciente> findMayoresDeEdad(int edad) {
        return pacienteRepository.findMayoresDe(edad);
    }

    // Buscar pacientes por especialidad
    public List<Paciente> findByEspecialidadNombre(String nombreEspecialidad) {
        return pacienteRepository.findByEspecialidadNombre(nombreEspecialidad);
    }
}