package com.duoc.hospital;

import com.duoc.hospital.controller.PacienteController;
import com.duoc.hospital.model.Paciente;
import com.duoc.hospital.model.Prevision;
import com.duoc.hospital.service.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PacienteControllerTest {

    // Simulamos el servicio de pacientes (como si fuera falso)
    @Mock
    private PacienteService pacienteService;

    // Aquí probamos el controlador de pacientes, usando el servicio simulado
    @InjectMocks
    private PacienteController pacienteController;

    // Esto se ejecuta antes de cada prueba para dejar todo listo
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Método para crear un paciente de prueba (ficticio)
    private Paciente crearPaciente() {
        Paciente p = new Paciente();
        p.setId(1);
        p.setRun("12345678-9");
        p.setNombre("Juan");
        p.setApellido("Pérez");
        p.setCorreo("juan@mail.com");
        p.setTelefono("123456789");
        p.setFechaNacimiento(Date.valueOf("2000-01-01"));
        Prevision prev = new Prevision();
        prev.setId(1);
        prev.setNombre("FONASA");
        p.setPrevision(prev);
        return p;
    }

    @Test
    void testGetAll_ReturnsOk() {
        // Prueba: Si hay pacientes registrados,
        // el sistema debe responder con 200 (OK) y la lista
        List<Paciente> lista = Arrays.asList(crearPaciente(), crearPaciente());
        when(pacienteService.getAllPacientes()).thenReturn(lista);

        ResponseEntity<List<Paciente>> response = pacienteController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetAll_ReturnsNoContent() {
        // Prueba: Si NO hay pacientes registrados,
        // el sistema debe responder con 204 (Sin contenido)
        when(pacienteService.getAllPacientes()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Paciente>> response = pacienteController.getAll();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetById_Found() {
        // Prueba: Si busco un paciente que EXISTE,
        // el sistema debe responder con 200 (OK) y sus datos
        Paciente paciente = crearPaciente();
        when(pacienteService.findById(1)).thenReturn(Optional.of(paciente));

        ResponseEntity<Paciente> response = pacienteController.getById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetById_NotFound() {
        // Prueba: Si busco un paciente que NO EXISTE,
        // el sistema debe responder con 404 (No encontrado)
        when(pacienteService.findById(99)).thenReturn(Optional.empty());

        ResponseEntity<Paciente> response = pacienteController.getById(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate_ReturnsCreated() {
        // Prueba: Si creo un paciente correctamente,
        // el sistema debe responder con 201 (Creado) y sus datos
        Paciente paciente = crearPaciente();
        when(pacienteService.save(any(Paciente.class))).thenReturn(paciente);

        ResponseEntity<Paciente> response = pacienteController.create(paciente);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testCreate_ReturnsBadRequest() {
        // Prueba: Si creo un paciente con datos inválidos (ej. RUN duplicado),
        // el sistema debe responder con 400 (Solicitud incorrecta)
        Paciente paciente = crearPaciente();
        when(pacienteService.save(any(Paciente.class))).thenThrow(new IllegalArgumentException());

        ResponseEntity<Paciente> response = pacienteController.create(paciente);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdate_ReturnsOk() {
        // Prueba: Si actualizo un paciente que EXISTE,
        // el sistema debe responder con 200 (OK) y sus datos actualizados
        Paciente paciente = crearPaciente();
        when(pacienteService.update(eq(1), any(Paciente.class))).thenReturn(Optional.of(paciente));

        ResponseEntity<Paciente> response = pacienteController.update(1, paciente);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdate_ReturnsNotFound() {
        // Prueba: Si actualizo un paciente que NO EXISTE,
        // el sistema debe responder con 404 (No encontrado)
        Paciente paciente = crearPaciente();
        when(pacienteService.update(eq(99), any(Paciente.class))).thenReturn(Optional.empty());

        ResponseEntity<Paciente> response = pacienteController.update(99, paciente);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdate_ReturnsBadRequest() {
        // Prueba: Si actualizo un paciente con datos inválidos,
        // el sistema debe responder con 400 (Solicitud incorrecta)
        Paciente paciente = crearPaciente();
        when(pacienteService.update(eq(1), any(Paciente.class))).thenThrow(new IllegalArgumentException());

        ResponseEntity<Paciente> response = pacienteController.update(1, paciente);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testDelete_ReturnsNoContent() {
        // Prueba: Si elimino un paciente que EXISTE,
        // el sistema debe responder con 204 (Sin contenido)
        Paciente paciente = crearPaciente();
        when(pacienteService.findById(1)).thenReturn(Optional.of(paciente));
        doNothing().when(pacienteService).deleteById(1);

        ResponseEntity<Void> response = pacienteController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDelete_ReturnsNotFound() {
        // Prueba: Si elimino un paciente que NO EXISTE,
        // el sistema debe responder con 404 (No encontrado)
        when(pacienteService.findById(99)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = pacienteController.delete(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // PRUEBAS PARA REPORTES ESPECIALES
    @Test
    void testGetMayoresDe_ReturnsOk() {
        // Prueba: Si busco pacientes mayores de cierta edad y hay resultados,
        // el sistema debe responder con 200 (OK)
        List<Paciente> lista = Arrays.asList(crearPaciente());
        when(pacienteService.findMayoresDeEdad(60)).thenReturn(lista);

        ResponseEntity<List<Paciente>> response = pacienteController.getMayoresDe(60);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetMayoresDe_ReturnsNoContent() {
        // Prueba: Si busco pacientes mayores de cierta edad pero no hay resultados,
        // el sistema debe responder con 204 (Sin contenido)
        when(pacienteService.findMayoresDeEdad(60)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Paciente>> response = pacienteController.getMayoresDe(60);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetMenoresDe_ReturnsOk() {
        // Prueba: Si busco pacientes menores de cierta edad y hay resultados,
        // el sistema debe responder con 200 (OK)
        List<Paciente> lista = Arrays.asList(crearPaciente());
        when(pacienteService.findMenoresDeEdad(18)).thenReturn(lista);

        ResponseEntity<List<Paciente>> response = pacienteController.getMenoresDe(18);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetMenoresDe_ReturnsNoContent() {
        // Prueba: Si busco pacientes menores de cierta edad pero no hay resultados,
        // el sistema debe responder con 204 (Sin contenido)
        when(pacienteService.findMenoresDeEdad(18)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Paciente>> response = pacienteController.getMenoresDe(18);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetByPrevision_ReturnsOk() {
        // Prueba: Si busco pacientes por previsión (ej. FONASA) y hay resultados,
        // el sistema debe responder con 200 (OK)
        List<Paciente> lista = Arrays.asList(crearPaciente());
        when(pacienteService.findByPrevisionNombre("FONASA")).thenReturn(lista);

        ResponseEntity<List<Paciente>> response = pacienteController.getByPrevision("FONASA");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetByPrevision_ReturnsNoContent() {
        // Prueba: Si busco pacientes por previsión (ej. ISAPRE) pero no hay resultados,
        // el sistema debe responder con 204 (Sin contenido)
        when(pacienteService.findByPrevisionNombre("ISAPRE")).thenReturn(Collections.emptyList());

        ResponseEntity<List<Paciente>> response = pacienteController.getByPrevision("ISAPRE");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}