package com.duoc.hospital.service;

import com.duoc.hospital.model.Paciente;
import com.duoc.hospital.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    /*GET, GETBYID, GUARDAR, ACTUALIZAR, BORRAR */

    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> findById(int id) {
        return pacienteRepository.findById(id);
    }

    public Paciente save(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public void deleteById(int id) {
        pacienteRepository.deleteById(id);
    }

    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }
}
