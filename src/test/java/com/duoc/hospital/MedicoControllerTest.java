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

// Clase de pruebas unitarias para MedicoController
class MedicoControllerTest {

    // Simulamos el servicio de médicos usando Mockito.
    // @Mock crea un mock (objeto simulado) de MedicoService.
    @Mock
    private MedicoService medicoService;

    // @InjectMocks inyecta los mocks creados (como medicoService)
    // en la instancia real de MedicoController que vamos a probar.
    @InjectMocks
    private MedicoController medicoController;

    // Este método se ejecuta antes de cada prueba (@Test).
    // Prepara el entorno de prueba inicializando los mocks.
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks anotados.
    }

    // --- Pruebas para getAll() ---
    @Test
    void testGetAll_ReturnsOk() {
        // Prueba: Cuando hay médicos registrados,
        // debe responder con estado 200 (OK) y la lista de médicos.
        List<Medico> lista = Arrays.asList(new Medico(), new Medico());
        // Cuando se llama a medicoService.getAllMedicos(), devuelve nuestra lista simulada.
        when(medicoService.getAllMedicos()).thenReturn(lista);

        // Llama al método del controlador que estamos probando.
        ResponseEntity<List<Medico>> response = medicoController.getAll();

        // Verifica que el estado HTTP de la respuesta sea OK (200).
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verifica que el cuerpo de la respuesta contenga 2 médicos.
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetAll_ReturnsNoContent() {
        // Prueba: Cuando NO hay médicos registrados,
        // debe responder con estado 204 (Sin contenido).
        // Cuando se llama a medicoService.getAllMedicos(), devuelve una lista vacía.
        when(medicoService.getAllMedicos()).thenReturn(Collections.emptyList());

        // Llama al método del controlador.
        ResponseEntity<List<Medico>> response = medicoController.getAll();

        // Verifica que el estado HTTP sea NO_CONTENT (204).
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    // --- Pruebas para getById() ---
    @Test
    void testGetById_Found() {
        // Prueba: Cuando busco un médico que EXISTE,
        // debe responder con estado 200 (OK) y sus datos.
        Medico medico = new Medico();
        // Cuando se llama a medicoService.findById(1), devuelve un Optional con el médico.
        when(medicoService.findById(1)).thenReturn(Optional.of(medico));

        // Llama al método del controlador.
        ResponseEntity<Medico> response = medicoController.getById(1);

        // Verifica que el estado HTTP sea OK (200).
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verifica que el cuerpo de la respuesta no sea nulo.
        assertNotNull(response.getBody());
    }

    @Test
    void testGetById_NotFound() {
        // Prueba: Cuando busco un médico que NO EXISTE,
        // debe responder con error 404 (No encontrado).
        // Cuando se llama a medicoService.findById(99), devuelve un Optional vacío.
        when(medicoService.findById(99)).thenReturn(Optional.empty());

        // Llama al método del controlador.
        ResponseEntity<Medico> response = medicoController.getById(99);

        // Verifica que el estado HTTP sea NOT_FOUND (404).
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // --- Pruebas para create() ---
    @Test
    void testCreate_ReturnsCreated() {
        // Prueba: Cuando creo un nuevo médico correctamente,
        // debe responder con estado 201 (Creado) y sus datos.
        Medico medico = new Medico();
        Especialidad esp = new Especialidad();
        esp.setId(1);
        medico.setEspecialidadMedico(esp); // Simula la asignación de especialidad.

        // Cuando se llama a medicoService.save con cualquier Medico, devuelve el médico simulado.
        when(medicoService.save(any(Medico.class))).thenReturn(medico);

        // Llama al método del controlador.
        ResponseEntity<Medico> response = medicoController.create(medico);

        // Verifica que el estado HTTP sea CREATED (201).
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // Verifica que el cuerpo de la respuesta no sea nulo.
        assertNotNull(response.getBody());
    }

    @Test
    void testCreate_ReturnsBadRequest() {
        // Prueba: Cuando intento crear un médico con datos inválidos (ej. RUN duplicado),
        // debe responder con estado 400 (Solicitud incorrecta).
        // Cuando se llama a medicoService.save, lanza una IllegalArgumentException.
        when(medicoService.save(any(Medico.class))).thenThrow(new IllegalArgumentException("RUN duplicado"));

        // Llama al método del controlador.
        ResponseEntity<Medico> response = medicoController.create(new Medico());

        // Verifica que el estado HTTP sea BAD_REQUEST (400).
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Verifica que el cuerpo de la respuesta sea nulo (como lo maneja el controlador en caso de error).
        assertNull(response.getBody());
    }

    // --- Pruebas para delete() ---
    @Test
    void testDelete_ReturnsNoContent() {
        // Prueba: Cuando elimino un médico que EXISTE,
        // debe responder con estado 204 (Sin contenido).
        // Simula que el médico con ID 1 existe.
        when(medicoService.findById(1)).thenReturn(Optional.of(new Medico()));
        // Configura el mock para que deleteById no haga nada (void method).
        doNothing().when(medicoService).deleteById(1);

        // Llama al método del controlador.
        ResponseEntity<Void> response = medicoController.delete(1);

        // Verifica que el estado HTTP sea NO_CONTENT (204).
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        // Verifica que el método deleteById fue llamado una vez.
        verify(medicoService, times(1)).deleteById(1);
    }

    @Test
    void testDelete_ReturnsNotFound() {
        // Prueba: Cuando intento eliminar un médico que NO EXISTE,
        // debe responder con error 404 (No encontrado).
        // Simula que el médico con ID 1 no existe.
        when(medicoService.findById(1)).thenReturn(Optional.empty());

        // Llama al método del controlador.
        ResponseEntity<Void> response = medicoController.delete(1);

        // Verifica que el estado HTTP sea NOT_FOUND (404).
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        // Verifica que deleteById nunca fue llamado.
        verify(medicoService, never()).deleteById(1);
    }

    // --- Pruebas para update() ---
    @Test
    void testUpdateMedico_Success() {
        // Prueba: Cuando actualizo un médico que EXISTE correctamente,
        // debe responder con estado 200 (OK) y sus datos actualizados.
        Medico medico = new Medico();
        // Simula que la actualización es exitosa.
        when(medicoService.update(eq(1), any(Medico.class))).thenReturn(Optional.of(medico));

        // Llama al método del controlador.
        ResponseEntity<Medico> response = medicoController.update(1, medico);

        // Verifica que el estado HTTP sea OK (200).
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdateMedico_NotFound() {
        // Prueba: Cuando intento actualizar un médico que NO EXISTE,
        // debe responder con error 404 (No encontrado).
        // Simula que el médico a actualizar no se encuentra.
        when(medicoService.update(eq(1), any(Medico.class))).thenReturn(Optional.empty());

        // Llama al método del controlador.
        ResponseEntity<Medico> response = medicoController.update(1, new Medico());

        // Verifica que el estado HTTP sea NOT_FOUND (404).
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateMedico_BadRequest() {
        // Prueba: Cuando actualizo con datos INVÁLIDOS (ej. un RUN duplicado),
        // debe responder con error 400 (Solicitud incorrecta).
        // Simula que el servicio lanza una IllegalArgumentException.
        when(medicoService.update(eq(1), any(Medico.class))).thenThrow(IllegalArgumentException.class);

        // Llama al método del controlador.
        ResponseEntity<Medico> response = medicoController.update(1, new Medico());

        // Verifica que el estado HTTP sea BAD_REQUEST (400).
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // --- Pruebas para getMedicosByAntiguedad() ---
    @Test
    void testGetMedicosByAntiguedad_ReturnsOk() {
        // Prueba: Cuando hay médicos con la antigüedad exacta especificada.
        List<Medico> medicos = Arrays.asList(new Medico(), new Medico());
        // Simula que el servicio devuelve dos médicos para 5 años de antigüedad.
        when(medicoService.findByAntiguedadExacta(5)).thenReturn(medicos);

        // Llama al método del controlador.
        ResponseEntity<List<Medico>> response = medicoController.getMedicosByAntiguedad(5);

        // Verifica el estado y el tamaño de la lista.
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetMedicosByAntiguedad_ReturnsNoContent() {
        // Prueba: Cuando no hay médicos con la antigüedad exacta especificada.
        // Simula que el servicio devuelve una lista vacía.
        when(medicoService.findByAntiguedadExacta(5)).thenReturn(Collections.emptyList());

        // Llama al método del controlador.
        ResponseEntity<List<Medico>> response = medicoController.getMedicosByAntiguedad(5);

        // Verifica que la respuesta sea 204 No Content.
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    // --- Pruebas para getMedicosByEspecialidad() (Nuevo Endpoint) ---
    @Test
    void testGetMedicosByEspecialidad_ReturnsOk() {
        // Prueba: Cuando se encuentran médicos para una especialidad dada.
        List<Medico> medicos = Arrays.asList(new Medico(), new Medico());
        // Simula que el servicio devuelve médicos para "Cardiología".
        when(medicoService.findByEspecialidad("Cardiología")).thenReturn(medicos);

        // Llama al método del controlador.
        ResponseEntity<List<Medico>> response = medicoController.getMedicosByEspecialidad("Cardiología");

        // Verifica el estado y el tamaño de la lista.
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetMedicosByEspecialidad_ReturnsNoContent() {
        // Prueba: Cuando no se encuentran médicos para una especialidad dada.
        // Simula que el servicio devuelve una lista vacía.
        when(medicoService.findByEspecialidad("Pediatría")).thenReturn(Collections.emptyList());

        // Llama al método del controlador.
        ResponseEntity<List<Medico>> response = medicoController.getMedicosByEspecialidad("Pediatría");

        // Verifica que la respuesta sea 204 No Content.
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    // --- Pruebas para getSueldoBaseById() (Modificado de getSueldoById) ---
    @Test
    void testGetSueldoBaseById_ReturnsOk() {
        // Prueba: Cuando se encuentra un médico y se obtiene su sueldo base.
        Medico medico = new Medico();
        medico.setSueldoBase(1500000);
        // Simula que medicoService.findById devuelve el médico con sueldo base.
        when(medicoService.findById(1)).thenReturn(Optional.of(medico));

        // Llama al método del controlador.
        ResponseEntity<Integer> response = medicoController.getSueldoBaseById(1);

        // Verifica el estado y el valor del sueldo base.
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1500000, response.getBody());
    }

    @Test
    void testGetSueldoBaseById_ReturnsNotFound() {
        // Prueba: Cuando el médico no se encuentra al intentar obtener su sueldo base.
        // Simula que medicoService.findById devuelve un Optional vacío.
        when(medicoService.findById(1)).thenReturn(Optional.empty());

        // Llama al método del controlador.
        ResponseEntity<Integer> response = medicoController.getSueldoBaseById(1);

        // Verifica que la respuesta sea 404 Not Found.
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // --- Pruebas para getSueldoTotalById() (Nuevo Endpoint) ---
    @Test
    void testGetSueldoTotalById_ReturnsOk() {
        // Prueba: Cuando el sueldo total se calcula exitosamente.
        // Simula que medicoService.calcularSueldoTotal devuelve un valor.
        when(medicoService.calcularSueldoTotal(1)).thenReturn(2000000); // Suponemos un cálculo total de 2,000,000.

        // Llama al método del controlador.
        ResponseEntity<Integer> response = medicoController.getSueldoTotalById(1);

        // Verifica el estado y el valor del sueldo total.
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2000000, response.getBody());
    }

    @Test
    void testGetSueldoTotalById_ReturnsNotFound() {
        // Prueba: Cuando el médico no existe al intentar calcular el sueldo total.
        // Simula que el servicio lanza una IllegalArgumentException (por médico no encontrado).
        when(medicoService.calcularSueldoTotal(1)).thenThrow(new IllegalArgumentException("El médico con ID 1 no existe."));

        // Llama al método del controlador.
        ResponseEntity<Integer> response = medicoController.getSueldoTotalById(1);

        // Verifica que la respuesta sea 404 Not Found.
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetSueldoTotalById_ReturnsBadRequest() {
        // Prueba: Cuando ocurre otro error inesperado durante el cálculo del sueldo total.
        // Simula que el servicio lanza una RuntimeException genérica.
        when(medicoService.calcularSueldoTotal(1)).thenThrow(new RuntimeException("Error inesperado en el cálculo."));

        // Llama al método del controlador.
        ResponseEntity<Integer> response = medicoController.getSueldoTotalById(1);

        // Verifica que la respuesta sea 400 Bad Request.
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}