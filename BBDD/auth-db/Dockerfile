# Imagen base de MySQL
FROM mysql:8.4

# Variables de entorno para MySQL
ENV MYSQL_ROOT_PASSWORD=abc123.
ENV MYSQL_DATABASE=auth_service_db
ENV MYSQL_ROOT_HOST=%

# Configurar UTF-8 en el servidor MySQL
COPY ./my.cnf /etc/mysql/conf.d/my.cnf

# Copiar los scripts al contenedor
COPY auth_service_db.sql /docker-entrypoint-initdb.d/01-auth_service_db.sql