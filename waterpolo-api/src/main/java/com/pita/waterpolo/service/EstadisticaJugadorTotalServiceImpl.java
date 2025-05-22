package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.EstadisticaJugadorTotalRequest;
import com.pita.waterpolo.dto.response.EstadisticaJugadorTotalResponse;
import com.pita.waterpolo.entity.EstadisticaJugadorTotal;
import com.pita.waterpolo.entity.Jugador;
import com.pita.waterpolo.entity.Temporada;
import com.pita.waterpolo.mapper.EstadisticaJugadorTotalMapper;
import com.pita.waterpolo.repository.EstadisticaJugadorTotalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadisticaJugadorTotalServiceImpl implements EstadisticaJugadorTotalService {

    private final EstadisticaJugadorTotalRepository estadisticaJugadorTotalRepository;
    private final EstadisticaJugadorTotalMapper estadisticaJugadorTotalMapper;

    public EstadisticaJugadorTotalServiceImpl(EstadisticaJugadorTotalRepository estadisticaJugadorTotalRepository,
                                              EstadisticaJugadorTotalMapper estadisticaJugadorTotalMapper) {
        this.estadisticaJugadorTotalRepository = estadisticaJugadorTotalRepository;
        this.estadisticaJugadorTotalMapper = estadisticaJugadorTotalMapper;
    }

    @Override
    public List<EstadisticaJugadorTotalResponse> findAll() {
        return estadisticaJugadorTotalRepository.findAll()
                .stream()
                .map(estadisticaJugadorTotalMapper::toResponse)
                .toList();
    }

    @Override
    public List<EstadisticaJugadorTotalResponse> findByJugador(Jugador jugador) {
        return estadisticaJugadorTotalRepository.findByJugador(jugador)
                .stream()
                .map(estadisticaJugadorTotalMapper::toResponse)
                .toList();
    }

    @Override
    public List<EstadisticaJugadorTotalResponse> findByTemporada(Temporada temporada) {
        return estadisticaJugadorTotalRepository.findByTemporada(temporada)
                .stream()
                .map(estadisticaJugadorTotalMapper::toResponse)
                .toList();
    }

    @Override
    public Optional<EstadisticaJugadorTotalResponse> findById(Integer id) {
        return estadisticaJugadorTotalRepository.findById(id)
                .map(estadisticaJugadorTotalMapper::toResponse);
    }

    @Override
    public Optional<EstadisticaJugadorTotal> findEntityById(Integer id) {
        return estadisticaJugadorTotalRepository.findById(id);
    }

    @Override
    public Optional<EstadisticaJugadorTotalResponse> findByJugadorAndTemporada(Jugador jugador, Temporada temporada) {
        return estadisticaJugadorTotalRepository.findByJugadorAndTemporada(jugador, temporada)
                .map(estadisticaJugadorTotalMapper::toResponse);
    }

    @Override
    public EstadisticaJugadorTotalResponse save(EstadisticaJugadorTotalRequest estadisticaJugadorTotalRequest, Jugador jugador, Temporada temporada) {
        EstadisticaJugadorTotal estadisticaJugadorTotal = estadisticaJugadorTotalMapper.toEntity(estadisticaJugadorTotalRequest);
        estadisticaJugadorTotal.setJugador(jugador);
        estadisticaJugadorTotal.setTemporada(temporada);

        EstadisticaJugadorTotal savedEstadistica = estadisticaJugadorTotalRepository.save(estadisticaJugadorTotal);

        return estadisticaJugadorTotalMapper.toResponse(savedEstadistica);
    }

    @Override
    public EstadisticaJugadorTotalResponse update(EstadisticaJugadorTotalRequest estadisticaJugadorTotalRequest,
                                                  Jugador jugador, Temporada temporada,
                                                  Optional<EstadisticaJugadorTotal> estadisticaJugadorTotalOptional) {
        EstadisticaJugadorTotal original =  estadisticaJugadorTotalOptional.get();
        updateEstadistica(estadisticaJugadorTotalRequest, original);
        original.setJugador(jugador);
        original.setTemporada(temporada);

        EstadisticaJugadorTotal updatedEstadistica = estadisticaJugadorTotalRepository.save(original);

        return estadisticaJugadorTotalMapper.toResponse(updatedEstadistica);
    }

    @Override
    public void delete(Integer id) {
        estadisticaJugadorTotalRepository.deleteById(id);
    }

    private EstadisticaJugadorTotal updateEstadistica(EstadisticaJugadorTotalRequest estadisticaJugadorTotalRequest,
                                                      EstadisticaJugadorTotal original) {
        original.setGolesTotales(estadisticaJugadorTotalRequest.golesTotales());
        original.setGolesPenaltiTotales(estadisticaJugadorTotalRequest.golesPenaltiTotales());
        original.setGolesTandaPenaltiTotales(estadisticaJugadorTotalRequest.golesTandaPenaltiTotales());
        original.setTarAmarillaTotales(estadisticaJugadorTotalRequest.tarAmarillaTotales());
        original.setTarRojaTotales(estadisticaJugadorTotalRequest.tarRojaTotales());
        original.setExpulsionesTotales(estadisticaJugadorTotalRequest.expulsionesTotales());
        original.setExpulsionesSustitucionDTotales(estadisticaJugadorTotalRequest.expulsionesSustitucionDTotales());
        original.setExpulsionesBrutalidadTotales(estadisticaJugadorTotalRequest.expulsionesBrutalidadTotales());
        original.setExpulsionesSustitucionNdTotales(estadisticaJugadorTotalRequest.expulsionesSustitucionNdTotales());
        original.setExpulsionesPenaltiTotales(estadisticaJugadorTotalRequest.expulsionesPenaltiTotales());
        original.setFaltasPenaltiTotales(estadisticaJugadorTotalRequest.faltasPenaltiTotales());
        original.setPenaltiFalladosTotales(estadisticaJugadorTotalRequest.penaltiFalladosTotales());
        original.setOtrosTotales(estadisticaJugadorTotalRequest.otrosTotales());
        original.setTiemposMuertosTotales(estadisticaJugadorTotalRequest.tiemposMuertosTotales());
        original.setJuegoLimpioTotales(estadisticaJugadorTotalRequest.juegoLimpioTotales());
        original.setMvpTotales(estadisticaJugadorTotalRequest.mvpTotales());

        return original;
    }
}
