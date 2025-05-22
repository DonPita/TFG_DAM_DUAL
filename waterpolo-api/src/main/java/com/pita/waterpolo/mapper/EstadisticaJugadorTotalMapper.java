package com.pita.waterpolo.mapper;

import com.pita.waterpolo.dto.request.EstadisticaJugadorTotalRequest;
import com.pita.waterpolo.dto.response.EstadisticaJugadorTotalResponse;
import com.pita.waterpolo.entity.EstadisticaJugadorTotal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EstadisticaJugadorTotalMapper {

    @Mappings({
            @Mapping(source = "idJugador", target = "jugador.id"),
            @Mapping(source = "idTemporada", target = "temporada.id")
    })
    EstadisticaJugadorTotal toEntity(EstadisticaJugadorTotalRequest estadisticaJugadorTotalRequest);

    @Mappings({
            @Mapping(source = "jugador.id", target = "idJugador"),
            @Mapping(target = "nombreJugador",
                    expression = "java(estadisticaJugadorTotal.getJugador().getNombre() + ' ' + estadisticaJugadorTotal.getJugador().getApellidos())"),
            @Mapping(source = "temporada.id", target = "idTemporada"),
            @Mapping(source = "temporada.nombre", target = "nombreTemporada")
    })
    EstadisticaJugadorTotalResponse toResponse(EstadisticaJugadorTotal estadisticaJugadorTotal);
}
