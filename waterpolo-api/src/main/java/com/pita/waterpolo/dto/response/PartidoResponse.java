package com.pita.waterpolo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record PartidoResponse (

        Integer id,
        Integer idTemporada,
        String nombreTemporada,
        Integer jornada,
        @JsonFormat(pattern = "HH:mm dd-MM-yyyy") LocalDateTime fecha,
        Integer idEquipoLigaLocal,
        String nombreEquipoLigaLocal,
        Integer idEquipoLigaVisitante,
        String nombreEquipoLigaVisitante,
        Integer golesLocal,
        Integer golesVisitante
) {
}
