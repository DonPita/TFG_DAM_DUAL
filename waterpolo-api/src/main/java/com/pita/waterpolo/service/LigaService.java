package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.LigaRequest;
import com.pita.waterpolo.dto.response.LigaResponse;
import com.pita.waterpolo.entity.Liga;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LigaService {

    List<LigaResponse> findAll();

    Optional<LigaResponse> findById(Integer id);

    Optional<Liga> findEntityById(Integer id);

    LigaResponse save(LigaRequest ligaRequest);

    LigaResponse update(LigaRequest ligaRequest, Optional<Liga> optionalLiga);

    void delete(Integer id);
}
