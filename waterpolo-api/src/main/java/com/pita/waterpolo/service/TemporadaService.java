package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.TemporadaRequest;
import com.pita.waterpolo.dto.response.TemporadaResponse;
import com.pita.waterpolo.entity.Temporada;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TemporadaService {

    List<TemporadaResponse> findAll();

    Optional<TemporadaResponse> findById(Integer id);

    Optional<Temporada> findEntityById(Integer id);

    TemporadaResponse save(TemporadaRequest temporadaRequest);

    TemporadaResponse update(TemporadaRequest temporadaRequest, Optional<Temporada> optionalTemporada);

    void delete(Integer id);
}
