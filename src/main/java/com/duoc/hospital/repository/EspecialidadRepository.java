package com.duoc.hospital.repository;

import com.duoc.hospital.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Integer> {
    boolean existsByNombre(String nombre);
    Optional<Especialidad> findByNombre(String nombre);
}