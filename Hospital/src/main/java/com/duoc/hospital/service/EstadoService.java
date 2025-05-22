package com.duoc.hospital.service;

import com.duoc.hospital.model.Estado;
import com.duoc.hospital.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoService{

@Autowired
private EstadoRepository estadoRepository;

/*GET, GETBYID, GUARDAR, ACTUALIZAR, BORRAR */

public List<Estado> getAllEstados() { return estadoRepository.findAll();}

public Optional<Estado> findById(int id) {return estadoRepository.findById(id);}

public Estado save(Estado estado) {return estadoRepository.save(estado);}

public void deleteById(int id) {estadoRepository.deleteById(id);}

public List<Estado> findAll() {return estadoRepository.findAll();}
}