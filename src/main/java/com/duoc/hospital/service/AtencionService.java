package com.duoc.hospital.service;

import com.duoc.hospital.model.Atencion;
import com.duoc.hospital.model.Medico;
import com.duoc.hospital.model.Paciente;
import com.duoc.hospital.repository.AtencionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AtencionService {

    @Autowired
    private AtencionRepository atencionRepository;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    public List<Atencion> getAllAtenciones() {
        return atencionRepository.findAll();
    }

    public Optional<Atencion> findById(int id) {
        return atencionRepository.findById(id);
    }

    public Atencion save(Atencion atencion) {
        if (atencion.getEstado() == null || atencion.getEstado().isEmpty()) {
            atencion.setEstado("Pendiente");
        }

        // Validar y cargar médico completo
        if (atencion.getMedico() == null || atencion.getMedico().getId() == 0) {
            throw new IllegalArgumentException("Debe especificar un médico válido.");
        }
        Medico medico = medicoService.findById(atencion.getMedico().getId())
                .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado."));
        atencion.setMedico(medico);

        // Validar y cargar paciente completo
        if (atencion.getPaciente() == null || atencion.getPaciente().getId() == 0) {
            throw new IllegalArgumentException("Debe especificar un paciente válido.");
        }
        Paciente paciente = pacienteService.findById(atencion.getPaciente().getId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado."));
        atencion.setPaciente(paciente);

        return atencionRepository.save(atencion);
    }

    public void deleteById(int id) {
        atencionRepository.deleteById(id);
    }

    public List<Atencion> findAll() {
        return atencionRepository.findAll();
    }

    // Buscar atenciones por fecha exacta (en UTC)
    public List<Atencion> findByFecha(Date fecha) {
        java.util.Calendar cal = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("UTC"));
        cal.setTime(fecha);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        Date inicio = cal.getTime();

        cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
        Date fin = cal.getTime();

        return atencionRepository.findByFechaEnDia(inicio, fin);
    }

    // Conversión de fechas de Chile a UTC antes de consultar (así no hay desfases)
    public List<Atencion> findByFechaBetween(Date desde, Date hasta) {
        ZoneId chileZone = ZoneId.of("America/Santiago");

        // Convertir 'desde' a UTC (inicio del día en Chile)
        Instant desdeUtc = LocalDateTime.ofInstant(desde.toInstant(), chileZone)
                .atZone(chileZone)
                .withHour(0).withMinute(0).withSecond(0).withNano(0)
                .toInstant();

        // Convertir 'hasta' a UTC (fin del día en Chile)
        Instant hastaUtc = LocalDateTime.ofInstant(hasta.toInstant(), chileZone)
                .atZone(chileZone)
                .withHour(23).withMinute(59).withSecond(59).withNano(999_000_000)
                .toInstant();

        Date desdeDateUtc = Date.from(desdeUtc);
        Date hastaDateUtc = Date.from(hastaUtc);

        return atencionRepository.findBetweenFechas(desdeDateUtc, hastaDateUtc);
    }

    public List<Atencion> findByCostoMenorA(int costo) {
        return atencionRepository.findByCostoLessThan(costo);
    }

    public List<Atencion> findByCostoMayorA(int costo) {
        return atencionRepository.findByCostoGreaterThan(costo);
    }

    public List<Atencion> findByMedicoId(int idMedico) {
        return atencionRepository.findByMedicoId(idMedico);
    }

    public List<Atencion> findByPacienteId(int idPaciente) {
        return atencionRepository.findByPacienteId(idPaciente);
    }

    public List<Atencion> findByEstado(String estado) {
        return atencionRepository.findByEstado(estado);
    }

    public int calcularGananciaTotalAlta() {
        List<Atencion> atenciones = atencionRepository.findByEstado("Alta");
        return atenciones.stream().mapToInt(Atencion::getCosto).sum();
    }

    public int calcularCostoTotalPaciente(int idPaciente) {
        List<Atencion> atenciones = atencionRepository.findAtencionesByPacienteId(idPaciente);
        return (int) Math.round(atenciones.stream()
                .mapToDouble(a -> {
                    double cobertura = 0.0;
                    try {
                        cobertura = Double.parseDouble(a.getPaciente().getPrevision().getCobertura().replace("%", "")) / 100.0;
                    } catch (Exception e) {
                        cobertura = 0.0;
                    }
                    return a.getCosto() * (1 - cobertura);
                })
                .sum());
    }
    // Agrega este método a tu clase AtencionService
    public Optional<Atencion> update(int id, Atencion atencionActualizada) {
        return atencionRepository.findById(id)
                .map(atencionExistente -> {
                    atencionActualizada.setId(id);
                    // Aquí puedes añadir la lógica para cargar Medico y Paciente si es necesario
                    return atencionRepository.save(atencionActualizada);
                });
    }
}