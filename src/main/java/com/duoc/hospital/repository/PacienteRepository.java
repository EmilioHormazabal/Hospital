package com.duoc.hospital.repository;

import com.duoc.hospital.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    Optional<Paciente> findByRun(String run);

    Optional<Paciente> findByCorreo(String correo);

    Optional<Paciente> findByTelefono(String telefono);

    List<Paciente> findByNombreAndApellido(String nombre, String apellido);

    List<Paciente> findByPrevisionNombre(String nombre);

    // Buscar pacientes menores de cierta edad (fechaNacimiento > fechaLimite)
    @Query("SELECT p FROM Paciente p WHERE p.fechaNacimiento > :fechaLimite")
    List<Paciente> findMenoresDe(@Param("fechaLimite") Date fechaLimite);

    // Buscar pacientes mayores de cierta edad (fechaNacimiento < fechaLimite)
    @Query("SELECT p FROM Paciente p WHERE p.fechaNacimiento < :fechaLimite")
    List<Paciente> findMayoresDe(@Param("fechaLimite") Date fechaLimite);

    // Métodos agregados para compatibilidad (debes calcular fechaLimite en el Service)
    @Query("SELECT DISTINCT p FROM Paciente p JOIN Atencion a ON a.paciente.id = p.id WHERE a.medico.especialidadMedico.nombre = :nombreEspecialidad")
    List<Paciente> findByEspecialidadNombre(@Param("nombreEspecialidad") String nombreEspecialidad);
}