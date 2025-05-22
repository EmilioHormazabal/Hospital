package com.duoc.hospital.model;

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

    @Column(unique = true, length = 12, nullable = false)
    private String run;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false)
    private Date fechaNacimiento;

    @Column(nullable = false,length = 100)
    private String correo;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false, length = 20)
    private String prevision;

    @OneToMany(mappedBy = "paciente")
    private List<Atencion> atenciones;

}