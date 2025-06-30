# ⚙️ Manual de Operación - Hospital V&M

Este manual describe los pasos para instalar, desplegar, operar y mantener el sistema **Hospital V&M**.

---

## 1. Instalación y despliegue

### Opción A: Despliegue tradicional

1. **Instala los requisitos:**
   - Java 21
   - Maven
   - MySQL 8.x

2. **Clona el repositorio:**

git clone https://github.com/EmilioHormazabal/Hospital.git
cd Hospital


3. **Configura la base de datos:**
- Crea una base de datos llamada `hospital` en MySQL.
- Edita `src/main/resources/application.properties` con tus credenciales.

4. **Ejecuta la aplicación:**

mvn spring-boot:run


---

### Opción B: Despliegue con Docker (recomendado)

1. **Instala Docker Desktop** y asegúrate de que esté en ejecución.
2. **Clona el repositorio:**

git clone https://github.com/EmilioHormazabal/Hospital.git
cd Hospital

3. **Levanta el sistema:**

docker compose up --build

4. **Accede a la API:**  
[http://localhost:8080](http://localhost:8080)

---

## 2. Operación básica

- **Detener el sistema:**
- Si usas Docker:
 ```
 docker compose down
 ```
- Si usas Maven:
 - Detén el proceso en la terminal (Ctrl+C).

- **Ver logs:**
- Docker:
 ```
 docker compose logs
 ```
- Maven:
 - Revisa la salida de la terminal donde ejecutaste el comando.

---

## 3. Mantenimiento

- **Actualizar el sistema:**
1. Detén la aplicación.
2. Ejecuta:
  ```
  git pull
  ```
3. Vuelve a desplegar el sistema según la opción elegida.

- **Restaurar la base de datos:**
- Si usas `spring.jpa.hibernate.ddl-auto=create-drop`, las tablas se recrean automáticamente al reiniciar la app.
- Para restaurar datos específicos, importa un respaldo SQL usando MySQL Workbench o línea de comandos.

---

## 4. Resolución de problemas

- **El puerto 8080 o 3306 está en uso:**  
Cambia el puerto en el archivo `docker-compose.yml` o detén otros servicios que usen esos puertos.

- **No puedo acceder a Swagger:**  
Verifica que la aplicación esté corriendo y accede a [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

- **Error de conexión a la base de datos:**  
Asegúrate de que MySQL esté corriendo y que las credenciales sean correctas.

---

## 5. Soporte y contacto

- **Desarrollador:** Emilio Hormazabal  
- **Email:** emi.hormazabal@duocuc.cl

---

> **Nota:** Para dudas sobre el uso de la API, consulta el **Manual de Usuario** incluido en este repositorio.