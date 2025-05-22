package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.PartidoRequest;
import com.pita.waterpolo.dto.response.PartidoResponse;
import com.pita.waterpolo.entity.EquipoLiga;
import com.pita.waterpolo.entity.Partido;
import com.pita.waterpolo.entity.Temporada;
import com.pita.waterpolo.exception.ResourceNotFoundException;
import com.pita.waterpolo.mapper.PartidoMapper;
import com.pita.waterpolo.repository.PartidoRepository;
import com.pita.waterpolo.repository.TemporadaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PartidoServiceImpl implements PartidoService {

    private final PartidoRepository partidoRepository;
    private final PartidoMapper partidoMapper;

    public PartidoServiceImpl(PartidoRepository partidoRepository, PartidoMapper partidoMapper) {
        this.partidoRepository = partidoRepository;
        this.partidoMapper = partidoMapper;
    }

    @Override
    public List<PartidoResponse> findAll() {
        return partidoRepository.findAll().stream()
                .map(partido -> partidoMapper.toResponse(partido))
                .toList();
    }

    @Override
    public List<PartidoResponse> findByTemporada(Temporada temporada) {

        return partidoRepository.findPartidosByTemporada(temporada).stream()
                .map(partido -> partidoMapper.toResponse(partido))
                .toList();
    }

    @Override
    public List<PartidoResponse> findPartidosByTemporadaAndJornada(Temporada temporada, Integer jornada) {
        return partidoRepository.findPartidosByTemporadaAndJornada(temporada, jornada).stream()
                .map(partido -> partidoMapper.toResponse(partido))
                .toList();
    }

    @Override
    public List<PartidoResponse> findPartidosByEquipoLigaAndTemporada(EquipoLiga equipoLiga, Temporada temporada) {
        List<Partido> partidos = partidoRepository.findPartidosByTemporadaAndEquipoLiga(temporada, equipoLiga);

        return partidos.stream()
                .map(partido -> partidoMapper.toResponse(partido))
                .toList();
    }

    @Override
    public Optional<PartidoResponse> findById(Integer id) {
        return partidoRepository.findById(id).map(partido -> partidoMapper.toResponse(partido));

    }

    @Override
    public Optional<Partido> findEntityById(Integer id) {
        return partidoRepository.findById(id);
    }

    @Override
    public PartidoResponse save(PartidoRequest partidoRequest) {
        Partido partido = partidoMapper.toEntity(partidoRequest);

        Partido savedPartido = partidoRepository.save(partido);

        return partidoMapper.toResponse(savedPartido);
    }

    @Override
    public PartidoResponse update(PartidoRequest partidoRequest, Temporada temporada, EquipoLiga equipoLigaLocal,
                                  EquipoLiga equipoLigaVisitante, Optional<Partido> partidoOptional) {
        Partido partido = partidoOptional.get();
        partido.setTemporada(temporada);
        partido.setJornada(partidoRequest.jornada());
        partido.setFecha(partidoRequest.fecha());
        partido.setEquipoLocal(equipoLigaLocal);
        partido.setEquipoVisitante(equipoLigaVisitante);
        partido.setGolesLocal(partidoRequest.golesLocal());
        partido.setGolesVisitante(partidoRequest.golesVisitante());

        Partido savedPartido = partidoRepository.save(partido);

        return partidoMapper.toResponse(savedPartido);

    }

    @Override
    public void delete(Integer id) {
        partidoRepository.deleteById(id);
    }
}
