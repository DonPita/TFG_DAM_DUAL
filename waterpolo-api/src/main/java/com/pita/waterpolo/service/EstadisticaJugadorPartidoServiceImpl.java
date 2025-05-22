package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.EstadisticaJugadorPartidoRequest;
import com.pita.waterpolo.dto.response.EstadisticaJugadorPartidoResponse;
import com.pita.waterpolo.entity.EstadisticaJugadorPartido;
import com.pita.waterpolo.entity.Jugador;
import com.pita.waterpolo.entity.Partido;
import com.pita.waterpolo.mapper.EstadisticaJugadorPartidoMapper;
import com.pita.waterpolo.repository.EstadisticaJugadorPartidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadisticaJugadorPartidoServiceImpl implements EstadisticaJugadorPartidoService {

    private final EstadisticaJugadorPartidoRepository estadisticaJugadorPartidoRepository;
    private final EstadisticaJugadorPartidoMapper estadisticaJugadorPartidoMapper;

    public EstadisticaJugadorPartidoServiceImpl(EstadisticaJugadorPartidoRepository estadisticaJugadorPartidoRepository,
                                                EstadisticaJugadorPartidoMapper estadisticaJugadorPartidoMapper) {
        this.estadisticaJugadorPartidoRepository = estadisticaJugadorPartidoRepository;
        this.estadisticaJugadorPartidoMapper = estadisticaJugadorPartidoMapper;
    }

    @Override
    public List<EstadisticaJugadorPartidoResponse> findAll() {
        return estadisticaJugadorPartidoRepository.findAll()
                .stream()
                .map(estadisticaJugadorPartidoMapper::toResponse)
                .toList();
    }

    @Override
    public List<EstadisticaJugadorPartidoResponse> findByJugador(Jugador jugador) {
        return estadisticaJugadorPartidoRepository.findByJugador(jugador)
                .stream()
                .map(estadisticaJugadorPartidoMapper::toResponse)
                .toList();
    }

    @Override
    public List<EstadisticaJugadorPartidoResponse> findByPartido(Partido partido) {
        return estadisticaJugadorPartidoRepository.findByPartido(partido)
                .stream()
                .map(estadisticaJugadorPartidoMapper::toResponse)
                .toList();
    }

    @Override
    public Optional<EstadisticaJugadorPartidoResponse> findById(Integer id) {
        return estadisticaJugadorPartidoRepository.findById(id).map(estadisticaJugadorPartidoMapper::toResponse);
    }

    @Override
    public Optional<EstadisticaJugadorPartido> findEntityById(Integer id) {
        return estadisticaJugadorPartidoRepository.findById(id);
    }

    @Override
    public Optional<EstadisticaJugadorPartidoResponse> findByJugadorAndPartido(Jugador jugador, Partido partido) {
        return estadisticaJugadorPartidoRepository.findByJugadorAndPartido(jugador, partido)
                .map(estadisticaJugadorPartidoMapper::toResponse);
    }

    @Override
    public EstadisticaJugadorPartidoResponse save(EstadisticaJugadorPartidoRequest estadisticaJugadorPartidoRequest,
                                                  Jugador jugador, Partido partido) {
        EstadisticaJugadorPartido estadisticaJugadorPartido = estadisticaJugadorPartidoMapper.toEstadisticaJugadorPartido(estadisticaJugadorPartidoRequest);
        estadisticaJugadorPartido.setJugador(jugador);
        estadisticaJugadorPartido.setPartido(partido);

        EstadisticaJugadorPartido savedEstadistica = estadisticaJugadorPartidoRepository.save(estadisticaJugadorPartido);

        return estadisticaJugadorPartidoMapper.toResponse(savedEstadistica);
    }

    @Override
    public EstadisticaJugadorPartidoResponse update(EstadisticaJugadorPartidoRequest estadisticaJugadorPartidoRequest,
                                                    Jugador jugador, Partido partido,
                                                    Optional<EstadisticaJugadorPartido> estadisticaJugadorPartidoOptional) {
        EstadisticaJugadorPartido original = estadisticaJugadorPartidoOptional.get();
        updateEstadistica(estadisticaJugadorPartidoRequest, original);
        original.setJugador(jugador);
        original.setPartido(partido);

        EstadisticaJugadorPartido updatedEstadistica = estadisticaJugadorPartidoRepository.save(original);

        return estadisticaJugadorPartidoMapper.toResponse(updatedEstadistica);
    }

    @Override
    public void delete(Integer id) {
        estadisticaJugadorPartidoRepository.deleteById(id);
    }

    private EstadisticaJugadorPartido updateEstadistica (EstadisticaJugadorPartidoRequest estadisticaJugadorPartidoRequest,
                                                         EstadisticaJugadorPartido original) {
        original.setGoles(estadisticaJugadorPartidoRequest.goles());
        original.setGolesPenalti(estadisticaJugadorPartidoRequest.golesPenalti());
        original.setGolesTandaPenalti(estadisticaJugadorPartidoRequest.golesTandaPenalti());
        original.setAmarillas(estadisticaJugadorPartidoRequest.amarillas());
        original.setRojas(estadisticaJugadorPartidoRequest.rojas());
        original.setExpulsiones(estadisticaJugadorPartidoRequest.expulsiones());
        original.setExpulsionSustitucionDefinitiva(estadisticaJugadorPartidoRequest.expulsionSustitucionDefinitiva());
        original.setExpulsionBrutalidad(estadisticaJugadorPartidoRequest.expulsionBrutalidad());
        original.setExpulsionSustitucionNoDefinitiva(estadisticaJugadorPartidoRequest.expulsionSustitucionNoDefinitiva());
        original.setExpulsionesPenalti(estadisticaJugadorPartidoRequest.expulsionesPenalti());
        original.setFaltasPenalti(estadisticaJugadorPartidoRequest.faltasPenalti());
        original.setPenaltisFallados(estadisticaJugadorPartidoRequest.penaltisFallados());
        original.setOtros(estadisticaJugadorPartidoRequest.otros());
        original.setTiemposMuertos(estadisticaJugadorPartidoRequest.tiemposMuertos() != null ? estadisticaJugadorPartidoRequest.tiemposMuertos() : 0);
        original.setJuegoLimpio(estadisticaJugadorPartidoRequest.juegoLimpio() != null ? estadisticaJugadorPartidoRequest.juegoLimpio() : 0);
        original.setMvp(estadisticaJugadorPartidoRequest.mvp() != null ? estadisticaJugadorPartidoRequest.mvp() : 0);

        return original;
    }
}
