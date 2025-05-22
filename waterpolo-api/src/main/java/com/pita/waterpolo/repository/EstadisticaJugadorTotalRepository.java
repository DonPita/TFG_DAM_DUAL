package com.pita.waterpolo.repository;

import com.pita.waterpolo.entity.EstadisticaJugadorTotal;
import com.pita.waterpolo.entity.Jugador;
import com.pita.waterpolo.entity.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadisticaJugadorTotalRepository extends JpaRepository<EstadisticaJugadorTotal, Integer> {

    List<EstadisticaJugadorTotal> findByJugador(Jugador jugador);

    List<EstadisticaJugadorTotal> findByTemporada(Temporada temporada);

    Optional<EstadisticaJugadorTotal> findByJugadorAndTemporada(Jugador jugador, Temporada temporada);
}
