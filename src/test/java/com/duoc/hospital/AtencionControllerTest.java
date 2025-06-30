package com.duoc.hospital;

// Importamos las clases necesarias para las pruebas
import com.duoc.hospital.controller.AtencionController;
import com.duoc.hospital.model.Atencion;
import com.duoc.hospital.service.AtencionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AtencionControllerTest {

    // Simulamos el servicio de atenciones (como si fuera falso)
    @Mock
    private AtencionService atencionService;

    // Aquí probamos el controlador de atenciones, usando el servicio simulado
    @InjectMocks
    private AtencionController atencionController;

    // Esto se ejecuta antes de cada prueba para dejar todo listo
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByFecha_ValidDate_ReturnsOk() {
        // Prueba: Si busco atenciones con una fecha válida y hay resultados,
        // el sistema debe responder con 200 (OK)
        when(atencionService.findByFecha(any())).thenReturn(List.of(new Atencion()));
        ResponseEntity<List<Atencion>> response = atencionController.findByFecha("2025-06-01");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testFindByFecha_NoContent() {
        // Prueba: Si busco atenciones con una fecha válida pero no hay resultados,
        // el sistema debe responder con 204 (Sin contenido)
        when(atencionService.findByFecha(any())).thenReturn(Collections.emptyList());
        ResponseEntity<List<Atencion>> response = atencionController.findByFecha("2025-06-01");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testFindByFecha_InvalidDate() {
        // Prueba: Si ingreso una fecha con formato incorrecto,
        // el sistema debe responder con 400 (Solicitud incorrecta)
        ResponseEntity<List<Atencion>> response = atencionController.findByFecha("invalid-date");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testFindBetween_ValidDates_ReturnsOk() {
        // Prueba: Si busco atenciones entre dos fechas válidas y hay resultados,
        // el sistema debe responder con 200 (OK)
        when(atencionService.findByFechaBetween(any(), any())).thenReturn(List.of(new Atencion()));
        ResponseEntity<List<Atencion>> response = atencionController.findBetween("2025-06-01", "2025-06-30");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testFindBetween_NoContent() {
        // Prueba: Si busco atenciones entre dos fechas válidas pero no hay registros,
        // el sistema debe responder con 204 (Sin contenido)
        when(atencionService.findByFechaBetween(any(), any())).thenReturn(Collections.emptyList());
        ResponseEntity<List<Atencion>> response = atencionController.findBetween("2025-06-01", "2025-06-30");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testFindBetween_InvalidStartDate() {
        // Prueba: Si la fecha de inicio tiene formato incorrecto,
        // el sistema debe responder con 400 (Solicitud incorrecta)
        ResponseEntity<List<Atencion>> response = atencionController.findBetween("invalid-date", "2025-06-30");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testFindBetween_InvalidEndDate() {
        // Prueba: Si la fecha de fin tiene formato incorrecto,
        // el sistema debe responder con 400 (Solicitud incorrecta)
        ResponseEntity<List<Atencion>> response = atencionController.findBetween("2025-06-01", "invalid-date");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
