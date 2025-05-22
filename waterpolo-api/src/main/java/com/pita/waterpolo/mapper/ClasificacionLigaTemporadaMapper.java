package com.pita.waterpolo.mapper;

import com.pita.waterpolo.dto.request.ClasificacionLigaTemporadaRequest;
import com.pita.waterpolo.dto.response.ClasificacionLigaTemporadaResponse;
import com.pita.waterpolo.entity.ClasificacionLigaTemporada;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ClasificacionLigaTemporadaMapper {

    @Mappings({
            @Mapping(source = "idEquipoLiga", target = "equipoLiga.id"),
            @Mapping(source = "idTemporada", target = "temporada.id")
    })
    ClasificacionLigaTemporada toEntity(ClasificacionLigaTemporadaRequest clasificacionLigaTemporadaRequest);

    @Mappings({
            @Mapping(source = "equipoLiga.id", target = "idEquipoLiga"),
            @Mapping(source = "equipoLiga.nombre", target = "nombreEquipoLiga"),
            @Mapping(source = "temporada.id", target = "idTemporada"),
            @Mapping(source = "temporada.nombre", target = "nombreTemporada")
    })
    ClasificacionLigaTemporadaResponse toResponse(ClasificacionLigaTemporada clasificacionLigaTemporada);

    @Mappings({
            @Mapping(source = "idEquipoLiga", target = "equipoLiga.id"),
            @Mapping(source = "idTemporada", target = "temporada.id"),
    })
    ClasificacionLigaTemporada toEntity(ClasificacionLigaTemporadaResponse clasificacionLigaTemporadaResponse);
}
