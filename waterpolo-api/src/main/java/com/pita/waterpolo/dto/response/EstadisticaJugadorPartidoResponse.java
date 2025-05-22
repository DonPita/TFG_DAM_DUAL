package com.pita.waterpolo.dto.response;

public record EstadisticaJugadorPartidoResponse(
        Integer id,
        Integer idJugador,
        String nombreJugador,
        Integer idPartido,
        Integer goles,
        Integer golesPenalti,
        Integer golesTandaPenalti,
        Integer amarillas,
        Integer rojas,
        Integer expulsiones,
        Integer expulsionSustitucionDefinitiva,
        Integer expulsionBrutalidad,
        Integer expulsionSustitucionNoDefinitiva,
        Integer expulsionesPenalti,
        Integer faltasPenalti,
        Integer penaltisFallados,
        Integer otros,
        Integer tiemposMuertos,
        Integer juegoLimpio,
        Integer mvp
) {
}
