package com.duoc.hospital.repository;

import com.duoc.hospital.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Integer> {
    // Buscar por nombre y apellido
    List<Medico> findByNombreAndApellido(String nombre, String apellido);

    // Buscar por run
    Optional<Medico> findByRun(String run);

    // Validaciones de unicidad
    Optional<Medico> findByCorreo(String correo);
    Optional<Medico> findByTelefono(String telefono);

    // Medicos con menos de N años de antiguedad
    @Query("SELECT m FROM Medico m WHERE m.fecha_contrato > :fechaLimite")
    List<Medico> findByAntiguedadMenorA(@Param("fechaLimite") Date fechaLimite);

    // Medicos con más de N años de antiguedad
    @Query("SELECT m FROM Medico m WHERE m.fecha_contrato <= :fechaLimite")
    List<Medico> findByAntiguedadMayorA(@Param("fechaLimite") Date fechaLimite);

    // Buscar médicos por especialidad
    @Query("SELECT m FROM Medico m WHERE m.especialidadMedico.nombre = :nombreEspecialidad")
    List<Medico> findByEspecialidadNombre(@Param("nombreEspecialidad") String nombreEspecialidad);
}

