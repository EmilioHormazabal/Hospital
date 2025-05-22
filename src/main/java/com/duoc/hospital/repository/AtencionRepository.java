package com.duoc.hospital.repository;

import com.duoc.hospital.model.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AtencionRepository extends JpaRepository<Atencion, Integer> {

}