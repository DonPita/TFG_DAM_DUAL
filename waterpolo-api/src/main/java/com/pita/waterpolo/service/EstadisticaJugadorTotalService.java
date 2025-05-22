package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.EstadisticaJugadorTotalRequest;
import com.pita.waterpolo.dto.response.EstadisticaJugadorTotalResponse;
import com.pita.waterpolo.entity.EstadisticaJugadorTotal;
import com.pita.waterpolo.entity.Jugador;
import com.pita.waterpolo.entity.Temporada;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EstadisticaJugadorTotalService {

     List<EstadisticaJugadorTotalResponse> findAll();

     List<EstadisticaJugadorTotalResponse> findByJugador(Jugador jugador);
     List<EstadisticaJugadorTotalResponse> findByTemporada(Temporada temporada);

     Optional<EstadisticaJugadorTotalResponse> findById(Integer id);
     Optional<EstadisticaJugadorTotal> findEntityById(Integer id);
     Optional<EstadisticaJugadorTotalResponse> findByJugadorAndTemporada(Jugador jugador, Temporada temporada);

     EstadisticaJugadorTotalResponse save(EstadisticaJugadorTotalRequest estadisticaJugadorTotalRequest,
                                          Jugador jugador, Temporada temporada);

     EstadisticaJugadorTotalResponse update(EstadisticaJugadorTotalRequest estadisticaJugadorTotalRequest,
                                            Jugador jugador, Temporada temporada,
                                            Optional<EstadisticaJugadorTotal> estadisticaJugadorTotalOptional);

     void delete(Integer id);

}
