package com.duoc.hospital.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "prevision")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Prevision {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String cobertura;

    @OneToMany(mappedBy = "prevision")
    @JsonIgnore
    private List<Paciente> pacientes;
}