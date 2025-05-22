package com.pita.waterpolo.repository;

import com.pita.waterpolo.dto.response.JugadorEquipoLigaResponse;
import com.pita.waterpolo.entity.EquipoLiga;
import com.pita.waterpolo.entity.Jugador;
import com.pita.waterpolo.entity.JugadorEquipoLiga;
import com.pita.waterpolo.entity.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadorEquipoLigaRepository extends JpaRepository<JugadorEquipoLiga, Integer> {

    List<JugadorEquipoLiga> findAllByJugador(Jugador jugador);

    List<JugadorEquipoLiga> findAllByEquipoLiga(EquipoLiga equipoLiga);

    List<JugadorEquipoLiga> findAllByTemporada(Temporada temporada);

    List<JugadorEquipoLiga> findAllByJugadorAndEquipoLiga(Jugador jugador, EquipoLiga equipoLiga);

    List<JugadorEquipoLiga> findAllByJugadorAndTemporada(Jugador jugador, Temporada temporada);

    List<JugadorEquipoLiga> findAllByEquipoLigaAndTemporada(EquipoLiga equipoLiga, Temporada temporada);
}
