package com.pita.waterpolo.util;

public class SqlUtil {

    // Consulta para findByLigaAndTemporadaAndJornada (sin cambios, para referencia)
    public static final String FIND_CLASIFICACION_BY_LIGA_TEMPORADA_JORNADA =
            "SELECT NEW com.pita.waterpolo.entity.ClasificacionLigaTemporada(" +
                    "el.id, el, :idTemporada, :jornada, " +
                    "COALESCE(c.puntos, (SELECT c2.puntos FROM ClasificacionLigaTemporada c2 " +
                    "                    WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    "                    AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                                     WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada " +
                    "                                     AND c3.jornada < :jornada))), " +
                    "COALESCE(c.partidosJugados, (SELECT c2.partidosJugados FROM ClasificacionLigaTemporada c2 " +
                    "                             WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    "                             AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                                              WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada " +
                    "                                              AND c3.jornada < :jornada))), " +
                    "COALESCE(c.victorias, (SELECT c2.victorias FROM ClasificacionLigaTemporada c2 " +
                    "                       WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    "                       AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                                        WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada " +
                    "                                        AND c3.jornada < :jornada))), " +
                    "COALESCE(c.derrotas, (SELECT c2.derrotas FROM ClasificacionLigaTemporada c2 " +
                    "                      WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    "                      AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                                       WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada " +
                    "                                       AND c3.jornada < :jornada))), " +
                    "COALESCE(c.empates, (SELECT c2.empates FROM ClasificacionLigaTemporada c2 " +
                    "                     WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    "                     AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                                      WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada " +
                    "                                      AND c3.jornada < :jornada))), " +
                    "COALESCE(c.golesAFavor, (SELECT c2.golesAFavor FROM ClasificacionLigaTemporada c2 " +
                    "                         WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    "                         AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                                          WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada " +
                    "                                          AND c3.jornada < :jornada))), " +
                    "COALESCE(c.golesEnContra, (SELECT c2.golesEnContra FROM ClasificacionLigaTemporada c2 " +
                    "                           WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    "                           AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                                            WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada " +
                    "                                            AND c3.jornada < :jornada))), " +
                    "COALESCE(c.diferenciaGoles, (SELECT c2.diferenciaGoles FROM ClasificacionLigaTemporada c2 " +
                    "                             WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    "                             AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                                              WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada " +
                    "                                              AND c3.jornada < :jornada)))) " +
                    "FROM EquipoLiga el " +
                    "JOIN el.liga l " +
                    "LEFT JOIN ClasificacionLigaTemporada c ON c.equipoLiga.id = el.id AND c.temporada.id = :idTemporada AND c.jornada = :jornada " +
                    "WHERE l.id = :idLiga AND el.activo = true " +
                    "ORDER BY COALESCE(c.puntos, (SELECT c2.puntos FROM ClasificacionLigaTemporada c2 " +
                    "                             WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    "                             AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                                              WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada " +
                    "                                              AND c3.jornada < :jornada))) DESC, " +
                    "         COALESCE(c.diferenciaGoles, (SELECT c2.diferenciaGoles FROM ClasificacionLigaTemporada c2 " +
                    "                                     WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    "                                     AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                                                      WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada " +
                    "                                                      AND c3.jornada < :jornada))) DESC";

    // Consulta para findClasificacionGlobalByLigaAndTemporada
    public static final String FIND_CLASIFICACION_GLOBAL_BY_LIGA_TEMPORADA =
            "SELECT NEW com.pita.waterpolo.entity.ClasificacionLigaTemporada(" +
                    "el.id, el, :idTemporada, " +
                    "(SELECT MAX(c2.jornada) FROM ClasificacionLigaTemporada c2 " +
                    "               WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada), " +
                    "(SELECT c2.puntos FROM ClasificacionLigaTemporada c2 " +
                    " WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    " AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                  WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada)), " +
                    "(SELECT c2.partidosJugados FROM ClasificacionLigaTemporada c2 " +
                    " WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    " AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                  WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada)), " +
                    "(SELECT c2.victorias FROM ClasificacionLigaTemporada c2 " +
                    " WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    " AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                  WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada)), " +
                    "(SELECT c2.derrotas FROM ClasificacionLigaTemporada c2 " +
                    " WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    " AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                  WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada)), " +
                    "(SELECT c2.empates FROM ClasificacionLigaTemporada c2 " +
                    " WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    " AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                  WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada)), " +
                    "(SELECT c2.golesAFavor FROM ClasificacionLigaTemporada c2 " +
                    " WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    " AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                  WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada)), " +
                    "(SELECT c2.golesEnContra FROM ClasificacionLigaTemporada c2 " +
                    " WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    " AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                  WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada)), " +
                    "(SELECT c2.diferenciaGoles FROM ClasificacionLigaTemporada c2 " +
                    " WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    " AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                  WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada))) " +
                    "FROM EquipoLiga el " +
                    "JOIN el.liga l " +
                    "WHERE l.id = :idLiga AND el.activo = true " +
                    "ORDER BY (SELECT c2.puntos FROM ClasificacionLigaTemporada c2 " +
                    "          WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    "          AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                           WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada)) DESC, " +
                    "         (SELECT c2.diferenciaGoles FROM ClasificacionLigaTemporada c2 " +
                    "          WHERE c2.equipoLiga.id = el.id AND c2.temporada.id = :idTemporada " +
                    "          AND c2.jornada = (SELECT MAX(c3.jornada) FROM ClasificacionLigaTemporada c3 " +
                    "                           WHERE c3.equipoLiga.id = el.id AND c3.temporada.id = :idTemporada)) DESC";
}

