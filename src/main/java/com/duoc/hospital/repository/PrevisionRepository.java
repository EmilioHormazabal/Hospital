package com.duoc.hospital.repository;

import com.duoc.hospital.model.Prevision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrevisionRepository extends JpaRepository<Prevision, Integer> {
    Optional<Prevision> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}