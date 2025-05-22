package com.pita.waterpolo.mapper;

import com.pita.waterpolo.dto.request.EquipoRequest;
import com.pita.waterpolo.dto.response.EquipoResponse;
import com.pita.waterpolo.entity.Equipo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EquipoMapper {

    Equipo toEquipo(EquipoRequest equipoRequest);
    EquipoResponse toEquipoResponse(Equipo equipo);
}
