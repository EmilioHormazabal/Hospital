package com.duoc.hospital.config;

import com.duoc.hospital.model.Especialidad;
import com.duoc.hospital.model.Estado;
import com.duoc.hospital.model.Prevision;
import com.duoc.hospital.repository.EspecialidadRepository;
import com.duoc.hospital.repository.EstadoRepository;
import com.duoc.hospital.repository.PrevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Componente que inicializa datos al iniciar la app
@Component
public class DataInitializar implements CommandLineRunner {

    // Repositorio para especialidades
    @Autowired
    private EspecialidadRepository especialidadRepository;

    // Repositorio para previsiones
    @Autowired
    private PrevisionRepository previsionRepository;

    // Repositorio para estados
    @Autowired
    private EstadoRepository estadoRepository;

    // Método que se ejecuta al iniciar la aplicación
    @Override
    public void run(String... args) throws Exception {
        // Si no existe la especialidad base, la crea
        if (!especialidadRepository.existsByNombre("MEDICINA GENERAL")) {
            especialidadRepository.save(new Especialidad(0, "MEDICINA GENERAL", "Medicina General", null));
        }

        // Si no existe la prevision FONASA, la crea
        if (!previsionRepository.existsByNombre("FONASA")) {
            previsionRepository.save(new Prevision(0, "FONASA", "50%", null));
        }
        // Si no existe la prevision ISAPRE, la crea
        if (!previsionRepository.existsByNombre("ISAPRE")) {
            previsionRepository.save(new Prevision(0, "ISAPRE", "60%", null));
        }

        // Si no existe el estado Alta, lo crea
        if (!estadoRepository.existsByNombre("Alta")) {
            estadoRepository.save(new Estado(0, "Alta", "Paciente en estado libre", null));
        }
        // Si no existe el estado Pendiente, lo crea
        if (!estadoRepository.existsByNombre("Pendiente")) {
            estadoRepository.save(new Estado(0, "Pendiente", "Paciente a espera de ser atendido", null));
        }
        // Si no existe el estado Hospitalizado, lo crea
        if (!estadoRepository.existsByNombre("Hospitalizado")) {
            estadoRepository.save(new Estado(0, "Hospitalizado", "Paciente hospitalizado", null));
        }
    }
}