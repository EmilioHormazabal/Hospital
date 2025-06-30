package com.duoc.hospital.service;

import com.duoc.hospital.model.Medico;
import com.duoc.hospital.model.Especialidad;
import com.duoc.hospital.repository.MedicoRepository;
import com.duoc.hospital.repository.EspecialidadRepository;
import com.duoc.hospital.repository.AtencionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private AtencionRepository atencionRepository;

    public List<Medico> getAllMedicos() {
        return medicoRepository.findAll();
    }

    public Optional<Medico> findById(int id) {
        return medicoRepository.findById(id);
    }

    public Medico save(Medico medico) {
        // Validar campos obligatorios (NN)
        if (medico.getRun() == null || medico.getRun().isEmpty()
                || medico.getCorreo() == null || medico.getCorreo().isEmpty()
                || medico.getTelefono() == null || medico.getTelefono().isEmpty()
                || medico.getNombre() == null || medico.getNombre().isEmpty()
                || medico.getApellido() == null || medico.getApellido().isEmpty()) {
            throw new IllegalArgumentException("Todos los campos obligatorios deben estar completos.");
        }

        // Validar unicidad solo si es nuevo (id == 0)
        if (medico.getId() == 0) {
            if (medicoRepository.findByRun(medico.getRun()).isPresent()) {
                throw new IllegalArgumentException("El run ya está registrado para otro médico.");
            }
            if (medicoRepository.findByCorreo(medico.getCorreo()).isPresent()) {
                throw new IllegalArgumentException("El correo ya está registrado para otro médico.");
            }
            if (medicoRepository.findByTelefono(medico.getTelefono()).isPresent()) {
                throw new IllegalArgumentException("El teléfono ya está registrado para otro médico.");
            }
        }

        // Asignar especialidad por defecto si no se especifica
        if (medico.getEspecialidadMedico() == null) {
            Especialidad especialidad = especialidadRepository.findByNombre("MEDICINA GENERAL")
                    .orElseThrow(() -> new RuntimeException("Especialidad 'MEDICINA GENERAL' no encontrada"));
            medico.setEspecialidadMedico(especialidad);
        } else {
            Especialidad especialidad = especialidadRepository.findById(medico.getEspecialidadMedico().getId())
                    .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
            medico.setEspecialidadMedico(especialidad);
        }
        return medicoRepository.save(medico);
    }

    public void deleteById(int id) {
        medicoRepository.deleteById(id);
    }

    public List<Medico> findAll() {
        return medicoRepository.findAll();
    }

    public List<Medico> findByNombreAndApellido(String nombre, String apellido) {
        return medicoRepository.findByNombreAndApellido(nombre, apellido);
    }

    public Optional<Medico> findByRun(String run) {
        return medicoRepository.findByRun(run);
    }

    // Calcula el sueldo total: sueldo base + 20% de atenciones
    public int calcularSueldoTotal(int id) {
        if (!medicoRepository.existsById(id)) {
            throw new IllegalArgumentException("El médico con ID " + id + " no existe.");
        }
        Double sueldo = atencionRepository.calcularSueldoTotalMedico(id);
        return sueldo != null ? (int) Math.round(sueldo) : 0;
    }

    // Médicos con menos de N años de antigüedad
    public List<Medico> findMenoresAntiguedad(int anios) {
        if (anios < 0) throw new IllegalArgumentException("Años no puede ser negativo.");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -anios);
        Date fechaLimite = cal.getTime();
        return medicoRepository.findByAntiguedadMenorA(fechaLimite);
    }

    // Médicos con más de N años de antigüedad
    public List<Medico> findMayoresAntiguedad(int anios) {
        if (anios < 0) throw new IllegalArgumentException("Años no puede ser negativo.");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -anios);
        Date fechaLimite = cal.getTime();
        return medicoRepository.findByAntiguedadMayorA(fechaLimite);
    }

    public List<Medico> findByEspecialidad(String nombreEspecialidad) {
        return medicoRepository.findByEspecialidadNombre(nombreEspecialidad);
    }
    public Optional<Medico> update(int id, Medico medicoActualizado) {
        return medicoRepository.findById(id)
                .map(medicoExistente -> {
                    // Valida que los datos únicos no se repitan con OTROS médicos
                    medicoRepository.findByRun(medicoActualizado.getRun()).ifPresent(m -> {
                        if (m.getId() != id) throw new IllegalArgumentException("El RUN ya está en uso por otro médico.");
                    });
                    medicoRepository.findByCorreo(medicoActualizado.getCorreo()).ifPresent(m -> {
                        if (m.getId() != id) throw new IllegalArgumentException("El correo ya está en uso por otro médico.");
                    });

                    medicoActualizado.setId(id);
                    return medicoRepository.save(medicoActualizado);
                });
    }

}