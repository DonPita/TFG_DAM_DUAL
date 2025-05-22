package com.pita.waterpolo.repository;

import com.pita.waterpolo.entity.EstadisticaJugadorPartido;
import com.pita.waterpolo.entity.Jugador;
import com.pita.waterpolo.entity.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadisticaJugadorPartidoRepository extends JpaRepository<EstadisticaJugadorPartido, Integer> {

    List<EstadisticaJugadorPartido> findByJugador(Jugador jugador);

    List<EstadisticaJugadorPartido> findByPartido(Partido partido);

    Optional<EstadisticaJugadorPartido> findByJugadorAndPartido(Jugador jugador, Partido partido);
}
