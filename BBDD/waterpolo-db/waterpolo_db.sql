CREATE DATABASE IF NOT EXISTS waterpolo_db;
USE waterpolo_db;
SET default_storage_engine=INNODB;

-- Eliminar tablas en orden inverso de dependencia
DROP TABLE IF EXISTS clasificacion_liga_temporada;
DROP TABLE IF EXISTS estadistica_jugador_total;
DROP TABLE IF EXISTS estadistica_jugador_partido;
DROP TABLE IF EXISTS partido;
DROP TABLE IF EXISTS jugador_equipo_liga;
DROP TABLE IF EXISTS jugador;
DROP TABLE IF EXISTS equipo_liga;
DROP TABLE IF EXISTS equipo;
DROP TABLE IF EXISTS liga;
DROP TABLE IF EXISTS temporada;

-- Tabla de temporadas
CREATE TABLE IF NOT EXISTS temporada (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL
);

-- Tabla de ligas
CREATE TABLE IF NOT EXISTS liga (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE,
    activo BOOLEAN DEFAULT TRUE
);

-- Tabla de equipos
CREATE TABLE IF NOT EXISTS equipo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE,
    ciudad VARCHAR(255) NOT NULL,
    fecha_fundacion DATE NOT NULL
);

-- Tabla intermedia: equipo_liga
CREATE TABLE IF NOT EXISTS equipo_liga (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_equipo INT NOT NULL,
    id_liga INT NOT NULL,
    nombre_equipo_liga VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_equipo) REFERENCES equipo(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_liga) REFERENCES liga(id) ON DELETE CASCADE ON UPDATE CASCADE
);
-- UNIQUE (id_equipo, id_liga)


-- Tabla de jugadores
CREATE TABLE IF NOT EXISTS jugador (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255) NOT NULL,
    fecha_nac DATE NOT NULL
);

-- Tabla intermedia: jugador_equipo_liga
CREATE TABLE IF NOT EXISTS jugador_equipo_liga (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_jugador INT NOT NULL,
    id_equipo_liga INT NOT NULL,
    id_temporada INT NOT NULL,
    FOREIGN KEY (id_jugador) REFERENCES jugador(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_equipo_liga) REFERENCES equipo_liga(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_temporada) REFERENCES temporada(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE (id_jugador, id_equipo_liga, id_temporada)
);

-- Tabla de partidos
CREATE TABLE IF NOT EXISTS partido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_temporada INT NOT NULL,
    jornada INT NOT NULL,
    fecha DATETIME NOT NULL,
    id_equipo_liga_local INT NOT NULL,
    id_equipo_liga_visitante INT NOT NULL,
    goles_local INT DEFAULT 0,
    goles_visitante INT DEFAULT 0,
    FOREIGN KEY (id_temporada) REFERENCES temporada(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_equipo_liga_local) REFERENCES equipo_liga(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_equipo_liga_visitante) REFERENCES equipo_liga(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY unique_equipo_jornada_local (id_temporada, jornada, id_equipo_liga_local),
    UNIQUE KEY unique_equipo_jornada_visitante (id_temporada, jornada, id_equipo_liga_visitante)
);

-- Tabla de estadísticas por partido
CREATE TABLE IF NOT EXISTS estadistica_jugador_partido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_jugador INT NOT NULL,
    id_partido INT NOT NULL,
    goles INT DEFAULT 0,
    goles_penalti INT DEFAULT 0,
    goles_tanda_penalti INT DEFAULT 0,
    tar_amarilla INT DEFAULT 0,
    tar_roja INT DEFAULT 0,
    expulsiones INT DEFAULT 0,
    expulsiones_sustitucion_d INT DEFAULT 0,
    expulsiones_brutalidad INT DEFAULT 0,
    expulsiones_sustitucion_nd INT DEFAULT 0,
    expulsiones_penalti INT DEFAULT 0,
    faltas_penalti INT DEFAULT 0,
    penalti_fallados INT DEFAULT 0,
    otros INT DEFAULT 0,
    tiempos_muertos INT DEFAULT 0,
    juego_limpio INT DEFAULT 0,
    mvp INT DEFAULT 0,
    FOREIGN KEY (id_jugador) REFERENCES jugador (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_partido) REFERENCES partido (id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE (id_jugador, id_partido)
);

-- Tabla de estadísticas totales por temporada
CREATE TABLE IF NOT EXISTS estadistica_jugador_total (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_jugador INT NOT NULL,
    id_temporada INT NOT NULL,
    goles_totales INT DEFAULT 0,
    goles_penalti_totales INT DEFAULT 0,
    goles_tanda_penalti_totales INT DEFAULT 0,
    tar_amarilla_totales INT DEFAULT 0,
    tar_roja_totales INT DEFAULT 0,
    expulsiones_totales INT DEFAULT 0,
    expulsiones_sustitucion_d_totales INT DEFAULT 0,
    expulsiones_brutalidad_totales INT DEFAULT 0,
    expulsiones_sustitucion_nd_totales INT DEFAULT 0,
    expulsiones_penalti_totales INT DEFAULT 0,
    faltas_penalti_totales INT DEFAULT 0,
    penalti_fallados_totales INT DEFAULT 0,
    otros_totales INT DEFAULT 0,
    tiempos_muertos_totales INT DEFAULT 0,
    juego_limpio_totales INT DEFAULT 0,
    mvp_totales INT DEFAULT 0,
    FOREIGN KEY (id_jugador) REFERENCES jugador (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_temporada) REFERENCES temporada (id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE (id_jugador, id_temporada)
);

-- Tabla de clasificación por liga, temporada y jornada
CREATE TABLE IF NOT EXISTS clasificacion_liga_temporada (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_equipo_liga INT NOT NULL,
    id_temporada INT NOT NULL,
    jornada INT NOT NULL,  
    puntos INT DEFAULT 0,
    partidos_jugados INT DEFAULT 0,
    victorias INT DEFAULT 0,
    derrotas INT DEFAULT 0,
    empates INT DEFAULT 0,
    goles_a_favor INT DEFAULT 0,
    goles_en_contra INT DEFAULT 0,
    diferencia_goles INT DEFAULT 0,
    FOREIGN KEY (id_equipo_liga) REFERENCES equipo_liga(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_temporada) REFERENCES temporada(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE (id_equipo_liga, id_temporada, jornada)  -- Nueva clave única
);

-- Índices para optimizar consultas
CREATE INDEX idx_jel_id_jugador ON jugador_equipo_liga(id_jugador);
CREATE INDEX idx_jel_id_equipo_liga ON jugador_equipo_liga(id_equipo_liga);
CREATE INDEX idx_equipo_liga_activo ON equipo_liga (activo);
CREATE INDEX idx_jel_id_temporada ON jugador_equipo_liga(id_temporada);
CREATE INDEX idx_el_id_equipo ON equipo_liga(id_equipo);
CREATE INDEX idx_el_nombre_equipo_liga ON equipo_liga(nombre_equipo_liga);
CREATE INDEX idx_ejp_id_jugador ON estadistica_jugador_partido(id_jugador);
CREATE INDEX idx_ejp_id_partido ON estadistica_jugador_partido(id_partido);
CREATE INDEX idx_ejt_id_jugador ON estadistica_jugador_total(id_jugador);
CREATE INDEX idx_ejt_id_temporada ON estadistica_jugador_total(id_temporada);
CREATE INDEX idx_clt_id_equipo_liga ON clasificacion_liga_temporada(id_equipo_liga);
CREATE INDEX idx_clt_id_temporada ON clasificacion_liga_temporada(id_temporada);

-- Triggers
DELIMITER //

-- Trigger para desactivar combinaciones en equipo_liga si la liga se desactiva
CREATE TRIGGER after_update_liga_inactiva
AFTER UPDATE ON liga
FOR EACH ROW
BEGIN
    IF NEW.activo = FALSE AND OLD.activo = TRUE THEN
        UPDATE equipo_liga 
        SET activo = FALSE
        WHERE id_liga = NEW.id;
    END IF;
END;//

-- Trigger para actualizar estadísticas totales de jugadores
CREATE TRIGGER after_insert_estadistica_jugador_partido
AFTER INSERT ON estadistica_jugador_partido
FOR EACH ROW
BEGIN
    INSERT INTO estadistica_jugador_total (
        id_jugador, id_temporada, goles_totales, goles_penalti_totales,
        goles_tanda_penalti_totales, tar_amarilla_totales, tar_roja_totales,
        expulsiones_totales, expulsiones_sustitucion_d_totales,
        expulsiones_brutalidad_totales, expulsiones_sustitucion_nd_totales,
        expulsiones_penalti_totales, faltas_penalti_totales,
        penalti_fallados_totales, otros_totales, tiempos_muertos_totales,
        juego_limpio_totales, mvp_totales
    )
    SELECT
        NEW.id_jugador, p.id_temporada, NEW.goles, NEW.goles_penalti,
        NEW.goles_tanda_penalti, NEW.tar_amarilla, NEW.tar_roja, NEW.expulsiones,
        NEW.expulsiones_sustitucion_d, NEW.expulsiones_brutalidad,
        NEW.expulsiones_sustitucion_nd, NEW.expulsiones_penalti, NEW.faltas_penalti,
        NEW.penalti_fallados, NEW.otros, NEW.tiempos_muertos, NEW.juego_limpio,
        NEW.mvp
    FROM partido p
    WHERE p.id = NEW.id_partido
    ON DUPLICATE KEY UPDATE
                        goles_totales = goles_totales + NEW.goles,
                        goles_penalti_totales = goles_penalti_totales + NEW.goles_penalti,
                        goles_tanda_penalti_totales = goles_tanda_penalti_totales + NEW.goles_tanda_penalti,
                        tar_amarilla_totales = tar_amarilla_totales + NEW.tar_amarilla,
                        tar_roja_totales = tar_roja_totales + NEW.tar_roja,
                        expulsiones_totales = expulsiones_totales + NEW.expulsiones,
                        expulsiones_sustitucion_d_totales = expulsiones_sustitucion_d_totales + NEW.expulsiones_sustitucion_d,
                        expulsiones_brutalidad_totales = expulsiones_brutalidad_totales + NEW.expulsiones_brutalidad,
                        expulsiones_sustitucion_nd_totales = expulsiones_sustitucion_nd_totales + NEW.expulsiones_sustitucion_nd,
                        expulsiones_penalti_totales = expulsiones_penalti_totales + NEW.expulsiones_penalti,
                        faltas_penalti_totales = faltas_penalti_totales + NEW.faltas_penalti,
                        penalti_fallados_totales = penalti_fallados_totales + NEW.penalti_fallados,
                        otros_totales = otros_totales + NEW.otros,
                        tiempos_muertos_totales = tiempos_muertos_totales + NEW.tiempos_muertos,
                        juego_limpio_totales = juego_limpio_totales + NEW.juego_limpio,
                        mvp_totales = mvp_totales + NEW.mvp;
END;//

-- Trigger para actualizar la clasificación tras insertar un partido
CREATE TRIGGER after_insert_partido
    AFTER INSERT ON partido
    FOR EACH ROW
BEGIN
    DECLARE puntos_ant_local INT DEFAULT 0;
    DECLARE partidos_ant_local INT DEFAULT 0;
    DECLARE victorias_ant_local INT DEFAULT 0;
    DECLARE derrotas_ant_local INT DEFAULT 0;
    DECLARE empates_ant_local INT DEFAULT 0;
    DECLARE goles_favor_ant_local INT DEFAULT 0;
    DECLARE goles_contra_ant_local INT DEFAULT 0;

    DECLARE puntos_ant_visitante INT DEFAULT 0;
    DECLARE partidos_ant_visitante INT DEFAULT 0;
    DECLARE victorias_ant_visitante INT DEFAULT 0;
    DECLARE derrotas_ant_visitante INT DEFAULT 0;
    DECLARE empates_ant_visitante INT DEFAULT 0;
    DECLARE goles_favor_ant_visitante INT DEFAULT 0;
    DECLARE goles_contra_ant_visitante INT DEFAULT 0;

    -- Obtener estadísticas acumuladas de la jornada anterior para el equipo local
    SELECT puntos, partidos_jugados, victorias, derrotas, empates, goles_a_favor, goles_en_contra
    INTO puntos_ant_local, partidos_ant_local, victorias_ant_local, derrotas_ant_local, 
         empates_ant_local, goles_favor_ant_local, goles_contra_ant_local
    FROM clasificacion_liga_temporada
    WHERE id_equipo_liga = NEW.id_equipo_liga_local 
      AND id_temporada = NEW.id_temporada 
      AND jornada = (SELECT MAX(jornada) 
                     FROM clasificacion_liga_temporada 
                     WHERE id_equipo_liga = NEW.id_equipo_liga_local 
                       AND id_temporada = NEW.id_temporada 
                       AND jornada < NEW.jornada);

    -- Obtener estadísticas acumuladas de la jornada anterior para el equipo visitante
    SELECT puntos, partidos_jugados, victorias, derrotas, empates, goles_a_favor, goles_en_contra
    INTO puntos_ant_visitante, partidos_ant_visitante, victorias_ant_visitante, derrotas_ant_visitante, 
         empates_ant_visitante, goles_favor_ant_visitante, goles_contra_ant_visitante
    FROM clasificacion_liga_temporada
    WHERE id_equipo_liga = NEW.id_equipo_liga_visitante 
      AND id_temporada = NEW.id_temporada 
      AND jornada = (SELECT MAX(jornada) 
                     FROM clasificacion_liga_temporada 
                     WHERE id_equipo_liga = NEW.id_equipo_liga_visitante 
                       AND id_temporada = NEW.id_temporada 
                       AND jornada < NEW.jornada);

    -- Equipo local
    INSERT INTO clasificacion_liga_temporada (
        id_equipo_liga, id_temporada, jornada, puntos, partidos_jugados, victorias, derrotas,
        empates, goles_a_favor, goles_en_contra, diferencia_goles
    )
    VALUES (
        NEW.id_equipo_liga_local,
        NEW.id_temporada,
        NEW.jornada,
        puntos_ant_local + CASE
            WHEN NEW.goles_local > NEW.goles_visitante THEN 3
            WHEN NEW.goles_local = NEW.goles_visitante THEN 1
            ELSE 0
        END,
        partidos_ant_local + 1,
        victorias_ant_local + IF(NEW.goles_local > NEW.goles_visitante, 1, 0),
        derrotas_ant_local + IF(NEW.goles_local < NEW.goles_visitante, 1, 0),
        empates_ant_local + IF(NEW.goles_local = NEW.goles_visitante, 1, 0),
        goles_favor_ant_local + NEW.goles_local,
        goles_contra_ant_local + NEW.goles_visitante,
        (goles_favor_ant_local + NEW.goles_local) - (goles_contra_ant_local + NEW.goles_visitante)
    )
    ON DUPLICATE KEY UPDATE
        puntos = puntos_ant_local + CASE
            WHEN NEW.goles_local > NEW.goles_visitante THEN 3
            WHEN NEW.goles_local = NEW.goles_visitante THEN 1
            ELSE 0
        END,
        partidos_jugados = partidos_ant_local + 1,
        victorias = victorias_ant_local + IF(NEW.goles_local > NEW.goles_visitante, 1, 0),
        derrotas = derrotas_ant_local + IF(NEW.goles_local < NEW.goles_visitante, 1, 0),
        empates = empates_ant_local + IF(NEW.goles_local = NEW.goles_visitante, 1, 0),
        goles_a_favor = goles_favor_ant_local + NEW.goles_local,
        goles_en_contra = goles_contra_ant_local + NEW.goles_visitante,
        diferencia_goles = (goles_favor_ant_local + NEW.goles_local) - (goles_contra_ant_local + NEW.goles_visitante);

    -- Equipo visitante
    INSERT INTO clasificacion_liga_temporada (
        id_equipo_liga, id_temporada, jornada, puntos, partidos_jugados, victorias, derrotas,
        empates, goles_a_favor, goles_en_contra, diferencia_goles
    )
    VALUES (
        NEW.id_equipo_liga_visitante,
        NEW.id_temporada,
        NEW.jornada,
        puntos_ant_visitante + CASE
            WHEN NEW.goles_visitante > NEW.goles_local THEN 3
            WHEN NEW.goles_visitante = NEW.goles_local THEN 1
            ELSE 0
        END,
        partidos_ant_visitante + 1,
        victorias_ant_visitante + IF(NEW.goles_visitante > NEW.goles_local, 1, 0),
        derrotas_ant_visitante + IF(NEW.goles_visitante < NEW.goles_local, 1, 0),
        empates_ant_visitante + IF(NEW.goles_visitante = NEW.goles_local, 1, 0),
        goles_favor_ant_visitante + NEW.goles_visitante,
        goles_contra_ant_visitante + NEW.goles_local,
        (goles_favor_ant_visitante + NEW.goles_visitante) - (goles_contra_ant_visitante + NEW.goles_local)
    )
    ON DUPLICATE KEY UPDATE
        puntos = puntos_ant_visitante + CASE
            WHEN NEW.goles_visitante > NEW.goles_local THEN 3
            WHEN NEW.goles_visitante = NEW.goles_local THEN 1
            ELSE 0
        END,
        partidos_jugados = partidos_ant_visitante + 1,
        victorias = victorias_ant_visitante + IF(NEW.goles_visitante > NEW.goles_local, 1, 0),
        derrotas = derrotas_ant_visitante + IF(NEW.goles_visitante < NEW.goles_local, 1, 0),
        empates = empates_ant_visitante + IF(NEW.goles_visitante = NEW.goles_local, 1, 0),
        goles_a_favor = goles_favor_ant_visitante + NEW.goles_visitante,
        goles_en_contra = goles_contra_ant_visitante + NEW.goles_local,
        diferencia_goles = (goles_favor_ant_visitante + NEW.goles_visitante) - (goles_contra_ant_visitante + NEW.goles_local);
END;//

DELIMITER ;
