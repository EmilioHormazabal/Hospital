package com.duoc.hospital.service;

import com.duoc.hospital.model.Medico;
import com.duoc.hospital.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    /*GET, GETBYID, GUARDAR, ACTUALIZAR, BORRAR */

    public List<Medico> getAllMedicos() {
        return medicoRepository.findAll();
    }

    public Optional<Medico> findById(int id) {
        return medicoRepository.findById(id);
    }

    public Medico save(Medico medico) {
        return medicoRepository.save(medico);
    }

    public void deleteById(int id) {
        medicoRepository.deleteById(id);
    }

    public List<Medico> findAll() {
        return medicoRepository.findAll();
    }
}
