package com.pita.waterpolo.dto.response;

public record JugadorEquipoLigaResponse(

        Integer id,
        Integer idJugador,
        String nombreJugador,
        String apellidosJugador,
        Integer idEquipoLiga,
        String nombreEquipo,
        Integer idTemporada,
        String nombreTemporada
) {
}
