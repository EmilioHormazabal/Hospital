package com.duoc.hospital;

// Esto es para que funcione el controlador, modelo y servicio
import com.duoc.hospital.controller.PacienteController;
import com.duoc.hospital.model.Paciente;
import com.duoc.hospital.model.Prevision;
import com.duoc.hospital.service.PacienteService;
// Esto es para las pruebas, lo básico de JUnit y Mockito
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// Y esto para listas, fechas y cosas de Java
import java.sql.Date;
import java.util.*;

// Para hacer las comprobaciones (assertEquals, etc.) y simular cosas (when, doNothing)
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Clase para probar el "cerebro" de los pacientes (el controlador)
class PacienteControllerTest {

    // Esto simula al servicio de pacientes, como si fuera una versión de prueba
    @Mock
    private PacienteService pacienteService;

    // Aquí le decimos a JUnit que ponga el servicio simulado en nuestro controlador real para probarlo
    @InjectMocks
    private PacienteController pacienteController;

    // Antes de cada prueba inicializamos todo lo que necesitamos para simular
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Arranca los simuladores (mocks)
    }

    // Una función de ayuda para crear un paciente de mentira rápidamente
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
        prev.setNombre("FONASA"); // Le ponemos FONASA de previsión
        p.setPrevision(prev);
        p.setDeuda(0); // Que no deba nada al inicio
        return p;
    }

    // --- Pruebas para obtener TODOS los pacientes ---
    @Test
    void testGetAll_ReturnsOk() {
        // Si hay pacientes, el controlador debe devolver 200 OK y la lista
        List<Paciente> lista = Arrays.asList(crearPaciente(), crearPaciente());
        when(pacienteService.getAllPacientes()).thenReturn(lista); // Simula que el servicio devuelve 2 pacientes

        ResponseEntity<List<Paciente>> response = pacienteController.getAll(); // Llamamos al método real

        assertEquals(HttpStatus.OK, response.getStatusCode()); // Debe ser 200 OK
        assertEquals(2, response.getBody().size()); // Debe haber 2 pacientes en la lista
    }

    @Test
    void testGetAll_ReturnsNoContent() {
        // Si NO hay pacientes, debe devolver 204 Sin Contenido
        when(pacienteService.getAllPacientes()).thenReturn(Collections.emptyList()); // Simula lista vacía

        ResponseEntity<List<Paciente>> response = pacienteController.getAll();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Debe ser 204 NO_CONTENT
    }

    // --- Pruebas para obtener un paciente por ID ---
    @Test
    void testGetById_Found() {
        // Si el paciente existe, debe devolver 200 OK y sus datos
        Paciente paciente = crearPaciente();
        when(pacienteService.findById(1)).thenReturn(Optional.of(paciente)); // Simula que encuentra el paciente 1

        ResponseEntity<Paciente> response = pacienteController.getById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody()); // Que el cuerpo de la respuesta no esté vacío
    }

    @Test
    void testGetById_NotFound() {
        // Si el paciente NO existe, debe devolver 404 No Encontrado
        when(pacienteService.findById(99)).thenReturn(Optional.empty()); // Simula que no encuentra el paciente 99

        ResponseEntity<Paciente> response = pacienteController.getById(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // Debe ser 404 NOT_FOUND
    }

    // --- Pruebas para CREAR un paciente ---
    @Test
    void testCreate_ReturnsCreated() {
        // Si se crea bien, debe devolver 201 Creado y el paciente
        Paciente paciente = crearPaciente();
        when(pacienteService.save(any(Paciente.class))).thenReturn(paciente); // Simula que guarda cualquier paciente

        ResponseEntity<Paciente> response = pacienteController.create(paciente);

        assertEquals(HttpStatus.CREATED, response.getStatusCode()); // Debe ser 201 CREATED
        assertNotNull(response.getBody());
    }

    @Test
    void testCreate_ReturnsBadRequest() {
        // Si hay un error al crear (ej. RUN repetido), debe devolver 400 Bad Request
        Paciente paciente = crearPaciente();
        when(pacienteService.save(any(Paciente.class))).thenThrow(new IllegalArgumentException()); // Simula un error

        ResponseEntity<Paciente> response = pacienteController.create(paciente);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); // Debe ser 400 BAD_REQUEST
    }

    // --- Pruebas para ACTUALIZAR un paciente ---
    @Test
    void testUpdate_ReturnsOk() {
        // Si se actualiza bien un paciente existente, debe devolver 200 OK
        Paciente paciente = crearPaciente();
        when(pacienteService.update(eq(1), any(Paciente.class))).thenReturn(Optional.of(paciente)); // Simula que actualiza el paciente 1

        ResponseEntity<Paciente> response = pacienteController.update(1, paciente);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdate_ReturnsNotFound() {
        // Si intentamos actualizar un paciente que NO existe, debe devolver 404 Not Found
        Paciente paciente = crearPaciente();
        when(pacienteService.update(eq(99), any(Paciente.class))).thenReturn(Optional.empty()); // Simula que no lo encuentra para actualizar

        ResponseEntity<Paciente> response = pacienteController.update(99, paciente);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdate_ReturnsBadRequest() {
        // Si intentamos actualizar con datos malos (ej. RUN de otro paciente), debe devolver 400 Bad Request
        Paciente paciente = crearPaciente();
        when(pacienteService.update(eq(1), any(Paciente.class))).thenThrow(new IllegalArgumentException()); // Simula error de validación

        ResponseEntity<Paciente> response = pacienteController.update(1, paciente);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // --- Pruebas para ELIMINAR un paciente ---
    @Test
    void testDelete_ReturnsNoContent() {
        // Si se elimina un paciente existente, debe devolver 204 Sin Contenido
        Paciente paciente = crearPaciente();
        when(pacienteService.findById(1)).thenReturn(Optional.of(paciente)); // Simula que el paciente 1 existe
        doNothing().when(pacienteService).deleteById(1); // Simula que el borrado fue exitoso

        ResponseEntity<Void> response = pacienteController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Debe ser 204 NO_CONTENT
        verify(pacienteService, times(1)).deleteById(1); // Comprobamos que el servicio fue llamado
    }

    @Test
    void testDelete_ReturnsNotFound() {
        // Si intentamos eliminar un paciente que NO existe, debe devolver 404 No Encontrado
        when(pacienteService.findById(99)).thenReturn(Optional.empty()); // Simula que el paciente 99 no existe

        ResponseEntity<Void> response = pacienteController.delete(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(pacienteService, never()).deleteById(99); // Comprobamos que el servicio NO fue llamado
    }

    // --- PRUEBAS PARA REPORTES ESPECIALES (Filtrar por edad y previsión) ---

    @Test
    void testGetMayoresDe_ReturnsOk() {
        // Si buscamos mayores de una edad y los encontramos, debe ser 200 OK
        List<Paciente> lista = Arrays.asList(crearPaciente());
        when(pacienteService.findMayoresDeEdad(60)).thenReturn(lista); // Simula que encuentra pacientes mayores

        ResponseEntity<List<Paciente>> response = pacienteController.getMayoresDe(60);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty()); // La lista no debe estar vacía
    }

    @Test
    void testGetMayoresDe_ReturnsNoContent() {
        // Si buscamos mayores de una edad y NO hay, debe ser 204 Sin Contenido
        when(pacienteService.findMayoresDeEdad(60)).thenReturn(Collections.emptyList()); // Simula lista vacía

        ResponseEntity<List<Paciente>> response = pacienteController.getMayoresDe(60);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetMenoresDe_ReturnsOk() {
        // Si buscamos menores de una edad y los encontramos, debe ser 200 OK
        List<Paciente> lista = Arrays.asList(crearPaciente());
        when(pacienteService.findMenoresDeEdad(18)).thenReturn(lista); // Simula que encuentra pacientes menores

        ResponseEntity<List<Paciente>> response = pacienteController.getMenoresDe(18);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetMenoresDe_ReturnsNoContent() {
        // Si buscamos menores de una edad y NO hay, debe ser 204 Sin Contenido
        when(pacienteService.findMenoresDeEdad(18)).thenReturn(Collections.emptyList()); // Simula lista vacía

        ResponseEntity<List<Paciente>> response = pacienteController.getMenoresDe(18);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetByPrevision_ReturnsOk() {
        // Si buscamos por previsión (ej. FONASA) y hay resultados, debe ser 200 OK
        List<Paciente> lista = Arrays.asList(crearPaciente());
        when(pacienteService.findByPrevisionNombre("FONASA")).thenReturn(lista); // Simula que encuentra pacientes FONASA

        ResponseEntity<List<Paciente>> response = pacienteController.getByPrevision("FONASA");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetByPrevision_ReturnsNoContent() {
        // Si buscamos por previsión y NO hay, debe ser 204 Sin Contenido
        when(pacienteService.findByPrevisionNombre("ISAPRE")).thenReturn(Collections.emptyList()); // Simula que no encuentra ISAPRE

        ResponseEntity<List<Paciente>> response = pacienteController.getByPrevision("ISAPRE");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    // --- Pruebas para obtener la deuda de un paciente ---
    @Test
    void testGetDeudaById_ReturnsOk() {
        // Si el paciente existe y tiene deuda, debe devolver 200 OK y el monto
        Paciente paciente = crearPaciente();
        paciente.setDeuda(75000); // Le ponemos una deuda de 75000
        when(pacienteService.getPacienteById(1)).thenReturn(Optional.of(paciente)); // Simula que encuentra el paciente 1

        ResponseEntity<Integer> response = pacienteController.getDeudaById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(75000, response.getBody()); // La deuda debe ser 75000
    }

    @Test
    void testGetDeudaById_ReturnsNotFound() {
        // Si el paciente NO existe, debe devolver 404 No Encontrado al buscar su deuda
        when(pacienteService.getPacienteById(1)).thenReturn(Optional.empty()); // Simula que no encuentra el paciente

        ResponseEntity<Integer> response = pacienteController.getDeudaById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // Debe ser 404 NOT_FOUND
    }
}