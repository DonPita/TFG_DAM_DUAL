# Gestión de Liga Deportiva de Waterpolo (TFG v0.1)

Este proyecto es una aplicación para gestionar ligas deportivas de waterpolo, desarrollado como Trabajo de Fin de Grado (TFG) en Desarrollo de Aplicaciones Multiplataforma (DAM). Incluye dos bases de datos MySQL (`waterpolo-db` y `auth-db`) y dos servicios Spring Boot (`waterpolo-api` y `auth-service`), todos orquestados mediante `docker-compose`.

## Requisitos

- **Docker** y **Docker Compose** instalados.
- **Java 21** (JDK) y **Maven** para generar los `.jar` de los servicios Spring Boot.
- Un archivo `.env` con las variables de entorno definidas (ver abajo).

## Estructura del Proyecto

El proyecto se organiza en las siguientes carpetas:

- **Bases de datos**:
    - `BBDD/auth-db/`:
        - `auth_service_db.sql`: Crea las tablas para usuarios y tokens.
        - `Dockerfile`: Construye la imagen de la base de datos de autenticación.
        - `my.cnf`: Configuración de MySQL (UTF-8).
    - `BBDD/waterpolo-db/`:
        - `waterpolo_db.sql`: Crea tablas, índices y triggers para la gestión de waterpolo.
        - `waterpolo_db_data_pre.sql`: Inserta datos iniciales (temporadas, ligas, equipos, etc.).
        - `waterpolo_db_jugadores.sql`: Inserta datos de jugadores de prueba.
        - `waterpolo_primera_vuelta_cadete.sql`: Inserta datos de partidos de la primera vuelta de la liga cadete.
        - `Dockerfile`: Construye la imagen de la base de datos de waterpolo.
        - `my.cnf`: Configuración de MySQL (UTF-8).
- **Servicios Spring Boot**:
    - `auth-service/`: API de autenticación (JWT).
        - Contiene `pom.xml`, código fuente (`src/`), y `Dockerfile`.
    - `waterpolo-api/`: API para gestionar ligas, equipos, partidos, etc.
        - Contiene `pom.xml`, código fuente (`src/`), y `Dockerfile`.
- **Raíz**:
    - `docker-compose.yml`: Orquesta los servicios `auth-service`, `waterpolo-api`, `auth-db`, y `waterpolo-db`.
    - `.env`: Define las variables de entorno.

## Configuración del Entorno

1. **Crea el archivo `.env`** en la raíz del proyecto con las siguientes variables:

```
DB_USERNAME=root
DB_PASSWORD=abc123.
AUTH_SERVICE_DB_URL=jdbc:mysql://auth-db:3306/auth_service_db?createDatabaseIfNotExist=true
WATERPOLO_DB_URL=jdbc:mysql://waterpolo-db:3306/waterpolo_db?createDatabaseIfNotExist=true
JWT_SECRET=your_jwt_secret_here
COMPOSE_PROJECT_NAME=waterpolo-v0
```

- `DB_PASSWORD`: Contraseña para las bases de datos.
- `AUTH_SERVICE_DB_URL` y `WATERPOLO_DB_URL`: URLs de conexión JDBC.
- `JWT_SECRET`: Clave secreta para firmar tokens JWT (cámbiala por una segura).

2. **Estructura esperada**:

```
project_root/
├── .env
├── docker-compose.yml
├── auth-service/
│   ├── Dockerfile
│   ├── pom.xml
│   ├── src/
├── waterpolo-api/
│   ├── Dockerfile
│   ├── pom.xml
│   ├── src/
├── BBDD/
│   ├── auth-db/
│   │   ├── Dockerfile
│   │   ├── auth_service_db.sql
│   │   ├── my.cnf
│   ├── waterpolo-db/
│   │   ├── Dockerfile
│   │   ├── waterpolo_db.sql
│   │   ├── waterpolo_db_data_pre.sql
│   │   ├── waterpolo_db_jugadores.sql
│   │   ├── my.cnf
```

## Preparar los Servicios Spring Boot

Antes de levantar los servicios, genera los archivos `.jar` para `auth-service` y `waterpolo-api`:

1. **Instala Java y Maven**:
    - Descarga e instala **JDK 21** 
    - Descarga **Apache Maven** desde [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi).
    - Configura `JAVA_HOME` y añade Maven al `PATH` (consulta la documentación oficial).

2. **Genera los `.jar`**:
    - Navega a cada carpeta de servicio y ejecuta:
      ```
      cd auth-service
      mvn clean package -DskipTests
      cd ../waterpolo-api
      mvn clean package -DskipTests
      ```
    - Esto genera los `.jar` en `auth-service/target/` y `waterpolo-api/target/` (por ejemplo, `auth-service.jar` y `waterpolo-api.jar`).

3. **Verifica los `Dockerfile`**:
    - Asegúrate de que los `Dockerfile` en `auth-service` y `waterpolo-api` copian los `.jar` generados. Ejemplo para `auth-service`:
      ```dockerfile
      FROM openjdk:17-jdk-slim
      WORKDIR /app
      COPY target/auth-service.jar app.jar
      EXPOSE 8082
      ENTRYPOINT ["java", "-jar", "app.jar"]
      ```

## Levantar los Servicios

1. **Levanta todos los servicios**:
    - Desde la carpeta raíz (donde está `docker-compose.yml`), ejecuta:
      ```
      docker compose up -d
      ```
    - Esto construye y levanta los contenedores para `auth-service` (puerto 8082), `waterpolo-api` (puerto 8081), `auth-db` (puerto 3307), y `waterpolo-db` (puerto 3308) en segundo plano.

2. **Persistencia de datos (opcional)**:
    - Por defecto, los datos de las bases de datos no persisten. Para habilitar la persistencia, hay que descomentar las secciones de `volumes` en el `docker-compose.yml`:
      ```yaml
      services:
        auth-db:
          # ...
          volumes:
            - auth-db-data:/var/lib/mysql
        waterpolo-db:
          # ...
          volumes:
            - waterpolo-db-data:/var/lib/mysql
      volumes:
        auth-db-data:
        waterpolo-db-data:
      ```
    - Luego, ejecuta:
      ```
      docker compose up -d
      ```

## Acceder a los Servicios

### Bases de Datos

- **Conectar a `waterpolo-db`** (puerto 3308):
  ```
  mysql -h 127.0.0.1 -P 3308 -u root -p
  ```
  O directamente al contenedor:
  ```
  docker exec -it waterpolo-db mysql -u root -p"$DB_PASSWORD" waterpolo_db
  ```

- **Conectar a `auth-db`** (puerto 3307):
  ```
  mysql -h 127.0.0.1 -P 3307 -u root -p
  ```
  O directamente al contenedor:
  ```
  docker exec -it auth-db mysql -u root -p"$DB_PASSWORD" auth_service_db
  ```

- **Contraseña**: La definida en `DB_PASSWORD` (por ejemplo, `abc123.`).

### APIs

- **Swagger UI** (documentación de las APIs):
    - Para `waterpolo-api`:
      ```
      http://localhost:8081/api-v0/swagger-ui/index.html
      ```
    - Para `auth-service`:
      ```
      http://localhost:8082/swagger-ui/index.html
      ```


- **Especificación OpenAPI (JSON)**:
    - `http://localhost:8081/api-v0/v3/api-docs` (waterpolo-api)
    - `http://localhost:8082/v3/api-docs` (auth-service)

## Gestionar los Contenedores

- **Ver estado**: `docker compose ps`
- **Parar**: `docker compose stop`
- **Iniciar (si ya existen)**: `docker compose start`
- **Eliminar contenedores**: `docker compose down`
- **Eliminar contenedores y volúmenes**: `docker compose down -v`
- **Ver logs**: `docker compose logs [auth-service | waterpolo-api | auth-db | waterpolo-db]`

## Detalles de los Servicios

### Waterpolo DB

- **Nombre**: `waterpolo_db`
- **Contenedor**: `waterpolo-db`
- **Puerto**: `3308 en local` `3306 en contenedor`
- **Usuario**: `root`
- **Contraseña**: Definida en `DB_PASSWORD`
- **Contenido**: Tablas para temporadas, ligas, equipos, jugadores, partidos, estadísticas y clasificaciones, con datos iniciales de ejemplo.
- **Persistencia**: Sin volúmenes, los datos se reinician al recrear el contenedor.

### Auth DB

- **Nombre**: `auth_service_db`
- **Contenedor**: `auth-db`
- **Puerto**: `3307 en local` `3306 en contenedor`
- **Usuario**: `root`
- **Contraseña**: Definida en `DB_PASSWORD`
- **Contenido**: Tablas para usuarios y tokens de autenticación.
- **Persistencia**: Sin volúmenes, los datos se reinician al recrear el contenedor.

### Waterpolo API

- **Contenedor**: `waterpolo-api`
- **Puerto**: `8081 en local`
- **Descripción**: API REST para gestionar ligas, equipos, partidos, y estadísticas de waterpolo.
- **Dependencias**: Requiere `waterpolo-db` y `auth-service`.

### Auth Service

- **Contenedor**: `auth-service`
- **Puerto**: `8082 en local`
- **Descripción**: API REST para autenticación y autorización (JWT).
- **Dependencias**: Requiere `auth-db`.

## Notas Adicionales

- **Red**: Los servicios están conectados en una red bridge (`app-network`) para comunicación interna.
- **Healthcheck**: Las bases de datos incluyen un chequeo de salud que verifica MySQL cada 10 segundos.
- **Codificación**: UTF-8 (`utf8mb4`) configurado en `my.cnf` para soportar caracteres especiales.
- **Puertos**: Asegúrate de que los puertos `3307`, `3308`, `8081`, y `8082` estén libres. Ajusta el `docker-compose.yml` si hay conflictos.
- **Docker Compose V2**: Usa `docker compose` (sin guión) para comandos. El campo `version` en `docker-compose.yml` es obsoleto y puede eliminarse.
- **Diagrama de clases**: Para documentar el modelo, en un futuro se generará un diagrama UML con IntelliJ IDEA Ultimate:
    1. Abre el proyecto en IntelliJ.
    2. Haz clic derecho en el paquete principal (por ejemplo, `com.tfg.model`) > `Diagrams` > `Show Diagram` > `Java Class Diagram`.
    3. Exporta como PNG para la memoria del TFG.

---

© 2025 Adrián Pita Bermúdez. Todolos dereitos reservados.
