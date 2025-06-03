package com.duoc.hospital.repository;

import com.duoc.hospital.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {

    boolean existsByNombre(String nombre);
}