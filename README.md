# Hospital V&M

API REST para la gestión hospitalaria desarrollada en Java con Spring Boot y Maven. Permite administrar pacientes, médicos, atenciones, especialidades, previsiones y estados.

## Tecnologías utilizadas

- Java 21
- Spring Boot 3.4.5
- Maven
- JPA/Hibernate
- SQL Server

## Requisitos previos

- Java 21 instalado
- Maven instalado
- SQL Server en ejecución y accesible
- IDE recomendado: IntelliJ IDEA

## Configuración inicial

1. **Clona el repositorio:**
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd hospital

Configura la base de datos:


Crea una base de datos llamada Hospital en SQL Server.
Crea el usuario y otorga permisos si es necesario.
Edita el archivo src/main/resources/application.properties con tus credenciales y URL de conexión:
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=Hospital;encrypt=true;trustServerCertificate=true;
spring.datasource.username=DUOC_USER
spring.datasource.password=Duocadmin25
Compila y ejecuta la aplicación:


Desde terminal:
mvn clean install
mvn spring-boot:run
O desde IntelliJ ejecutando la clase HospitalApplication.
Endpoints principales
/api/v1/pacientes
CRUD y búsquedas de pacientes (por nombre, apellido, run, previsión, especialidad, edad).
/api/v1/medicos
CRUD y búsquedas de médicos (por especialidad, sueldo total, etc).
/api/v1/atenciones
CRUD y consultas de atenciones (por fecha, rango, costo, médico, paciente, estado, ganancia total).
/api/v1/especialidades
CRUD y listado de especialidades médicas.
/api/v1/previsiones
CRUD y listado de previsiones.
/api/v1/Estados
CRUD y listado de estados de atención.
Ejemplo de uso
Para consumir la API desde Postman o cualquier cliente HTTP, agrega el header:

Content-Type: application/json

Ejemplo de petición
Listar pacientes:
GET http://localhost:8080/api/v1/pacientes
Crear un médico:
POST http://localhost:8080/api/v1/medicos
Body: { ... }
Notas importantes
Las fechas se manejan en UTC en la base de datos. Si necesitas mostrar la hora local de Chile, convierte en el frontend usando la zona horaria America/Santiago.
El sistema valida unicidad de RUN, correo y teléfono para pacientes y médicos.
Solo se aceptan previsiones FONASA o ISAPRE para pacientes.
El sistema crea y elimina las tablas automáticamente al iniciar/detener la app (spring.jpa.hibernate.ddl-auto=create-drop).
Estructura del proyecto
model/ — Entidades JPA
repository/ — Repositorios Spring Data JPA
service/ — Lógica de negocio
controller/ — Endpoints REST
Contacto emi.hormazabal@duocuc.cl
Para dudas o soporte, contacta a los desarrolladores del proyecto (Emilio Hormazabal, Ing en Informatica 2do Año).