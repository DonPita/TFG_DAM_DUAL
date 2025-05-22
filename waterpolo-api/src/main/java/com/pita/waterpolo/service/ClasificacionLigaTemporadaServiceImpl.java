package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.ClasificacionLigaTemporadaRequest;
import com.pita.waterpolo.dto.response.ClasificacionLigaTemporadaResponse;
import com.pita.waterpolo.entity.ClasificacionLigaTemporada;
import com.pita.waterpolo.entity.EquipoLiga;
import com.pita.waterpolo.entity.Liga;
import com.pita.waterpolo.entity.Temporada;
import com.pita.waterpolo.mapper.ClasificacionLigaTemporadaMapper;
import com.pita.waterpolo.repository.ClasificacionLigaTemporadaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClasificacionLigaTemporadaServiceImpl implements ClasificacionLigaTemporadaService {

    private final ClasificacionLigaTemporadaRepository clasificacionLigaTemporadaRepository;
    private final ClasificacionLigaTemporadaMapper clasificacionLigaTemporadaMapper;

    public ClasificacionLigaTemporadaServiceImpl(ClasificacionLigaTemporadaRepository clasificacionLigaTemporadaRepository,
                                                 ClasificacionLigaTemporadaMapper clasificacionLigaTemporadaMapper) {
        this.clasificacionLigaTemporadaRepository = clasificacionLigaTemporadaRepository;
        this.clasificacionLigaTemporadaMapper = clasificacionLigaTemporadaMapper;
    }

    @Override
    public List<ClasificacionLigaTemporadaResponse> findAll() {
        return clasificacionLigaTemporadaRepository.findAll()
                .stream()
                .map(clasificacionLigaTemporadaMapper::toResponse)
                .toList();
    }

    @Override
    public Optional<ClasificacionLigaTemporadaResponse> findById(Integer id) {
        return clasificacionLigaTemporadaRepository.findById(id)
                .map(clasificacionLigaTemporadaMapper::toResponse);
    }

    @Override
    public Optional<ClasificacionLigaTemporada> findEntityById(Integer id) {
        return clasificacionLigaTemporadaRepository.findById(id);
    }

    @Override
    public List<ClasificacionLigaTemporadaResponse> findByLigaAndTemporadaAndJornada(Liga liga, Temporada temporada,
                                                                                     Integer jornada) {
        return clasificacionLigaTemporadaRepository.findByLigaAndTemporadaAndJornada(liga.getId(), temporada.getId(), jornada)
                .stream()
                .map(clasificacionLigaTemporadaMapper::toResponse)
                .toList();
    }

    @Override
    public List<ClasificacionLigaTemporadaResponse> findClasificacionGlobalByLigaAndTemporada(Liga liga, Temporada temporada) {
        return clasificacionLigaTemporadaRepository.findClasificacionGlobalByLigaAndTemporada(liga.getId(), temporada.getId())
                .stream()
                .map(clasificacionLigaTemporadaMapper::toResponse)
                .toList();
    }

    @Override
    public List<ClasificacionLigaTemporadaResponse> findByEquipoLigaAndTemporada(EquipoLiga equipoLiga, Temporada temporada) {
        return clasificacionLigaTemporadaRepository.findByEquipoLigaAndTemporada(equipoLiga, temporada)
                .stream()
                .map(clasificacionLigaTemporadaMapper::toResponse)
                .toList();
    }

    @Override
    public ClasificacionLigaTemporadaResponse save(ClasificacionLigaTemporadaRequest clasificacionLigaTemporadaRequest,
                                                   Temporada temporada, EquipoLiga equipoLiga) {
        ClasificacionLigaTemporada clasificacionLigaTemporada = clasificacionLigaTemporadaMapper.toEntity(clasificacionLigaTemporadaRequest);
        clasificacionLigaTemporada.setTemporada(temporada);
        clasificacionLigaTemporada.setEquipoLiga(equipoLiga);

        clasificacionLigaTemporada = clasificacionLigaTemporadaRepository.save(clasificacionLigaTemporada);

        return clasificacionLigaTemporadaMapper.toResponse(clasificacionLigaTemporada);
    }

    @Override
    public ClasificacionLigaTemporadaResponse update(ClasificacionLigaTemporadaRequest clasificacionLigaTemporadaRequest,
                                                     Temporada temporada, EquipoLiga equipoLiga,
                                                     Optional<ClasificacionLigaTemporada> clasificacionLigaTemporadaOptional) {
        ClasificacionLigaTemporada original = clasificacionLigaTemporadaOptional.get();
        updateClasificacion(clasificacionLigaTemporadaRequest, original);
        original.setTemporada(temporada);
        original.setEquipoLiga(equipoLiga);

        ClasificacionLigaTemporada updated = clasificacionLigaTemporadaRepository.save(original);

        return clasificacionLigaTemporadaMapper.toResponse(updated);
    }

    @Override
    public void delete(Integer id) {
        clasificacionLigaTemporadaRepository.deleteById(id);
    }

    private ClasificacionLigaTemporada updateClasificacion(ClasificacionLigaTemporadaRequest clasificacionLigaTemporadaRequest,
                                                     ClasificacionLigaTemporada original) {
        original.setJornada(clasificacionLigaTemporadaRequest.jornada());
        original.setPuntos(clasificacionLigaTemporadaRequest.puntos());
        original.setPartidosJugados(clasificacionLigaTemporadaRequest.partidosJugados());
        original.setVictorias(clasificacionLigaTemporadaRequest.victorias());
        original.setDerrotas(clasificacionLigaTemporadaRequest.derrotas());
        original.setEmpates(clasificacionLigaTemporadaRequest.empates());
        original.setGolesAFavor(clasificacionLigaTemporadaRequest.golesAFavor());
        original.setGolesEnContra(clasificacionLigaTemporadaRequest.golesEnContra());
        original.setDiferenciaGoles(clasificacionLigaTemporadaRequest.diferenciaGoles());

        return original;
    }
}
