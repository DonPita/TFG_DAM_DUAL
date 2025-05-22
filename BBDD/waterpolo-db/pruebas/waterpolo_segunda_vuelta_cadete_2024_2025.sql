USE waterpolo_db;

-- Partidos de la segunda vuelta (jornadas 8-14, 21 partidos)
INSERT INTO partido (id_temporada, jornada, fecha, id_equipo_liga_local, id_equipo_liga_visitante, goles_local, goles_visitante) VALUES
-- Jornada 8: 2024-10-26
(2, 8, '2024-10-26 16:00:00', 12, 5, 6, 8),   -- Santiago vs Lugo
(2, 8, '2024-10-26 17:00:00', 33, 19, 9, 7),  -- Vigo vs Ferrol
(2, 8, '2024-10-26 18:00:00', 47, 40, 5, 10), -- Coruña vs Pontevedra
-- Jornada 9: 2024-11-02
(2, 9, '2024-11-02 16:00:00', 19, 12, 7, 6),  -- Ferrol vs Santiago
(2, 9, '2024-11-02 17:00:00', 40, 33, 8, 7),  -- Pontevedra vs Vigo
(2, 9, '2024-11-02 18:00:00', 54, 47, 6, 8),  -- As Mariñas vs Coruña
-- Jornada 10: 2024-11-09
(2, 10, '2024-11-09 16:00:00', 19, 5, 6, 9),  -- Ferrol vs Lugo
(2, 10, '2024-11-09 17:00:00', 33, 12, 8, 5), -- Vigo vs Santiago
(2, 10, '2024-11-09 18:00:00', 54, 40, 7, 9), -- As Mariñas vs Pontevedra
-- Jornada 11: 2024-11-16
(2, 11, '2024-11-16 16:00:00', 33, 5, 7, 8),  -- Vigo vs Lugo
(2, 11, '2024-11-16 17:00:00', 47, 19, 6, 7), -- Coruña vs Ferrol
(2, 11, '2024-11-16 18:00:00', 54, 12, 8, 6), -- As Mariñas vs Santiago
-- Jornada 12: 2024-11-23
(2, 12, '2024-11-23 16:00:00', 40, 5, 7, 6),  -- Pontevedra vs Lugo
(2, 12, '2024-11-23 17:00:00', 47, 33, 6, 8), -- Coruña vs Vigo
(2, 12, '2024-11-23 18:00:00', 54, 19, 6, 7), -- As Mariñas vs Ferrol
-- Jornada 13: 2024-11-30
(2, 13, '2024-11-30 16:00:00', 47, 5, 5, 10), -- Coruña vs Lugo
(2, 13, '2024-11-30 17:00:00', 40, 12, 9, 6), -- Pontevedra vs Santiago
(2, 13, '2024-11-30 18:00:00', 54, 33, 7, 8), -- As Mariñas vs Vigo
-- Jornada 14: 2024-12-07
(2, 14, '2024-12-07 16:00:00', 54, 5, 6, 8),  -- As Mariñas vs Lugo
(2, 14, '2024-12-07 17:00:00', 47, 12, 7, 7), -- Coruña vs Santiago
(2, 14, '2024-12-07 18:00:00', 40, 19, 9, 6); -- Pontevedra vs Ferrol

-- Estadísticas de jugadores (4 jugadores por equipo por partido)
-- Jornada 8
-- Partido 22: Santiago (12) vs Lugo (5): 6-8
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(79, 22, 2, 0, 1, 0), (80, 22, 2, 0, 0, 0), (81, 22, 1, 0, 2, 0), (82, 22, 1, 0, 1, 0), -- Santiago
(14, 22, 3, 1, 1, 1), (15, 22, 2, 0, 0, 0), (16, 22, 2, 0, 2, 0), (17, 22, 1, 0, 0, 0); -- Lugo
-- Partido 23: Vigo (33) vs Ferrol (19): 9-7
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(248, 23, 3, 1, 0, 1), (249, 23, 3, 0, 1, 0), (250, 23, 2, 0, 2, 0), (251, 23, 1, 0, 0, 0), -- Vigo
(157, 23, 2, 1, 1, 0), (158, 23, 2, 0, 0, 0), (159, 23, 2, 0, 2, 0), (160, 23, 1, 0, 1, 0); -- Ferrol
-- Partido 24: Coruña (47) vs Pontevedra (40): 5-10
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(404, 24, 2, 0, 1, 0), (405, 24, 1, 0, 0, 0), (406, 24, 1, 0, 2, 0), (407, 24, 1, 0, 1, 0), -- Coruña
(313, 24, 4, 1, 0, 1), (314, 24, 3, 0, 1, 0), (315, 24, 2, 0, 2, 0), (316, 24, 1, 0, 0, 0); -- Pontevedra

-- Jornada 9
-- Partido 25: Ferrol (19) vs Santiago (12): 7-6
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(157, 25, 2, 1, 1, 1), (158, 25, 2, 0, 0, 0), (159, 25, 2, 0, 2, 0), (160, 25, 1, 0, 0, 0), -- Ferrol
(79, 25, 2, 0, 1, 0), (80, 25, 2, 0, 0, 0), (81, 25, 1, 0, 2, 0), (82, 25, 1, 0, 1, 0); -- Santiago
-- Partido 26: Pontevedra (40) vs Vigo (33): 8-7
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(313, 26, 3, 1, 0, 1), (314, 26, 2, 0, 1, 0), (315, 26, 2, 0, 2, 0), (316, 26, 1, 0, 0, 0), -- Pontevedra
(248, 26, 2, 1, 0, 0), (249, 26, 2, 0, 1, 0), (250, 26, 2, 0, 2, 0), (251, 26, 1, 0, 0, 0); -- Vigo
-- Partido 27: As Mariñas (54) vs Coruña (47): 6-8
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(470, 27, 2, 0, 1, 0), (471, 27, 2, 0, 0, 0), (472, 27, 1, 0, 2, 0), (473, 27, 1, 0, 0, 0), -- As Mariñas
(404, 27, 3, 1, 1, 1), (405, 27, 2, 0, 0, 0), (406, 27, 2, 0, 2, 0), (407, 27, 1, 0, 0, 0); -- Coruña

-- Jornada 10
-- Partido 28: Ferrol (19) vs Lugo (5): 6-9
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(157, 28, 2, 0, 1, 0), (158, 28, 2, 0, 0, 0), (159, 28, 1, 0, 2, 0), (160, 28, 1, 0, 1, 0), -- Ferrol
(14, 28, 3, 1, 0, 1), (15, 28, 3, 0, 1, 0), (16, 28, 2, 0, 2, 0), (17, 28, 1, 0, 0, 0); -- Lugo
-- Partido 29: Vigo (33) vs Santiago (12): 8-5
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(248, 29, 3, 1, 0, 1), (249, 29, 2, 0, 1, 0), (250, 29, 2, 0, 2, 0), (251, 29, 1, 0, 0, 0), -- Vigo
(79, 29, 2, 0, 1, 0), (80, 29, 1, 0, 0, 0), (81, 29, 1, 0, 2, 0), (82, 29, 1, 0, 1, 0); -- Santiago
-- Partido 30: As Mariñas (54) vs Pontevedra (40): 7-9
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(470, 30, 2, 1, 0, 0), (471, 30, 2, 0, 1, 0), (472, 30, 2, 0, 2, 0), (473, 30, 1, 0, 0, 0), -- As Mariñas
(313, 30, 3, 1, 0, 1), (314, 30, 3, 0, 1, 0), (315, 30, 2, 0, 2, 0), (316, 30, 1, 0, 0, 0); -- Pontevedra

-- Jornada 11
-- Partido 31: Vigo (33) vs Lugo (5): 7-8
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(248, 31, 2, 1, 0, 0), (249, 31, 2, 0, 1, 0), (250, 31, 2, 0, 2, 0), (251, 31, 1, 0, 0, 0), -- Vigo
(14, 31, 3, 1, 1, 1), (15, 31, 2, 0, 0, 0), (16, 31, 2, 0, 2, 0), (17, 31, 1, 0, 0, 0); -- Lugo
-- Partido 32: Coruña (47) vs Ferrol (19): 6-7
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(404, 32, 2, 0, 1, 0), (405, 32, 2, 0, 0, 0), (406, 32, 1, 0, 2, 0), (407, 32, 1, 0, 1, 0), -- Coruña
(157, 32, 2, 1, 0, 1), (158, 32, 2, 0, 1, 0), (159, 32, 2, 0, 2, 0), (160, 32, 1, 0, 0, 0); -- Ferrol
-- Partido 33: As Mariñas (54) vs Santiago (12): 8-6
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(470, 33, 3, 1, 0, 1), (471, 33, 2, 0, 1, 0), (472, 33, 2, 0, 2, 0), (473, 33, 1, 0, 0, 0), -- As Mariñas
(79, 33, 2, 0, 1, 0), (80, 33, 2, 0, 0, 0), (81, 33, 1, 0, 2, 0), (82, 33, 1, 0, 1, 0); -- Santiago

-- Jornada 12
-- Partido 34: Pontevedra (40) vs Lugo (5): 7-6
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(313, 34, 2, 1, 0, 1), (314, 34, 2, 0, 1, 0), (315, 34, 2, 0, 2, 0), (316, 34, 1, 0, 0, 0), -- Pontevedra
(14, 34, 2, 0, 1, 0), (15, 34, 2, 0, 0, 0), (16, 34, 1, 0, 2, 0), (17, 34, 1, 0, 1, 0); -- Lugo
-- Partido 35: Coruña (47) vs Vigo (33): 6-8
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(404, 35, 2, 0, 1, 0), (405, 35, 2, 0, 0, 0), (406, 35, 1, 0, 2, 0), (407, 35, 1, 0, 1, 0), -- Coruña
(248, 35, 3, 1, 0, 1), (249, 35, 2, 0, 1, 0), (250, 35, 2, 0, 2, 0), (251, 35, 1, 0, 0, 0); -- Vigo
-- Partido 36: As Mariñas (54) vs Ferrol (19): 6-7
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(470, 36, 2, 0, 1, 0), (471, 36, 2, 0, 0, 0), (472, 36, 1, 0, 2, 0), (473, 36, 1, 0, 1, 0), -- As Mariñas
(157, 36, 2, 1, 0, 1), (158, 36, 2, 0, 1, 0), (159, 36, 2, 0, 2, 0), (160, 36, 1, 0, 0, 0); -- Ferrol

-- Jornada 13
-- Partido 37: Coruña (47) vs Lugo (5): 5-10
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(404, 37, 2, 0, 1, 0), (405, 37, 1, 0, 0, 0), (406, 37, 1, 0, 2, 0), (407, 37, 1, 0, 1, 0), -- Coruña
(14, 37, 4, 1, 0, 1), (15, 37, 3, 0, 1, 0), (16, 37, 2, 0, 2, 0), (17, 37, 1, 0, 0, 0); -- Lugo
-- Partido 38: Pontevedra (40) vs Santiago (12): 9-6
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(313, 38, 3, 1, 0, 1), (314, 38, 3, 0, 1, 0), (315, 38, 2, 0, 2, 0), (316, 38, 1, 0, 0, 0), -- Pontevedra
(79, 38, 2, 0, 1, 0), (80, 38, 2, 0, 0, 0), (81, 38, 1, 0, 2, 0), (82, 38, 1, 0, 1, 0); -- Santiago
-- Partido 39: As Mariñas (54) vs Vigo (33): 7-8
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(470, 39, 2, 1, 0, 0), (471, 39, 2, 0, 1, 0), (472, 39, 2, 0, 2, 0), (473, 39, 1, 0, 0, 0), -- As Mariñas
(248, 39, 3, 1, 0, 1), (249, 39, 2, 0, 1, 0), (250, 39, 2, 0, 2, 0), (251, 39, 1, 0, 0, 0); -- Vigo

-- Jornada 14
-- Partido 40: As Mariñas (54) vs Lugo (5): 6-8
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(470, 40, 2, 0, 1, 0), (471, 40, 2, 0, 0, 0), (472, 40, 1, 0, 2, 0), (473, 40, 1, 0, 1, 0), -- As Mariñas
(14, 40, 3, 1, 0, 1), (15, 40, 2, 0, 1, 0), (16, 40, 2, 0, 2, 0), (17, 40, 1, 0, 0, 0); -- Lugo
-- Partido 41: Coruña (47) vs Santiago (12): 7-7
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(404, 41, 2, 1, 0, 0), (405, 41, 2, 0, 1, 0), (406, 41, 2, 0, 2, 0), (407, 41, 1, 0, 0, 0), -- Coruña
(79, 41, 2, 1, 1, 1), (80, 41, 2, 0, 0, 0), (81, 41, 2, 0, 2, 0), (82, 41, 1, 0, 1, 0); -- Santiago
-- Partido 42: Pontevedra (40) vs Ferrol (19): 9-6
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(313, 42, 3, 1, 0, 1), (314, 42, 3, 0, 1, 0), (315, 42, 2, 0, 2, 0), (316, 42, 1, 0, 0, 0), -- Pontevedra
(157, 42, 2, 0, 1, 0), (158, 42, 2, 0, 0, 0), (159, 42, 1, 0, 2, 0), (160, 42, 1, 0, 1, 0); -- Ferrol