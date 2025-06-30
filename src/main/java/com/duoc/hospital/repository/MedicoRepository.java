package com.duoc.hospital.repository;

import com.duoc.hospital.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Integer> {
    List<Medico> findByNombreAndApellido(String nombre, String apellido);

    Optional<Medico> findByRun(String run);

    Optional<Medico> findByCorreo(String correo);

    Optional<Medico> findByTelefono(String telefono);

    @Query("SELECT m FROM Medico m WHERE m.fecha_contrato > :fechaLimite")
    List<Medico> findByAntiguedadMenorA(@Param("fechaLimite") Date fechaLimite);

    @Query("SELECT m FROM Medico m WHERE m.fecha_contrato <= :fechaLimite")
    List<Medico> findByAntiguedadMayorA(@Param("fechaLimite") Date fechaLimite);

    @Query("SELECT m FROM Medico m WHERE m.especialidadMedico.nombre = :nombreEspecialidad")
    List<Medico> findByEspecialidadNombre(@Param("nombreEspecialidad") String nombreEspecialidad);

    @Query(value = "SELECT * FROM Medico WHERE TIMESTAMPDIFF(YEAR, fecha_contrato, CURDATE()) = :antiguedad",
            nativeQuery = true)
    List<Medico> findByAntiguedadExacta(@Param("antiguedad") int antiguedad);

}
