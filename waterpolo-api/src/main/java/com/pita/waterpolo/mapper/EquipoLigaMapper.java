package com.pita.waterpolo.mapper;

import com.pita.waterpolo.dto.request.EquipoLigaRequest;
import com.pita.waterpolo.dto.response.EquipoLigaResponse;
import com.pita.waterpolo.entity.EquipoLiga;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EquipoLigaMapper {

    @Mappings({
            @Mapping(source = "idEquipo", target = "equipo.id"),
            @Mapping(source = "idLiga", target = "liga.id")
    })
    EquipoLiga toEquipoLiga(EquipoLigaRequest equipoLigaRequest);

    @Mappings({
            @Mapping(source = "equipo.id", target = "idEquipo"),
            @Mapping(source = "equipo.nombre", target = "nombreEquipo"),
            @Mapping(source = "liga.id", target = "idLiga"),
            @Mapping(source = "liga.nombre", target = "nombreLiga")
    })
    EquipoLigaResponse toEquipoLigaResponse(EquipoLiga equipoLiga);
}
