package com.pita.waterpolo.mapper;

import com.pita.waterpolo.dto.request.TemporadaRequest;
import com.pita.waterpolo.dto.response.TemporadaResponse;
import com.pita.waterpolo.entity.Temporada;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemporadaMapper {

    Temporada toEntity(TemporadaRequest temporadaRequest);
    TemporadaResponse toResponse(Temporada temporada);
}
