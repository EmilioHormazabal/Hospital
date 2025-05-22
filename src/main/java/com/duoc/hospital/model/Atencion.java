package com.duoc.hospital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Atencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDate fecha_atencion;

    @Column(nullable = false)
    private LocalTime hora_inicio;

    @Column(columnDefinition = "integer default 0", length = 10)
    private int costo;

    @Column(nullable = true, length = 300)
    private String comentario;

    @Column(nullable = false, length = 20)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;
}