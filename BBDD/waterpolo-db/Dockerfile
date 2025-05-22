# Imagen base de MySQL
FROM mysql:8.4

# Variables de entorno para MySQL
ENV MYSQL_ROOT_PASSWORD=abc123.
ENV MYSQL_DATABASE=waterpolo_db
ENV MYSQL_ROOT_HOST=%

# Configurar UTF-8 en el servidor MySQL
COPY ./my.cnf /etc/mysql/conf.d/my.cnf

# Copiar los scripts al contenedor
COPY waterpolo_db.sql /docker-entrypoint-initdb.d/01-waterpolo_db.sql
COPY waterpolo_db_data_pre.sql /docker-entrypoint-initdb.d/02-waterpolo_db_datos.sql
COPY waterpolo_db_jugadores.sql /docker-entrypoint-initdb.d/03-waterpolo_db_jugadores.sql
COPY waterpolo_primera_vuelta_cadete_2024_2025.sql /docker-entrypoint-initdb.d/04-waterpolo_primera_vuelta_cadete_2024_2025.sql