package com.duoc.hospital.repository;

import com.duoc.hospital.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MedicoRepository extends JpaRepository<Medico, Integer> {

}
