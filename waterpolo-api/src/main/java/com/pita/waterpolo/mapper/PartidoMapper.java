package com.pita.waterpolo.mapper;

import com.pita.waterpolo.dto.request.PartidoRequest;
import com.pita.waterpolo.dto.response.PartidoResponse;
import com.pita.waterpolo.entity.Partido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PartidoMapper {

    @Mappings({
            @Mapping(target = "temporada.id", source = "idTemporada"),
            @Mapping(target = "equipoLocal.id", source = "idEquipoLigaLocal"),
            @Mapping(target = "equipoVisitante.id", source = "idEquipoLigaVisitante")
    })
    Partido toEntity(PartidoRequest partidoRequest);

    @Mappings({
            @Mapping(source = "temporada.id", target = "idTemporada"),
            @Mapping(source = "temporada.nombre", target = "nombreTemporada"),
            @Mapping(source = "equipoLocal.id", target = "idEquipoLigaLocal"),
            @Mapping(source = "equipoLocal.nombre", target = "nombreEquipoLigaLocal"),
            @Mapping(source = "equipoVisitante.id", target = "idEquipoLigaVisitante"),
            @Mapping(source = "equipoVisitante.nombre", target = "nombreEquipoLigaVisitante")
    })
    PartidoResponse toResponse(Partido partido);
}
