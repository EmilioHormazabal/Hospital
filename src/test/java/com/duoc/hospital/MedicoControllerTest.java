package com.duoc.hospital;

// Importamos las clases necesarias para las pruebas
import com.duoc.hospital.controller.MedicoController;
import com.duoc.hospital.model.Medico;
import com.duoc.hospital.model.Especialidad;
import com.duoc.hospital.service.MedicoService;
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

class MedicoControllerTest {

    // Simulamos el servicio de médicos
    @Mock
    private MedicoService medicoService;

    // Inyectamos el controlador que vamos a probar
    @InjectMocks
    private MedicoController medicoController;

    // Preparamos el entorno de prueba antes de cada test
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll_ReturnsOk() {
        // Prueba: Cuando hay médicos registrados
        // Debe responder con estado 200 (OK) y la lista de médicos
        List<Medico> lista = Arrays.asList(new Medico(), new Medico());
        when(medicoService.getAllMedicos()).thenReturn(lista);

        ResponseEntity<List<Medico>> response = medicoController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetAll_ReturnsNoContent() {
        // Prueba: Cuando NO hay médicos registrados
        // Debe responder con estado 204 (Sin contenido)
        when(medicoService.getAllMedicos()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Medico>> response = medicoController.getAll();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetById_Found() {
        // Prueba: Cuando busco un médico que EXISTE
        // Debe responder con estado 200 (OK) y sus datos
        Medico medico = new Medico();
        when(medicoService.findById(1)).thenReturn(Optional.of(medico));

        ResponseEntity<Medico> response = medicoController.getById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetById_NotFound() {
        // Prueba: Cuando busco un médico que NO EXISTE
        // Debe responder con error 404 (No encontrado)
        when(medicoService.findById(99)).thenReturn(Optional.empty());

        ResponseEntity<Medico> response = medicoController.getById(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate_ReturnsCreated() {
        // Prueba: Cuando creo un nuevo médico correctamente
        // Debe responder con estado 201 (Creado) y sus datos
        Medico medico = new Medico();
        Especialidad esp = new Especialidad();
        esp.setId(1);
        medico.setEspecialidadMedico(esp);

        when(medicoService.save(any(Medico.class))).thenReturn(medico);

        ResponseEntity<Medico> response = medicoController.create(medico);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // PRUEBAS PARA ELIMINAR Y ACTUALIZAR MÉDICOS
    @Test
    void testDelete_ReturnsNoContent() {
        // Prueba: Cuando elimino un médico que EXISTE
        // Debe responder con estado 204 (Sin contenido)
        when(medicoService.findById(1)).thenReturn(Optional.of(new Medico()));
        doNothing().when(medicoService).deleteById(1);

        ResponseEntity<Void> response = medicoController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDelete_ReturnsNotFound() {
        // Prueba: Cuando intento eliminar un médico que NO EXISTE
        // Debe responder con error 404 (No encontrado)
        when(medicoService.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = medicoController.delete(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateMedico_Success() {
        // Prueba: Cuando actualizo un médico que EXISTE
        // Debe responder con estado 200 (OK) y sus datos actualizados
        Medico medico = new Medico();
        when(medicoService.update(eq(1), any())).thenReturn(Optional.of(medico));
        ResponseEntity<Medico> response = medicoController.update(1, medico);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateMedico_NotFound() {
        // Prueba: Cuando intento actualizar un médico que NO EXISTE
        // Debe responder con error 404 (No encontrado)
        when(medicoService.update(eq(1), any())).thenReturn(Optional.empty());
        ResponseEntity<Medico> response = medicoController.update(1, new Medico());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateMedico_BadRequest() {
        // Prueba: Cuando actualizo con datos INVÁLIDOS
        // (como un RUN duplicado)
        // Debe responder con error 400 (Solicitud incorrecta)
        when(medicoService.update(eq(1), any())).thenThrow(IllegalArgumentException.class);
        ResponseEntity<Medico> response = medicoController.update(1, new Medico());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
