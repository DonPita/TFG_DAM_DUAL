package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.PartidoRequest;
import com.pita.waterpolo.dto.response.PartidoResponse;
import com.pita.waterpolo.entity.EquipoLiga;
import com.pita.waterpolo.entity.Partido;
import com.pita.waterpolo.entity.Temporada;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PartidoService {

    List<PartidoResponse> findAll();
    List<PartidoResponse> findByTemporada(Temporada temporada);
    List<PartidoResponse> findPartidosByTemporadaAndJornada(Temporada temporada, Integer jornada);
    List<PartidoResponse> findPartidosByEquipoLigaAndTemporada(EquipoLiga equipoLiga, Temporada temporada);

    Optional<PartidoResponse> findById(Integer id);
    Optional<Partido> findEntityById(Integer id);

    PartidoResponse save(PartidoRequest partidoRequest);

    PartidoResponse update(PartidoRequest partidoRequest, Temporada temporada, EquipoLiga equipoLigaLocal,
                           EquipoLiga equipoLigaVisitante, Optional<Partido> partidoOptional);

    void delete(Integer id);
}
