package com.duoc.hospital.repository;

import com.duoc.hospital.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    Optional<Paciente> findByRun(String run);

    Optional<Paciente> findByCorreo(String correo);

    Optional<Paciente> findByTelefono(String telefono);

    List<Paciente> findByNombreAndApellido(String nombre, String apellido);

    List<Paciente> findByPrevisionNombre(String nombre);

    // Buscar pacientes menores de cierta edad
    @Query("SELECT p FROM Paciente p WHERE DATEDIFF(YEAR, p.fechaNacimiento, CURRENT_DATE) < :edad")
    List<Paciente> findMenoresDe(@Param("edad") int edad);

    // Buscar pacientes mayores de cierta edad
    @Query("SELECT p FROM Paciente p WHERE DATEDIFF(YEAR, p.fechaNacimiento, CURRENT_DATE) > :edad")
    List<Paciente> findMayoresDe(@Param("edad") int edad);

    // Métodos agregados para compatibilidad (pueden reutilizar los mismos query)
    default List<Paciente> findMenoresDeEdad(int edad) {
        return findMenoresDe(edad);
    }

    default List<Paciente> findMayoresDeEdad(int edad) {
        return findMayoresDe(edad);
    }

    @Query("SELECT DISTINCT p FROM Paciente p JOIN Atencion a ON a.paciente.id = p.id WHERE a.medico.especialidadMedico.nombre = :nombreEspecialidad")
    List<Paciente> findByEspecialidadNombre(@Param("nombreEspecialidad") String nombreEspecialidad);
}