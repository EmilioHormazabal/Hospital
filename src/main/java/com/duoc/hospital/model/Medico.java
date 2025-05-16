package com.duoc.hospital.model;

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

public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    @Column(nullable = true, length = 12)
    private String run;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false)
    private Date fecha_contrato;

    @Column(nullable = false, length = 10)
    private int sueldo_base;

    @Column(nullable = false, length = 100, unique = true)
    private String correo;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = true, length = 20)
    private String especialidad;

    @OneToMany (mappedBy = "medico")
    private List<Atencion> atenciones;

}
