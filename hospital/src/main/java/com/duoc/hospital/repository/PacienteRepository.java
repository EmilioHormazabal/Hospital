package com.duoc.hospital.repository;

import com.duoc.hospital.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

}
