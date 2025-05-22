package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.EquipoLigaRequest;
import com.pita.waterpolo.dto.response.EquipoLigaResponse;
import com.pita.waterpolo.entity.Equipo;
import com.pita.waterpolo.entity.EquipoLiga;
import com.pita.waterpolo.entity.Liga;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EquipoLigaService {

    List<EquipoLigaResponse> findAll();

    Optional<EquipoLigaResponse> findById(Integer id);
    Optional<EquipoLiga> findEntityById(Integer id);

    List<EquipoLigaResponse> findByEquipo(Integer idEquipo);
    List<EquipoLigaResponse> findByLiga(Integer idLiga);

    EquipoLigaResponse save(EquipoLigaRequest equipoLigaRequest, Equipo equipo, Liga liga);

    EquipoLigaResponse update(EquipoLigaRequest equipoLigaRequest, Equipo equipo, Liga liga, Optional<EquipoLiga> equipoLigaOptional);

    void delete(Integer id);
}
