package com.duoc.hospital.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, length = 12)
    private String run;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false)
    private Date fechaNacimiento;

    @Column(unique = true, nullable = false,length = 100)
    private String correo;

    @Column(unique = true, nullable = false, length = 20)
    private String telefono;

    @OneToMany(mappedBy = "paciente")
    @JsonIgnore
    private List<Atencion> atenciones;

    @ManyToOne
    @JoinColumn(name = "prevision", nullable = false)
    private Prevision prevision;
}