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
    private LocalTime fecha_inicio;

    @Column(columnDefinition = "integer default 0")
    private int costo;


    private String comentario;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

}