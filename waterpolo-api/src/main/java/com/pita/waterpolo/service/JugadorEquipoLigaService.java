package com.pita.waterpolo.service;

import com.pita.waterpolo.dto.request.JugadorEquipoLigaRequest;
import com.pita.waterpolo.dto.response.JugadorEquipoLigaResponse;
import com.pita.waterpolo.entity.EquipoLiga;
import com.pita.waterpolo.entity.Jugador;
import com.pita.waterpolo.entity.JugadorEquipoLiga;
import com.pita.waterpolo.entity.Temporada;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface JugadorEquipoLigaService {

    List<JugadorEquipoLigaResponse> findAll();
    List<JugadorEquipoLigaResponse> findByJugador(Jugador jugador);
    List<JugadorEquipoLigaResponse> findByEquipoLiga(EquipoLiga equipoLiga);
    List<JugadorEquipoLigaResponse> findByTemporada(Temporada temporada);
    List<JugadorEquipoLigaResponse> findByJugadorAndEquipoLiga(Jugador jugador, EquipoLiga equipoLiga);
    List<JugadorEquipoLigaResponse> findByJugadorAndTemporada(Jugador jugador, Temporada temporada);
    List<JugadorEquipoLigaResponse> findByEquipoLigaAndTemporada(EquipoLiga equipoLiga, Temporada temporada);


    Optional<JugadorEquipoLigaResponse> findById(Integer id);
    Optional<JugadorEquipoLiga> findEntityById(Integer id);

    JugadorEquipoLigaResponse save(JugadorEquipoLigaRequest jugadorEquipoLigaRequest, Jugador jugador,
                                   EquipoLiga equipoLiga, Temporada temporada);

    JugadorEquipoLigaResponse update(JugadorEquipoLigaRequest jugadorEquipoLigaRequest, Jugador jugador,
                                     EquipoLiga equipoLiga, Temporada temporada, Optional<JugadorEquipoLiga> jugadorEquipoLigaOptional);

    void delete(Integer id);
}
