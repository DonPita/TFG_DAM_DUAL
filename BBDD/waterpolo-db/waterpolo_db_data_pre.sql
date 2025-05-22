USE waterpolo_db;

-- Temporadas
INSERT INTO temporada (nombre, fecha_inicio, fecha_fin) VALUES
    ('2023/2024', '2023-09-01', '2024-06-30'),
    ('2024/2025', '2024-09-01', '2025-06-30');

-- Ligas
INSERT INTO liga (nombre, activo) VALUES
    ('Sin liga', TRUE),
    ('Liga Absoluta Masculina', TRUE),
    ('Liga Absoluta Femenina', TRUE),
    ('Liga Juvenil Masculina', TRUE),
    ('Liga Cadete Mixta', TRUE),
    ('Liga Infantil Mixta', TRUE),
    ('Liga Alevín Mixta', TRUE),
    ('Liga Benjamín Mixta', TRUE);

-- Equipos
INSERT INTO equipo (nombre, ciudad, fecha_fundacion) VALUES
    ('Sin equipo', 'Sin equipo', '2000-01-01'),
    ('Club Waterpolo Lugo', 'Lugo', '2018-09-01'),
    ('Club Waterpolo Santiago', 'Santiago', '2004-05-15'),
    ('Club Marina Ferrol', 'Ferrol', '1995-03-10'),
    ('Club Waterpolo Pabellón Ourense', 'Ourense', '2000-07-15'),
    ('Real Club Náutico de Vigo', 'Vigo', '1980-11-20'),
    ('Club Waterpolo Pontevedra', 'Pontevedra', '2010-02-05'),
    ('Club Waterpolo Coruña Rías Altas', 'A Coruña', '1998-06-25'),
    ('Club Waterpolo As Mariñas', 'Betanzos', '2015-09-12');

-- Equipo_liga 
-- ID ligas: 1=Sin liga, 2=Absoluta Masculina, 3=Absoluta Femenina, 4=Juvenil Masculina, 
--           5=Cadete Mixta, 6=Infantil Mixta, 7=Alevín Mixta, 8=Benjamín Mixta
-- ID equipos: 1=Sin equipo, 2=Lugo, 3=Santiago, 4=Ferrol, 5=Ourense, 6=Vigo, 7=Pontevedra, 8=Coruña, 9=As Mariñas
INSERT INTO equipo_liga (id_equipo, id_liga, nombre_equipo_liga, activo) VALUES
    -- Sin equipo
    (1, 1, 'Sin equipo - Sin liga', TRUE),
    -- Club Waterpolo Lugo
    (2, 2, 'CW Lugo Absoluto Masculino', TRUE),
    (2, 3, 'CW Lugo Absoluto Femenino', TRUE),
    (2, 4, 'CW Lugo Juvenil', FALSE),
    (2, 5, 'CW Lugo Cadete', TRUE),
    (2, 6, 'CW Lugo Infantil', TRUE),
    (2, 7, 'CW Lugo Alevín', FALSE),
    (2, 8, 'CW Lugo Benjamín', FALSE),
    -- Club Waterpolo Santiago
    (3, 2, 'CW Santiago Absoluto Masculino', TRUE),
    (3, 3, 'CW Santiago Absoluto Femenino', TRUE),
    (3, 4, 'CW Santiago Juvenil', TRUE),
    (3, 5, 'CW Santiago Cadete', TRUE),
    (3, 6, 'CW Santiago Infantil', TRUE),
    (3, 7, 'CW Santiago Alevín', TRUE),
    (3, 8, 'CW Santiago Benjamín', TRUE),
    -- Club Marina Ferrol
    (4, 2, 'CM Ferrol Absoluto Masculino', TRUE),
    (4, 3, 'CM Ferrol Absoluto Femenino', FALSE),
    (4, 4, 'CM Ferrol Juvenil', TRUE),
    (4, 5, 'CM Ferrol Cadete', TRUE),
    (4, 6, 'CM Ferrol Infantil', TRUE),
    (4, 7, 'CM Ferrol Alevín', TRUE),
    (4, 8, 'CM Ferrol Benjamín', FALSE),
    -- Club Waterpolo Pabellón Ourense
    (5, 2, 'CWP Ourense Absoluto Masculino', TRUE),
    (5, 3, 'CWP Ourense Absoluto Femenino', FALSE),
    (5, 4, 'CWP Ourense Juvenil', FALSE),
    (5, 5, 'CWP Ourense Cadete', FALSE),
    (5, 6, 'CWP Ourense Infantil', FALSE),
    (5, 7, 'CWP Ourense Alevín', FALSE),
    (5, 8, 'CWP Ourense Benjamín', FALSE),
    -- Real Club Náutico de Vigo
    (6, 2, 'RCN Vigo Absoluto Masculino', TRUE),
    (6, 3, 'RCN Vigo Absoluto Femenino', TRUE),
    (6, 4, 'RCN Vigo Juvenil', TRUE),
    (6, 5, 'RCN Vigo Cadete', TRUE),
    (6, 6, 'RCN Vigo Infantil', TRUE),
    (6, 7, 'RCN Vigo Alevín', TRUE),
    (6, 8, 'RCN Vigo Benjamín', FALSE),
    -- Club Waterpolo Pontevedra
    (7, 2, 'CW Pontevedra Absoluto Masculino', TRUE),
    (7, 3, 'CW Pontevedra Absoluto Femenino', FALSE),
    (7, 4, 'CW Pontevedra Juvenil', TRUE),
    (7, 5, 'CW Pontevedra Cadete', TRUE),
    (7, 6, 'CW Pontevedra Infantil', TRUE),
    (7, 7, 'CW Pontevedra Alevín', TRUE),
    (7, 8, 'CW Pontevedra Benjamín', TRUE),
    -- Club Waterpolo Coruña Rías Altas
    (8, 2, 'CWC Rías Altas Absoluto Masculino', TRUE),
    (8, 3, 'CWC Rías Altas Absoluto Femenino', TRUE),
    (8, 4, 'CWC Rías Altas Juvenil', TRUE),
    (8, 5, 'CWC Rías Altas Cadete', TRUE),
    (8, 6, 'CWC Rías Altas Infantil', TRUE),
    (8, 7, 'CWC Rías Altas Alevín', TRUE),
    (8, 8, 'CWC Rías Altas Benjamín', FALSE),
    -- Club Waterpolo As Mariñas
    (9, 2, 'CW As Mariñas Absoluto Masculino', FALSE),
    (9, 3, 'CW As Mariñas Absoluto Femenino', TRUE),
    (9, 4, 'CW As Mariñas Juvenil', FALSE),
    (9, 5, 'CW As Mariñas Cadete', TRUE),
    (9, 6, 'CW As Mariñas Infantil', TRUE),
    (9, 7, 'CW As Mariñas Alevín', TRUE),
    (9, 8, 'CW As Mariñas Benjamín', FALSE);

INSERT INTO clasificacion_liga_temporada (
    id_equipo_liga,
    id_temporada,
    jornada,
    puntos,
    partidos_jugados,
    victorias,
    derrotas,
    empates,
    goles_a_favor,
    goles_en_contra,
    diferencia_goles
)
SELECT
    el.id,
    2 AS id_temporada,
    0 AS jornada,
    0 AS puntos,
    0 AS partidos_jugados,
    0 AS victorias,
    0 AS derrotas,
    0 AS empates,
    0 AS goles_a_favor,
    0 AS goles_en_contra,
    0 AS diferencia_goles
FROM equipo_liga el
WHERE el.id_liga = 5 AND el.activo = TRUE;
-- -- Jugadores
-- INSERT INTO jugador (nombre, apellidos, fecha_nac) VALUES
--     ('Manuel', 'Pérez López', '2008-04-10'),
--     ('Carlos', 'Gómez Rodríguez', '2009-07-23');
--
-- -- Jugador_equipo_liga (usamos temporada 2024/2025 por ahora, ID=2)
-- INSERT INTO jugador_equipo_liga (id_jugador, id_equipo_liga, id_temporada) VALUES
--     (1, 5, 2), -- Manuel en Lugo Cadete, 2024/2025
--     (1, 6, 2), -- Manuel en Lugo Infantil, 2024/2025
--     (2, 6, 2), -- Carlos en Lugo Infantil, 2024/2025
--     (1, 11, 2); -- Manuel en Santiago Juvenil, 2024/2025

-- -- Partidos (usamos temporada 2024/2025 por ahora, ID=2)
-- INSERT INTO partido (id_temporada, fecha, id_equipo_liga_local, id_equipo_liga_visitante, goles_local, goles_visitante) VALUES
--     (2, '2025-03-20 18:00:00', 5, 12, 5, 3); -- Lugo Cadete (ID=5) vs Santiago Cadete (ID=11)
--
-- -- Estadísticas jugador partido
-- INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, expulsiones, asistencias, minutos_jugados) VALUES
--     (1, 1, 2, 1, 1, 20), -- Manuel
--     (2, 1, 1, 0, 0, 15); -- Carlos
