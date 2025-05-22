package com.pita.waterpolo.mapper;

import com.pita.waterpolo.dto.request.JugadorEquipoLigaRequest;
import com.pita.waterpolo.dto.response.JugadorEquipoLigaResponse;
import com.pita.waterpolo.entity.JugadorEquipoLiga;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface JugadorEquipoLigaMapper {

    @Mappings({
            @Mapping(source = "idJugador", target = "jugador.id"),
            @Mapping(source = "idEquipoLiga", target = "equipoLiga.id"),
            @Mapping(source = "idTemporada", target = "temporada.id")
    })
    JugadorEquipoLiga toEntity(JugadorEquipoLigaRequest jugadorEquipoLigaRequest);

    @Mappings({
            @Mapping(source = "jugador.id", target = "idJugador"),
            @Mapping(source = "jugador.nombre", target = "nombreJugador"),
            @Mapping(source = "jugador.apellidos", target = "apellidosJugador"),
            @Mapping(source = "equipoLiga.id", target = "idEquipoLiga"),
            @Mapping(source = "equipoLiga.nombre", target = "nombreEquipo"),
            @Mapping(source = "temporada.id", target = "idTemporada"),
            @Mapping(source = "temporada.nombre", target = "nombreTemporada")
    })
    JugadorEquipoLigaResponse toResponse(JugadorEquipoLiga jugadorEquipoLiga);
}
