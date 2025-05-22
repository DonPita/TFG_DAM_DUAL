package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.EquipoRequest;
import com.pita.waterpolo.dto.response.EquipoResponse;
import com.pita.waterpolo.entity.Equipo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EquipoService {

    List<EquipoResponse> findAll();

    Optional<EquipoResponse> findById(Integer id);

    Optional<Equipo> findEntityById(Integer id);

    EquipoResponse save(EquipoRequest equipoRequest);

    EquipoResponse update(EquipoRequest equipoRequest, Optional<Equipo> equipoOptional);

    void delete(Integer id);
}
