package com.pita.waterpolo.mapper;

import com.pita.waterpolo.dto.request.LigaRequest;
import com.pita.waterpolo.dto.response.LigaResponse;
import com.pita.waterpolo.entity.Liga;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LigaMapper {

    Liga toEntity(LigaRequest ligaRequest);
    LigaResponse toResponse(Liga liga);
}
