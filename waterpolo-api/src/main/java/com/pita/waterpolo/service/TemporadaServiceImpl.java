package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.TemporadaRequest;
import com.pita.waterpolo.dto.response.TemporadaResponse;
import com.pita.waterpolo.entity.Temporada;
import com.pita.waterpolo.mapper.TemporadaMapper;
import com.pita.waterpolo.repository.TemporadaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemporadaServiceImpl implements TemporadaService {

    private final TemporadaRepository temporadaRepository;
    private final TemporadaMapper temporadaMapper;

    public TemporadaServiceImpl(TemporadaRepository temporadaRepository, TemporadaMapper temporadaMapper) {
        this.temporadaRepository = temporadaRepository;
        this.temporadaMapper = temporadaMapper;
    }

    @Override
    public List<TemporadaResponse> findAll() {
        List<Temporada> temporadas = temporadaRepository.findAll();

        List<TemporadaResponse> temporadaResponseList = temporadas.stream()
                .map(temporada -> temporadaMapper.toResponse(temporada))
                .toList();

        return temporadaResponseList;
    }

    @Override
    public Optional<TemporadaResponse> findById(Integer id) {
        return temporadaRepository.findById(id)
                .map(temporada -> temporadaMapper.toResponse(temporada));
    }

    @Override
    public Optional<Temporada> findEntityById(Integer id) {
        return temporadaRepository.findById(id);
    }

    @Override
    public TemporadaResponse save(TemporadaRequest temporadaRequest) {
        Temporada temporada = temporadaMapper.toEntity(temporadaRequest);

        Temporada savedTemporada = temporadaRepository.save(temporada);

        return temporadaMapper.toResponse(savedTemporada);
    }

    @Override
    public TemporadaResponse update(TemporadaRequest temporadaRequest, Optional<Temporada> optionalTemporada) {
        Temporada temporada = optionalTemporada.get();
        temporada.setNombre(temporadaRequest.nombre());
        temporada.setFechaInicio(temporadaRequest.fechaInicio());
        temporada.setFechaFin(temporadaRequest.fechaFin());

        Temporada updatedTemporada = temporadaRepository.save(temporada);

        return temporadaMapper.toResponse(updatedTemporada);
    }

    @Override
    public void delete(Integer id) {
        temporadaRepository.deleteById(id);
    }
}
