package com.duoc.hospital.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "paciente")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un paciente en el sistema")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del paciente", example = "1")
    private int id;

    @Column(nullable = false, unique = true, length = 12)
    @Schema(description = "RUN del paciente (formato: 12345678-9)", example = "12345678-9")
    private String run;

    @Column(nullable = false, length = 50)
    @Schema(description = "Nombre del paciente", example = "Juan")
    private String nombre;

    @Column(nullable = false, length = 50)
    @Schema(description = "Apellido del paciente", example = "Pérez")
    private String apellido;

    @Column(nullable = false)
    @Schema(description = "Fecha de nacimiento del paciente", example = "1990-01-01")
    private Date fechaNacimiento;

    @Column(unique = true, nullable = false,length = 100)
    @Schema(description = "Correo electrónico del paciente", example = "juan.perez@example.com")
    private String correo;

    @Column(unique = true, nullable = false, length = 20)
    @Schema(description = "Teléfono del paciente", example = "+56987654321")
    private String telefono;

    @OneToMany(mappedBy = "paciente")
    @JsonIgnore
    @Schema(description = "Lista de atenciones asociadas al paciente")
    private List<Atencion> atenciones;

    @ManyToOne
    @JoinColumn(name = "prevision", nullable = false)
    @Schema(description = "Previsión de salud del paciente")
    private Prevision prevision;

    @Column(name = "deuda", nullable = false, columnDefinition = "int default 0")
    @Schema(description = "Deuda acumulada del paciente en pesos chilenos", example = "75000")
    private int deuda;
}
