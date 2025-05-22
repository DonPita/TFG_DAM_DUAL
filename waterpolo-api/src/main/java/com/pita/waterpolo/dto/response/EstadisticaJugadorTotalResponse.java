package com.pita.waterpolo.dto.response;

public record EstadisticaJugadorTotalResponse(
        Integer id,
        Integer idJugador,
        String nombreJugador,
        Integer idTemporada,
        String nombreTemporada,
        Integer golesTotales,
        Integer golesPenaltiTotales,
        Integer golesTandaPenaltiTotales,
        Integer tarAmarillaTotales,
        Integer tarRojaTotales,
        Integer expulsionesTotales,
        Integer expulsionesSustitucionDTotales,
        Integer expulsionesBrutalidadTotales,
        Integer expulsionesSustitucionNdTotales,
        Integer expulsionesPenaltiTotales,
        Integer faltasPenaltiTotales,
        Integer penaltiFalladosTotales,
        Integer otrosTotales,
        Integer tiemposMuertosTotales,
        Integer juegoLimpioTotales,
        Integer mvpTotales
) {
}
