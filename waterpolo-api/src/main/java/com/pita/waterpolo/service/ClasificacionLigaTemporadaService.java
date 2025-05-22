package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.ClasificacionLigaTemporadaRequest;
import com.pita.waterpolo.dto.response.ClasificacionLigaTemporadaResponse;
import com.pita.waterpolo.entity.ClasificacionLigaTemporada;
import com.pita.waterpolo.entity.EquipoLiga;
import com.pita.waterpolo.entity.Liga;
import com.pita.waterpolo.entity.Temporada;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ClasificacionLigaTemporadaService {

    List<ClasificacionLigaTemporadaResponse> findAll();

    Optional<ClasificacionLigaTemporadaResponse> findById(Integer id);
    Optional<ClasificacionLigaTemporada> findEntityById(Integer id);

    List<ClasificacionLigaTemporadaResponse> findByLigaAndTemporadaAndJornada(Liga liga, Temporada temporada, Integer jornada);

    List<ClasificacionLigaTemporadaResponse> findClasificacionGlobalByLigaAndTemporada(Liga liga, Temporada temporada);

    List<ClasificacionLigaTemporadaResponse> findByEquipoLigaAndTemporada(EquipoLiga equipoLiga, Temporada temporada);

    ClasificacionLigaTemporadaResponse save(ClasificacionLigaTemporadaRequest clasificacionLigaTemporadaRequest,
                                            Temporada temporada, EquipoLiga equipoLiga);

    ClasificacionLigaTemporadaResponse update(ClasificacionLigaTemporadaRequest clasificacionLigaTemporadaRequest,
                                              Temporada temporada, EquipoLiga equipoLiga,
                                              Optional<ClasificacionLigaTemporada> clasificacionLigaTemporadaOptional);

    void delete(Integer id);


}
