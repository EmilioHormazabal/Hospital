package com.duoc.hospital.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Medico")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un médico en el sistema")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del médico", example = "1")
    private int id;

    @Column(nullable = false, length = 12, unique = true)
    @Schema(description = "RUN del médico (formato: 12345678-9)", example = "12345678-9")
    private String run;

    @Column(nullable = false, length = 50)
    @Schema(description = "Nombre del médico", example = "Juan")
    private String nombre;

    @Column(nullable = false, length = 50)
    @Schema(description = "Apellido del médico", example = "Pérez")
    private String apellido;

    @Column(nullable = false)
    @Schema(description = "Fecha de contrato del médico", example = "2023-01-15")
    private Date fecha_contrato;

    @Column(name = "sueldo_base", nullable = false, length = 10)
    @Schema(description = "Sueldo base del médico en pesos chilenos", example = "1500000")
    private int sueldoBase;

    @Column(nullable = false, length = 100, unique = true)
    @Schema(description = "Correo electrónico del médico", example = "juan.perez@hospital.cl")
    private String correo;

    @Column(nullable = false, length = 20, unique = true)
    @Schema(description = "Teléfono del médico", example = "+56912345678")
    private String telefono;

    @OneToMany(mappedBy = "medico")
    @JsonIgnore
    @Schema(description = "Lista de atenciones realizadas por el médico")
    private List<Atencion> atenciones;

    @ManyToOne
    @JoinColumn(name = "id_especialidad", nullable = false)
    @Schema(description = "Especialidad médica del médico")
    private Especialidad especialidadMedico; // Relación correcta con Especialidad
}
