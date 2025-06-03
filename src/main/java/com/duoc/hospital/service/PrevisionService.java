package com.duoc.hospital.service;

import com.duoc.hospital.model.Prevision;
import com.duoc.hospital.repository.PrevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrevisionService {
    @Autowired
    private PrevisionRepository previsionRepository;

    /*GET, GETBYID, GUARDAR, ACTUALIZAR, BORRAR */

    public List<Prevision> getAllPrevisiones() { return previsionRepository.findAll(); }

    public Optional<Prevision> findById(int id) { return previsionRepository.findById(id); }

    public Prevision save(Prevision prevision) { return previsionRepository.save(prevision); }

    public void deleteById(int id) { previsionRepository.deleteById(id); }

    public List<Prevision> findAll() { return previsionRepository.findAll(); }

    // Nuevo método para verificar si una previsión existe por su nombre
    public boolean existsByNombre(String nombre) {return previsionRepository.existsByNombre(nombre);}
}