package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.JugadorEquipoLigaRequest;
import com.pita.waterpolo.dto.response.JugadorEquipoLigaResponse;
import com.pita.waterpolo.entity.EquipoLiga;
import com.pita.waterpolo.entity.Jugador;
import com.pita.waterpolo.entity.JugadorEquipoLiga;
import com.pita.waterpolo.entity.Temporada;
import com.pita.waterpolo.mapper.JugadorEquipoLigaMapper;
import com.pita.waterpolo.repository.JugadorEquipoLigaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorEquipoLigaServiceImpl implements JugadorEquipoLigaService {

    private final JugadorEquipoLigaRepository jugadorEquipoLigaRepository;
    private final JugadorEquipoLigaMapper jugadorEquipoLigaMapper;

    public JugadorEquipoLigaServiceImpl(JugadorEquipoLigaRepository jugadorEquipoLigaRepository, JugadorEquipoLigaMapper jugadorEquipoLigaMapper) {
        this.jugadorEquipoLigaRepository = jugadorEquipoLigaRepository;
        this.jugadorEquipoLigaMapper = jugadorEquipoLigaMapper;
    }

    @Override
    public List<JugadorEquipoLigaResponse> findAll() {
        return jugadorEquipoLigaRepository.findAll().stream()
                .map(jugadorEquipoLiga -> jugadorEquipoLigaMapper.toResponse(jugadorEquipoLiga))
                .toList();
    }

    @Override
    public List<JugadorEquipoLigaResponse> findByJugador(Jugador jugador) {
        return jugadorEquipoLigaRepository.findAllByJugador(jugador).stream()
                .map(jugadorEquipoLiga -> jugadorEquipoLigaMapper.toResponse(jugadorEquipoLiga))
                .toList();
    }

    @Override
    public List<JugadorEquipoLigaResponse> findByEquipoLiga(EquipoLiga equipoLiga) {
        return jugadorEquipoLigaRepository.findAllByEquipoLiga(equipoLiga).stream()
                .map(jugadorEquipoLiga -> jugadorEquipoLigaMapper.toResponse(jugadorEquipoLiga))
                .toList();
    }

    @Override
    public List<JugadorEquipoLigaResponse> findByTemporada(Temporada temporada) {
        return jugadorEquipoLigaRepository.findAllByTemporada(temporada).stream()
                .map(jugadorEquipoLiga -> jugadorEquipoLigaMapper.toResponse(jugadorEquipoLiga))
                .toList();
    }

    @Override
    public List<JugadorEquipoLigaResponse> findByJugadorAndEquipoLiga(Jugador jugador, EquipoLiga equipoLiga) {
        return jugadorEquipoLigaRepository.findAllByJugadorAndEquipoLiga(jugador, equipoLiga).stream()
                .map(jugadorEquipoLiga -> jugadorEquipoLigaMapper.toResponse(jugadorEquipoLiga))
                .toList();
    }

    @Override
    public List<JugadorEquipoLigaResponse> findByJugadorAndTemporada(Jugador jugador, Temporada temporada) {
        return jugadorEquipoLigaRepository.findAllByJugadorAndTemporada(jugador, temporada).stream()
                .map(jugadorEquipoLiga -> jugadorEquipoLigaMapper.toResponse(jugadorEquipoLiga))
                .toList();
    }

    @Override
    public List<JugadorEquipoLigaResponse> findByEquipoLigaAndTemporada(EquipoLiga equipoLiga, Temporada temporada) {
        return jugadorEquipoLigaRepository.findAllByEquipoLigaAndTemporada(equipoLiga, temporada).stream()
                .map(jugadorEquipoLiga -> jugadorEquipoLigaMapper.toResponse(jugadorEquipoLiga))
                .toList();
    }

    @Override
    public Optional<JugadorEquipoLigaResponse> findById(Integer id) {
        return jugadorEquipoLigaRepository.findById(id).map(jugadorEquipoLigaMapper::toResponse);
    }

    @Override
    public Optional<JugadorEquipoLiga> findEntityById(Integer id) {
        return jugadorEquipoLigaRepository.findById(id);
    }

    @Override
    public JugadorEquipoLigaResponse save(JugadorEquipoLigaRequest jugadorEquipoLigaRequest, Jugador jugador,
                                          EquipoLiga equipoLiga, Temporada temporada) {
        JugadorEquipoLiga jugadorEquipoLiga = new JugadorEquipoLiga(jugador, equipoLiga, temporada);

        JugadorEquipoLiga savedJugadorEquipoLiga = jugadorEquipoLigaRepository.save(jugadorEquipoLiga);

        return jugadorEquipoLigaMapper.toResponse(savedJugadorEquipoLiga);
    }

    @Override
    public JugadorEquipoLigaResponse update(JugadorEquipoLigaRequest jugadorEquipoLigaRequest, Jugador jugador,
                                            EquipoLiga equipoLiga, Temporada temporada,
                                            Optional<JugadorEquipoLiga> jugadorEquipoLigaOptional) {

        JugadorEquipoLiga jugadorEquipoLiga = jugadorEquipoLigaOptional.get();
        jugadorEquipoLiga.setJugador(jugador);
        jugadorEquipoLiga.setEquipoLiga(equipoLiga);
        jugadorEquipoLiga.setTemporada(temporada);

        JugadorEquipoLiga updatedJugadorEquipoLiga = jugadorEquipoLigaRepository.save(jugadorEquipoLiga);

        return jugadorEquipoLigaMapper.toResponse(updatedJugadorEquipoLiga);
    }

    @Override
    public void delete(Integer id) {
        jugadorEquipoLigaRepository.deleteById(id);
    }
}
