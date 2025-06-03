package com.duoc.hospital.service;

import com.duoc.hospital.model.Especialidad;
import com.duoc.hospital.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    /*GET, GETBYID, GUARDAR, ACTUALIZAR, BORRAR */

    public List<Especialidad> getAllEspecialidades() { return especialidadRepository.findAll(); }

    public Optional<Especialidad> findById(int id) { return especialidadRepository.findById(id); }

    public Especialidad save(Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    public void deleteById(int id) {
        especialidadRepository.deleteById(id);
    }

    public List<Especialidad> findAll() {
        return especialidadRepository.findAll();
    }
    
}