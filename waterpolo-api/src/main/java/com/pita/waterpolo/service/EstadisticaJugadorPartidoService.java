package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.EstadisticaJugadorPartidoRequest;
import com.pita.waterpolo.dto.response.EstadisticaJugadorPartidoResponse;
import com.pita.waterpolo.entity.EstadisticaJugadorPartido;
import com.pita.waterpolo.entity.Jugador;
import com.pita.waterpolo.entity.Partido;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EstadisticaJugadorPartidoService {

    List<EstadisticaJugadorPartidoResponse> findAll();

    List<EstadisticaJugadorPartidoResponse> findByJugador(Jugador jugador);
    List<EstadisticaJugadorPartidoResponse> findByPartido(Partido partido);


    Optional<EstadisticaJugadorPartidoResponse> findById(Integer id);
    Optional<EstadisticaJugadorPartido> findEntityById(Integer id);
    Optional<EstadisticaJugadorPartidoResponse> findByJugadorAndPartido(Jugador jugador, Partido partido);

    EstadisticaJugadorPartidoResponse save(EstadisticaJugadorPartidoRequest estadisticaJugadorPartidoRequest,
                                           Jugador jugador, Partido partido);

    EstadisticaJugadorPartidoResponse update(EstadisticaJugadorPartidoRequest estadisticaJugadorPartidoRequest,
                                             Jugador jugador, Partido partido,
                                             Optional<EstadisticaJugadorPartido> estadisticaJugadorPartidoOptional);

    void delete(Integer id);
}
