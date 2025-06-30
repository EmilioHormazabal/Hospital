# Hospital V&M - API REST

API para gestión hospitalaria desarrollada en Java con Spring Boot 3.5.0 y Maven. Permite administrar pacientes, médicos, atenciones, especialidades, previsiones y estados.

## Tecnologías utilizadas
- Java 21
- Spring Boot 3.5.0
- Maven
- JPA/Hibernate
- MySQL
- Swagger/OpenAPI 3.0

## Requisitos previos (modo tradicional)
- Java 21 instalado
- Maven instalado
- MySQL 8.x en ejecución
- IDE recomendado: IntelliJ IDEA

## Configuración inicial (modo tradicional)
1. **Clona el repositorio:**

git clone https://github.com/EmilioHormazabal/Hospital.git
cd Hospital

2. **Configura la base de datos:**
- Crea una base de datos llamada `hospital` en MySQL
- Configura las credenciales en `src/main/resources/application.properties`:
  ```
  spring.datasource.url=jdbc:mysql://localhost:3306/hospital
  spring.datasource.username=tu_usuario
  spring.datasource.password=tu_contraseña
  ```

3. **Ejecuta la aplicación:**

mvn spring-boot:run


---

## 🚀 Ejecución rápida con Docker (opcional y recomendado para pruebas)

¿No quieres instalar Java, Maven ni MySQL? **Usa Docker y tendrás todo listo en minutos.**

### Requisitos previos
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado y en ejecución.

### Pasos para levantar todo el sistema
1. **Clona este repositorio:**

`git clone https://github.com/EmilioHormazabal/Hospital.git`
`cd Hospital`

2. **Ejecuta en la terminal:**

`docker compose up --build`

- Esto descargará las imágenes necesarias, construirá la app y levantará la base de datos MySQL y la API automáticamente.
- Espera a ver en los logs que la aplicación está corriendo en el puerto 8080.

3. **Accede a la aplicación:**
- [http://localhost:8080](http://localhost:8080)
- Documentación Swagger: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

4. **Para detener los contenedores:**

`docker compose down`


#### Notas importantes
- **No necesitas instalar Java, Maven ni MySQL:** Todo corre dentro de los contenedores Docker.
- **El primer arranque puede demorar** porque Docker descarga las imágenes.
- **Si tienes problemas con Docker Desktop:** Verifica que esté ejecutándose y que la virtualización esté habilitada.

---

## Documentación de la API

Accede a la documentación interactiva Swagger UI:

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- o [http://TuIPV4:8080/swagger-ui/index.html](http://TuIPV4:8080/swagger-ui/index.html)

(Para saber tu IP, puedes usar: `ipconfig` en PowerShell o CMD).

---

## Endpoints principales

| Módulo         | Endpoint                     | Funcionalidades                       |
|----------------|------------------------------|---------------------------------------|
| Pacientes      | `/api/v1/pacientes`          | CRUD, reportes por edad/previsión     |
| Médicos        | `/api/v1/medicos`            | CRUD, reportes por especialidad       |
| Atenciones     | `/api/v1/atenciones`         | CRUD, reportes por fecha/estado       |
| Reportes       | `/api/v1/pacientes/reportes` | Pacientes mayores/menores de edad     |
|                | `/api/v1/atenciones/fecha`   | Atenciones por fecha o rango          |

---

## Ejemplos de uso

**Crear un paciente:**

POST /api/v1/pacientes
{
"run": "12345678-9",
"nombre": "Juan",
"apellido": "Pérez",
"fechaNacimiento": "1990-01-01",
"correo": "juan@gmail.com",
"telefono": "+56912345678",
"prevision": {"id": 1}
}

**Obtener atenciones entre fechas:**

GET /api/v1/atenciones/fecha?desde=2025-01-01&hasta=2025-12-31


---

## Características clave

- ✅ Documentación completa con Swagger
- ✅ Validación de datos (unicidad de RUN, correo, teléfono)
- ✅ Manejo de zonas horarias (UTC/Chile)
- ✅ Pruebas unitarias para todos los controladores
- ✅ Inicialización automática de datos esenciales
- ✅ Soporte para previsiones FONASA e ISAPRE

---

## Estructura del proyecto

src/
├── main/
│ ├── java/
│ │ ├── controller/ # Controladores REST
│ │ ├── model/ # Entidades JPA
│ │ ├── repository/ # Repositorios Spring Data
│ │ ├── service/ # Lógica de negocio
│ │ └── config/ # Configuración Swagger e inicialización
│ └── resources/ # Archivos de configuración
└── test/ # Pruebas unitarias

---

## Contacto

- **Desarrollador**: Emilio Hormazabal - Ingeniería en Informática.
- **Email**: emi.hormazabal@duocuc.cl
- **Repositorio**: [https://github.com/EmilioHormazabal/Hospital](https://github.com/EmilioHormazabal/Hospital)
