# Base de Datos de Waterpolo y Servicio de Autenticación (TFG)

Este proyecto contiene una base de datos MySQL dockerizada para gestionar información de waterpolo, creada como parte de un Trabajo de Fin de Grado (TFG) en Desarrollo de Aplicaciones Multiplataforma (DAM). Incluye dos servicios: una base de datos para waterpolo y otra para autenticación, gestionados mediante `docker-compose`.

## Requisitos

- Docker y Docker Compose instalados en tu máquina.
- Un archivo `.env` con la variable `DB_PASSWORD` definida (por ejemplo, `DB_PASSWORD=abc123.`).

## Estructura del Proyecto

La base de datos de Waterpolo está definida en tres scripts:

- `waterpolo_db.sql`: Crea las tablas, índices y triggers.
- `waterpolo_db_data_pre.sql`: Inserta datos iniciales de ejemplo (temporadas, ligas, equipos, etc.).
- `waterpolo_db_jugadores.sql`: Inserta datos de jugadores de prueba y los añade a sus respectivos equipos.

La base de datos de Autenticación está definida en un script:

- `auth_service_db.sql`: Crea las tablas para la gestión de usuarios y tokens.

Estos scripts se integran en imágenes Docker para cada servicio (`waterpolo-db` y `auth-db`) mediante sus respectivos `Dockerfile`, ubicados en las carpetas `BBDD/waterpolo-db` y `BBDD/auth-db`. Los servicios se orquestan con un archivo `docker-compose.yml`.

## Levantar las Bases de Datos

Sigue estos pasos para ejecutar las bases de datos en contenedores Docker usando `docker-compose`:

### 1. Asegúrate de tener los archivos

El proyecto debe tener la siguiente estructura:

```
project_root/
├── .env
├── docker-compose.yml
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

Crea un archivo `.env` en la raíz del proyecto con la contraseña de la base de datos, por ejemplo:

```
DB_PASSWORD=abc123.
```

### 2. Levantar los servicios

Desde la carpeta raíz del proyecto (donde está `docker-compose.yml`), ejecuta:

```
docker-compose up -d
```

Esto construye las imágenes y levanta los contenedores para los servicios `waterpolo-db` y `auth-db` en segundo plano. Los servicios estarán disponibles en:

- `waterpolo-db`: Puerto `3308` (mapeado a `3306` internamente).
- `auth-db`: Puerto `3307` (mapeado a `3306` internamente).

### 3. Persistencia de datos (opcional)

Por defecto, los datos no persisten entre ejecuciones. Para habilitar la persistencia, descomenta las secciones de `volumes` en el `docker-compose.yml`:

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

Luego, vuelve a ejecutar:

```
docker-compose up -d
```

Esto crea volúmenes Docker (`auth-db-data` y `waterpolo-db-data`) para almacenar los datos de las bases de datos.

### 4. Conectarse a las bases de datos

Usa un cliente MySQL (CLI, MySQL Workbench, etc.) para conectarte a los servicios:

#### Para `waterpolo-db`:

```
mysql -h 127.0.0.1 -P 3308 -u root -p
```

O directamente al contenedor:

```
docker exec -it waterpolo-db mysql -u root -p
```

#### Para `auth-db`:

```
mysql -h 127.0.0.1 -P 3307 -u root -p
```

O directamente al contenedor:

```
docker exec -it auth-db mysql -u root -p
```

**Contraseña**: La definida en `DB_PASSWORD` (por ejemplo, `abc123.`).

Para acceder directamente a una base de datos específica:

```
docker exec -it waterpolo-db mysql -u root -p"$DB_PASSWORD" waterpolo_db
```

```
docker exec -it auth-db mysql -u root -p"$DB_PASSWORD" auth_service_db
```

## Gestionar los Contenedores

- **Ver estado**: `docker-compose ps`
- **Parar**: `docker-compose stop`
- **Iniciar (si ya existen)**: `docker-compose start`
- **Eliminar contenedores**: `docker-compose down`
- **Eliminar contenedores y volúmenes**: `docker-compose down -v`
- **Ver logs**: `docker-compose logs [waterpolo-db | auth-db]`

## Detalles de las Bases de Datos

### Waterpolo DB

- **Nombre**: `waterpolo_db`
- **Usuario**: `root`
- **Contraseña**: Definida en `DB_PASSWORD`
- **Contenido**: Tablas para temporadas, ligas, equipos, jugadores, partidos, estadísticas y clasificaciones, con datos iniciales de ejemplo.
- **Persistencia**: Sin volúmenes, los datos se reinician a los de `waterpolo_db_data_pre.sql` y `waterpolo_db_jugadores.sql` al recrear el contenedor.

### Auth DB

- **Nombre**: `auth_service_db`
- **Usuario**: `root`
- **Contraseña**: Definida en `DB_PASSWORD`
- **Contenido**: Tablas para la gestión de usuarios y tokens de autenticación.
- **Persistencia**: Sin volúmenes, los datos se reinician a los de `auth_service_db.sql` al recrear el contenedor.

## Notas Adicionales

- **Red**: Ambos servicios están conectados en una red bridge (`app-network`) para facilitar la comunicación interna.
- **Healthcheck**: Cada servicio incluye un chequeo de salud que verifica la disponibilidad de MySQL cada 10 segundos.
- **Codificación**: Los servicios usan UTF-8 (`utf8mb4`) configurado en `my.cnf` para soportar caracteres especiales.
- **Advertencia**: Los puertos `3307` y `3308` deben estar libres en el host. Ajusta el `docker-compose.yml` si hay conflictos.