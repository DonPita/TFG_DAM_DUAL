package com.pita.waterpolo.dto.response;

public record ClasificacionLigaTemporadaResponse(
        Integer id,
        Integer idEquipoLiga,
        String nombreEquipoLiga,
        Integer idTemporada,
        String nombreTemporada,
        Integer jornada,
        Integer puntos,
        Integer partidosJugados,
        Integer victorias,
        Integer derrotas,
        Integer empates,
        Integer golesAFavor,
        Integer golesEnContra,
        Integer diferenciaGoles
) {
}
