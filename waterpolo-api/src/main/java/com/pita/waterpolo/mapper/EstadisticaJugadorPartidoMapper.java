package com.pita.waterpolo.mapper;

import com.pita.waterpolo.dto.request.EstadisticaJugadorPartidoRequest;
import com.pita.waterpolo.dto.response.EstadisticaJugadorPartidoResponse;
import com.pita.waterpolo.entity.EstadisticaJugadorPartido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EstadisticaJugadorPartidoMapper {

    @Mappings({
            @Mapping(source = "idJugador", target = "jugador.id"),
            @Mapping(source = "idPartido", target = "partido.id")
    })
    EstadisticaJugadorPartido toEstadisticaJugadorPartido(EstadisticaJugadorPartidoRequest estadisticaJugadorPartidoRequest);

    @Mappings({
            @Mapping(source = "jugador.id", target = "idJugador"),
            @Mapping(target = "nombreJugador",
                    expression = "java(estadisticaJugadorPartido.getJugador().getNombre() + ' ' + estadisticaJugadorPartido.getJugador().getApellidos())"),
            @Mapping(source = "partido.id", target = "idPartido")
    })
    EstadisticaJugadorPartidoResponse toResponse(EstadisticaJugadorPartido estadisticaJugadorPartido);
}
