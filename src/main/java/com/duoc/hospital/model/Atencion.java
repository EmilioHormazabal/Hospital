package com.duoc.hospital.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "Entidad que representa una atención médica en el sistema",
        name = "Atencion"
)
public class Atencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "ID único de la atención",
            example = "1"
    )
    private int id;

    @Column(name = "fecha_atencion", nullable = false)
    @Schema(
            description = "Fecha de la atención médica",
            example = "2025-06-15"
    )
    private Date fechaAtencion;

    @Column(name = "hora_inicio", nullable = false)
    @Schema(
            description = "Hora de inicio de la atención",
            example = "09:30:00"
    )
    private LocalTime horaInicio;

    @Column(columnDefinition = "integer default 0", length = 10)
    @Schema(
            description = "Costo de la atención en pesos chilenos",
            example = "50000"
    )
    private int costo;

    @Column(nullable = true, length = 300)
    @Schema(
            description = "Comentarios adicionales sobre la atención",
            example = "Paciente con síntomas de resfriado común"
    )
    private String comentario;

    @Column(nullable = false, length = 20)
    @Schema(
            description = "Estado actual de la atención (PENDIENTE, REALIZADA, CANCELADA, PAGADA)",
            example = "PAGADA"
    )
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    @Schema(
            description = "Paciente asociado a la atención"
    )
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico")
    @Schema(
            description = "Médico responsable de la atención"
    )
    private Medico medico;
}
