# 📖 Manual de Usuario - Hospital V&M

Bienvenido/a al sistema **Hospital V&M**. Esta guía te ayudará a utilizar la API REST para la gestión hospitalaria, incluyendo cómo acceder a la documentación, probar los endpoints y comprender las principales funcionalidades.

---

## 1. Requisitos para el usuario

- Navegador web moderno (Chrome, Firefox, Edge, etc.)
- Acceso a la red local o a la máquina donde se despliega la API
- (Opcional) Docker Desktop para despliegue rápido

---

## 2. Acceso a la aplicación

- **Desde el navegador:**  
  Ingresa a [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) para explorar y probar la API de forma interactiva con Swagger UI.

---

## 3. Funcionalidades principales

- **Pacientes:** Crear, consultar, actualizar y eliminar pacientes.
- **Médicos:** Gestión completa de médicos y sus especialidades.
- **Atenciones:** Registro y consulta de atenciones médicas.
- **Reportes:** Consultas avanzadas por edad, previsión, fechas, etc.

---

## 4. Ejemplo de uso

### Crear un paciente

1. Accede a `/api/v1/pacientes` en Swagger UI.
2. Haz clic en el método **POST**.
3. Completa los datos del paciente en el formulario:

{
"run": "12345678-9",
"nombre": "Juan",
"apellido": "Pérez",
"fechaNacimiento": "1990-01-01",
"correo": "juan@mail.com",
"telefono": "+56912345678",
"prevision": {"id": 1}
}

4. Haz clic en **"Execute"** para enviar la solicitud.

### Consultar atenciones entre fechas

1. Accede a `/api/v1/atenciones/fecha` en Swagger UI.
2. Usa el método **GET** e ingresa los parámetros `desde` y `hasta`, por ejemplo:

desde=2025-01-01
hasta=2025-12-31

3. Haz clic en **"Execute"** para ver los resultados.

---

## 5. Preguntas frecuentes

- **¿Qué hago si veo un error 404?**  
Asegúrate de estar usando la ruta correcta (por ejemplo, `/api/v1/pacientes`).

- **¿Cómo accedo a la documentación interactiva?**  
Ve a [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

- **¿Puedo usar la API desde Postman o cURL?**  
Sí, puedes consumir cualquier endpoint usando herramientas externas.

- **¿Cómo reinicio el sistema?**  
Consulta el Manual de Operación para instrucciones detalladas.

---

## 6. Soporte

- **Desarrollador:** Emilio Hormazabal  
- **Email:** emi.hormazabal@duocuc.cl

---

> **Recuerda:** Si tienes dudas sobre cómo operar el sistema (instalación, reinicio, restauración de base de datos, etc.), revisa el **Manual de Operación** incluido en este repositorio.