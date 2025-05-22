USE waterpolo_db;

-- Partidos de la primera vuelta (7 jornadas, 21 partidos)
INSERT INTO partido (id_temporada, jornada, fecha, id_equipo_liga_local, id_equipo_liga_visitante, goles_local, goles_visitante) VALUES
-- Jornada 1: 2024-09-07
(2, 1, '2024-09-07 16:00:00', 5, 12, 8, 6),   -- Lugo vs Santiago
(2, 1, '2024-09-07 17:00:00', 19, 33, 7, 9),  -- Ferrol vs Vigo
(2, 1, '2024-09-07 18:00:00', 40, 47, 10, 5), -- Pontevedra vs Coruña
-- Jornada 2: 2024-09-14
(2, 2, '2024-09-14 16:00:00', 12, 19, 6, 8),  -- Santiago vs Ferrol
(2, 2, '2024-09-14 17:00:00', 33, 40, 7, 7),  -- Vigo vs Pontevedra
(2, 2, '2024-09-14 18:00:00', 47, 54, 9, 6),  -- Coruña vs As Mariñas
-- Jornada 3: 2024-09-21
(2, 3, '2024-09-21 16:00:00', 5, 19, 8, 7),   -- Lugo vs Ferrol
(2, 3, '2024-09-21 17:00:00', 12, 33, 5, 9),  -- Santiago vs Vigo
(2, 3, '2024-09-21 18:00:00', 40, 54, 10, 6), -- Pontevedra vs As Mariñas
-- Jornada 4: 2024-09-28
(2, 4, '2024-09-28 16:00:00', 5, 33, 7, 8),   -- Lugo vs Vigo
(2, 4, '2024-09-28 17:00:00', 19, 47, 6, 5),  -- Ferrol vs Coruña
(2, 4, '2024-09-28 18:00:00', 12, 54, 8, 7),  -- Santiago vs As Mariñas
-- Jornada 5: 2024-10-05
(2, 5, '2024-10-05 16:00:00', 5, 40, 9, 6),   -- Lugo vs Pontevedra
(2, 5, '2024-10-05 17:00:00', 33, 47, 8, 7),  -- Vigo vs Coruña
(2, 5, '2024-10-05 18:00:00', 19, 54, 7, 6),  -- Ferrol vs As Mariñas
-- Jornada 6: 2024-10-12
(2, 6, '2024-10-12 16:00:00', 5, 47, 10, 5),  -- Lugo vs Coruña
(2, 6, '2024-10-12 17:00:00', 12, 40, 6, 8),  -- Santiago vs Pontevedra
(2, 6, '2024-10-12 18:00:00', 33, 54, 9, 7),  -- Vigo vs As Mariñas
-- Jornada 7: 2024-10-19
(2, 7, '2024-10-19 16:00:00', 5, 54, 8, 6),   -- Lugo vs As Mariñas
(2, 7, '2024-10-19 17:00:00', 12, 47, 7, 7),  -- Santiago vs Coruña
(2, 7, '2024-10-19 18:00:00', 19, 40, 6, 9);  -- Ferrol vs Pontevedra

-- Estadísticas de jugadores (4 jugadores por equipo por partido)
-- Jornada 1
-- Partido 1: Lugo (5) vs Santiago (12): 8-6
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(14, 1, 3, 1, 1, 1), (15, 1, 2, 0, 0, 0), (16, 1, 2, 1, 2, 0), (17, 1, 1, 0, 1, 0), -- Lugo
(79, 1, 2, 1, 1, 0), (80, 1, 2, 0, 0, 0), (81, 1, 1, 0, 2, 0), (82, 1, 1, 0, 1, 0); -- Santiago
-- Partido 2: Ferrol (19) vs Vigo (33): 7-9
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(157, 2, 2, 1, 1, 0), (158, 2, 2, 0, 0, 0), (159, 2, 2, 0, 2, 0), (160, 2, 1, 0, 1, 0), -- Ferrol
(248, 2, 3, 1, 0, 1), (249, 2, 3, 1, 1, 0), (250, 2, 2, 0, 2, 0), (251, 2, 1, 0, 0, 0); -- Vigo
-- Partido 3: Pontevedra (40) vs Coruña (47): 10-5
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(313, 3, 3, 1, 1, 1), (314, 3, 3, 1, 0, 0), (315, 3, 2, 0, 2, 0), (316, 3, 2, 0, 1, 0), -- Pontevedra
(404, 3, 2, 1, 1, 0), (405, 3, 1, 0, 0, 0), (406, 3, 1, 0, 2, 0), (407, 3, 1, 0, 1, 0); -- Coruña

-- Jornada 2
-- Partido 4: Santiago (12) vs Ferrol (19): 6-8
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(79, 4, 2, 0, 1, 0), (80, 4, 2, 1, 0, 0), (81, 4, 1, 0, 2, 0), (82, 4, 1, 0, 1, 0), -- Santiago
(157, 4, 3, 1, 1, 1), (158, 4, 2, 0, 0, 0), (159, 4, 2, 0, 2, 0), (160, 4, 1, 0, 0, 0); -- Ferrol
-- Partido 5: Vigo (33) vs Pontevedra (40): 7-7
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(248, 5, 2, 1, 0, 0), (249, 5, 2, 0, 1, 0), (250, 5, 2, 0, 2, 1), (251, 5, 1, 0, 0, 0), -- Vigo
(313, 5, 2, 1, 1, 0), (314, 5, 2, 0, 0, 0), (315, 5, 2, 0, 2, 0), (316, 5, 1, 0, 1, 0); -- Pontevedra
-- Partido 6: Coruña (47) vs As Mariñas (54): 9-6
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(404, 6, 3, 1, 1, 1), (405, 6, 2, 0, 0, 0), (406, 6, 2, 0, 2, 0), (407, 6, 2, 0, 1, 0), -- Coruña
(470, 6, 2, 1, 0, 0), (471, 6, 2, 0, 1, 0), (472, 6, 1, 0, 2, 0), (473, 6, 1, 0, 0, 0); -- As Mariñas

-- Jornada 3
-- Partido 7: Lugo (5) vs Ferrol (19): 8-7
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(14, 7, 3, 1, 1, 1), (15, 7, 2, 0, 0, 0), (16, 7, 2, 0, 2, 0), (17, 7, 1, 0, 1, 0), -- Lugo
(157, 7, 2, 1, 0, 0), (158, 7, 2, 0, 1, 0), (159, 7, 2, 0, 2, 0), (160, 7, 1, 0, 0, 0); -- Ferrol
-- Partido 8: Santiago (12) vs Vigo (33): 5-9
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(79, 8, 2, 0, 1, 0), (80, 8, 1, 0, 0, 0), (81, 8, 1, 0, 2, 0), (82, 8, 1, 0, 1, 0), -- Santiago
(248, 8, 3, 1, 0, 1), (249, 8, 3, 0, 1, 0), (250, 8, 2, 0, 2, 0), (251, 8, 1, 0, 0, 0); -- Vigo
-- Partido 9: Pontevedra (40) vs As Mariñas (54): 10-6
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(313, 9, 3, 1, 1, 1), (314, 9, 3, 0, 0, 0), (315, 9, 2, 0, 2, 0), (316, 9, 2, 0, 1, 0), -- Pontevedra
(470, 9, 2, 1, 0, 0), (471, 9, 2, 0, 1, 0), (472, 9, 1, 0, 2, 0), (473, 9, 1, 0, 0, 0); -- As Mariñas

-- Jornada 4
-- Partido 10: Lugo (5) vs Vigo (33): 7-8
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(14, 10, 2, 1, 1, 0), (15, 10, 2, 0, 0, 0), (16, 10, 2, 0, 2, 0), (17, 10, 1, 0, 1, 0), -- Lugo
(248, 10, 3, 1, 0, 1), (249, 10, 2, 0, 1, 0), (250, 10, 2, 0, 2, 0), (251, 10, 1, 0, 0, 0); -- Vigo
-- Partido 11: Ferrol (19) vs Coruña (47): 6-5
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(157, 11, 2, 0, 1, 1), (158, 11, 2, 0, 0, 0), (159, 11, 1, 0, 2, 0), (160, 11, 1, 0, 0, 0), -- Ferrol
(404, 11, 2, 1, 1, 0), (405, 11, 1, 0, 0, 0), (406, 11, 1, 0, 2, 0), (407, 11, 1, 0, 1, 0); -- Coruña
-- Partido 12: Santiago (12) vs As Mariñas (54): 8-7
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(79, 12, 3, 1, 1, 1), (80, 12, 2, 0, 0, 0), (81, 12, 2, 0, 2, 0), (82, 12, 1, 0, 1, 0), -- Santiago
(470, 12, 2, 1, 0, 0), (471, 12, 2, 0, 1, 0), (472, 12, 2, 0, 2, 0), (473, 12, 1, 0, 0, 0); -- As Mariñas

-- Jornada 5
-- Partido 13: Lugo (5) vs Pontevedra (40): 9-6
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(14, 13, 3, 1, 1, 1), (15, 13, 3, 0, 0, 0), (16, 13, 2, 0, 2, 0), (17, 13, 1, 0, 1, 0), -- Lugo
(313, 13, 2, 1, 0, 0), (314, 13, 2, 0, 1, 0), (315, 13, 1, 0, 2, 0), (316, 13, 1, 0, 0, 0); -- Pontevedra
-- Partido 14: Vigo (33) vs Coruña (47): 8-7
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(248, 14, 3, 1, 0, 1), (249, 14, 2, 0, 1, 0), (250, 14, 2, 0, 2, 0), (251, 14, 1, 0, 0, 0), -- Vigo
(404, 14, 2, 1, 1, 0), (405, 14, 2, 0, 0, 0), (406, 14, 2, 0, 2, 0), (407, 14, 1, 0, 1, 0); -- Coruña
-- Partido 15: Ferrol (19) vs As Mariñas (54): 7-6
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(157, 15, 2, 1, 1, 1), (158, 15, 2, 0, 0, 0), (159, 15, 2, 0, 2, 0), (160, 15, 1, 0, 0, 0), -- Ferrol
(470, 15, 2, 0, 1, 0), (471, 15, 2, 0, 0, 0), (472, 15, 1, 0, 2, 0), (473, 15, 1, 0, 1, 0); -- As Mariñas

-- Jornada 6
-- Partido 16: Lugo (5) vs Coruña (47): 10-5
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(14, 16, 4, 1, 1, 1), (15, 16, 3, 0, 0, 0), (16, 16, 2, 0, 2, 0), (17, 16, 1, 0, 1, 0), -- Lugo
(404, 16, 2, 1, 0, 0), (405, 16, 1, 0, 1, 0), (406, 16, 1, 0, 2, 0), (407, 16, 1, 0, 0, 0); -- Coruña
-- Partido 17: Santiago (12) vs Pontevedra (40): 6-8
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(79, 17, 2, 0, 1, 0), (80, 17, 2, 0, 0, 0), (81, 17, 1, 0, 2, 0), (82, 17, 1, 0, 1, 0), -- Santiago
(313, 17, 3, 1, 0, 1), (314, 17, 2, 0, 1, 0), (315, 17, 2, 0, 2, 0), (316, 17, 1, 0, 0, 0); -- Pontevedra
-- Partido 18: Vigo (33) vs As Mariñas (54): 9-7
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(248, 18, 3, 1, 0, 1), (249, 18, 3, 0, 1, 0), (250, 18, 2, 0, 2, 0), (251, 18, 1, 0, 0, 0), -- Vigo
(470, 18, 2, 1, 0, 0), (471, 18, 2, 0, 1, 0), (472, 18, 2, 0, 2, 0), (473, 18, 1, 0, 0, 0); -- As Mariñas

-- Jornada 7
-- Partido 19: Lugo (5) vs As Mariñas (54): 8-6
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(14, 19, 3, 1, 1, 1), (15, 19, 2, 0, 0, 0), (16, 19, 2, 0, 2, 0), (17, 19, 1, 0, 1, 0), -- Lugo
(470, 19, 2, 0, 0, 0), (471, 19, 2, 0, 1, 0), (472, 19, 1, 0, 2, 0), (473, 19, 1, 0, 0, 0); -- As Mariñas
-- Partido 20: Santiago (12) vs Coruña (47): 7-7
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(79, 20, 2, 1, 1, 0), (80, 20, 2, 0, 0, 0), (81, 20, 2, 0, 2, 0), (82, 20, 1, 0, 1, 0), -- Santiago
(404, 20, 2, 1, 0, 1), (405, 20, 2, 0, 1, 0), (406, 20, 2, 0, 2, 0), (407, 20, 1, 0, 0, 0); -- Coruña
-- Partido 21: Ferrol (19) vs Pontevedra (40): 6-9
INSERT INTO estadistica_jugador_partido (id_jugador, id_partido, goles, goles_penalti, expulsiones, mvp) VALUES
(157, 21, 2, 0, 1, 0), (158, 21, 2, 0, 0, 0), (159, 21, 1, 0, 2, 0), (160, 21, 1, 0, 1, 0), -- Ferrol
(313, 21, 3, 1, 0, 1), (314, 21, 3, 0, 1, 0), (315, 21, 2, 0, 2, 0), (316, 21, 1, 0, 0, 0); -- Pontevedra