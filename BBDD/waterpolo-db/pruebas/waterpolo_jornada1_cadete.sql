USE waterpolo_db;

-- Partidos de la jornada 1, Liga Cadete Mixta, temporada 2024/2025
INSERT INTO partido (id_temporada, jornada, fecha, id_equipo_liga_local, id_equipo_liga_visitante, goles_local, goles_visitante) VALUES
(2, 1, '2024-09-07 16:00:00', 5, 12, 8, 6),   -- Lugo Cadete vs Santiago Cadete
(2, 1, '2024-09-07 17:00:00', 19, 33, 7, 9),   -- Ferrol Cadete vs Vigo Cadete
(2, 1, '2024-09-07 18:00:00', 40, 47, 10, 5);  -- Pontevedra Cadete vs Coruña Cadete
-- As Mariñas Cadete (id=54) descansa

-- Estadísticas para el partido Lugo Cadete vs Santiago Cadete (id_partido = 1, asumiendo auto-incremento)
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
-- Lugo Cadete (8 goles: 6 normales + 2 penaltis)
(14, 1, 3, 1, 1, 1), -- Lucas Pérez Fernández (MVP)
(15, 1, 2, 0, 0, 0), -- Hugo López Martínez
(16, 1, 2, 1, 2, 0), -- Martín Rodríguez Gómez
(17, 1, 1, 0, 1, 0), -- Adrián Sánchez Díaz
-- Santiago Cadete (6 goles: 5 normales + 1 penalti)
(79, 1, 2, 1, 1, 0), -- Alonso García López
(80, 1, 2, 0, 0, 0), -- Lorenzo Fernández Rodríguez
(81, 1, 1, 0, 2, 0), -- Santiago Martínez Sánchez
(82, 1, 1, 0, 1, 0); -- Tomás Gómez Díaz

-- Estadísticas para el partido Ferrol Cadete vs Vigo Cadete (id_partido = 2)
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
-- Ferrol Cadete (7 goles: 6 normales + 1 penalti)
(157, 2, 2, 1, 1, 0), -- Néstor García López
(158, 2, 2, 0, 0, 0), -- Omar Fernández Rodríguez
(159, 2, 2, 0, 2, 1), -- Pelayo Martínez Sánchez (MVP)
(160, 2, 1, 0, 1, 0), -- Quirino Gómez Díaz
-- Vigo Cadete (9 goles: 7 normales + 2 penaltis)
(248, 2, 3, 1, 0, 0), -- Nilo Pérez Fernández
(249, 2, 3, 1, 1, 0), -- Otto López Martínez
(250, 2, 2, 0, 2, 0), -- Pío Rodríguez Gómez
(251, 2, 1, 0, 0, 0); -- Quillian Sánchez Díaz

-- Estadísticas para el partido Pontevedra Cadete vs Coruña Cadete (id_partido = 3)
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
-- Pontevedra Cadete (10 goles: 8 normales + 2 penaltis)
(313, 3, 3, 1, 1, 1), -- Brais García López (MVP)
(314, 3, 3, 1, 0, 0), -- Ciro Fernández Rodríguez
(315, 3, 2, 0, 2, 0), -- Dante Martínez Sánchez
(316, 3, 2, 0, 1, 0), -- Efrén Gómez Díaz
-- Coruña Cadete (5 goles: 4 normales + 1 penalti)
(404, 3, 2, 1, 1, 0), -- Ezequiel Pérez Fernández
(405, 3, 1, 0, 0, 0), -- Fabián López Martínez
(406, 3, 1, 0, 2, 0), -- Gaspar Rodríguez Gómez
(407, 3, 1, 0, 1, 0); -- Hermes Sánchez Díaz