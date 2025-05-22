package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.LigaRequest;
import com.pita.waterpolo.dto.response.LigaResponse;
import com.pita.waterpolo.entity.Liga;
import com.pita.waterpolo.mapper.LigaMapper;
import com.pita.waterpolo.repository.LigaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LigaServiceImpl implements LigaService {

    private final LigaRepository ligaRepository;
    private final LigaMapper ligaMapper;

    public LigaServiceImpl(LigaRepository ligaRepository, LigaMapper ligaMapper) {
        this.ligaRepository = ligaRepository;
        this.ligaMapper = ligaMapper;
    }

    @Override
    public List<LigaResponse> findAll() {
        List<Liga> ligas = ligaRepository.findAll();

        List<LigaResponse> ligasDTO = ligas.stream()
                .map( liga -> ligaMapper.toResponse(liga)).toList();

        return ligasDTO;
    }

    @Override
    public Optional<LigaResponse> findById(Integer id) {
        return ligaRepository.findById(id)
                .map( liga -> ligaMapper.toResponse(liga));
    }

    @Override
    public Optional<Liga> findEntityById(Integer id) {
        return ligaRepository.findById(id);
    }

    @Override
    public LigaResponse save(LigaRequest ligaRequest) {
        Liga liga = ligaMapper.toEntity(ligaRequest);

        Liga savedLiga = ligaRepository.save(liga);

        return ligaMapper.toResponse(savedLiga);
    }

    @Override
    public LigaResponse update(LigaRequest ligaRequest, Optional<Liga> optionalLiga) {
        Liga liga = optionalLiga.get();
        liga.setNombre(ligaRequest.nombre());
        liga.setActivo(ligaRequest.activo());

        Liga updatedLiga = ligaRepository.save(liga);

        return ligaMapper.toResponse(updatedLiga);
    }

    @Override
    public void delete(Integer id) {
        ligaRepository.deleteById(id);
    }
}
