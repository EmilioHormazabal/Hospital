package com.duoc.hospital.service;

import com.duoc.hospital.model.Atencion;
import com.duoc.hospital.repository.AtencionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtencionService {

    @Autowired
    private AtencionRepository atencionRepository;

    /*GET, GETBYID, GUARDAR, ACTUALIZAR, BORRAR */

    public List<Atencion> getAllAtenciones() {
        return atencionRepository.findAll();
    }

    public Optional<Atencion> findById(int id) {
        return atencionRepository.findById(id);
    }

    public Atencion save(Atencion atencion) {
        return atencionRepository.save(atencion);
    }

    public void deleteById(int id) {
        atencionRepository.deleteById(id);
    }

    public List<Atencion> findAll() {
        return atencionRepository.findAll();
    }
}
