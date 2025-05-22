package com.pita.waterpolo.mapper;

import com.pita.waterpolo.dto.request.JugadorRequest;
import com.pita.waterpolo.dto.response.JugadorResponse;
import com.pita.waterpolo.entity.Jugador;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JugadorMapper {

    Jugador toEntity(JugadorRequest jugadorRequest);
    JugadorResponse toResponse(Jugador jugador);
}
