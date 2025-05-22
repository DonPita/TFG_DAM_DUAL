CREATE DATABASE IF NOT EXISTS auth_service_db;
USE auth_service_db;
SET default_storage_engine=INNODB;

-- Eliminar tabla si existe
DROP TABLE IF EXISTS refresh_tokens;
DROP TABLE IF EXISTS usuario;

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL, -- Hasheado con bcrypt en producción
    nombre_completo VARCHAR(100),
    rol ENUM('ADMIN', 'ARBITRO', 'JUGADOR', 'USUARIO') DEFAULT 'USUARIO',
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    ultimo_acceso DATETIME DEFAULT NULL
    );

-- Tabla de Tokens
CREATE TABLE IF NOT EXISTS refresh_tokens (
    id INT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    usuario_id INT NOT NULL,
    expiracion DATETIME NOT NULL,
    valido BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    UNIQUE (token)
);

-- Datos iniciales
INSERT INTO usuario (nombre_usuario, email, contrasena, nombre_completo, rol, activo) VALUES
    ('admin', 'admin@admin.com', '$2a$10$vgrMUUrfJiopXIv0.xytdeJ9f6VYdC1P6nf4vphK.5Mc3/syy9XCG', 'Administrador', 'ADMIN', TRUE), -- Contraseña: administrador
    ('pita', 'pita@gmail.com', '$2a$10$6j.l.GO1in9CjCZ/hzU7tu3K1xl5qCcpejPM3BUzA0qquKtyFwt.u', 'Pita Bermúdez', 'JUGADOR', TRUE), -- Contraseña: pitando
    ('arbitro1', 'arbitro1@waterpolo.com', '$2a$10$Mzr8kf4pMX8FvLgx.TgpM.Xht6QLq7g93NVOREAw3HGKCFeCR5T4G', 'Árbitro Uno', 'ARBITRO', TRUE), -- Contraseña: arbitro1
    ('usuario1', 'usuario1@gmail.com', '$2a$10$K49.s0yGtuInkGP2XyuoxuS3Fh0OLSjl4ESwYJNRo5M7FLjOLnB.e', 'Usuario Uno', 'USUARIO', TRUE); -- Contraseña: usuario1