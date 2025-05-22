package com.pita.waterpolo.repository;

import com.pita.waterpolo.entity.EquipoLiga;
import com.pita.waterpolo.entity.Partido;
import com.pita.waterpolo.entity.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Integer> {

    List<Partido> findPartidosByTemporada(Temporada temporada);

    List<Partido> findPartidosByTemporadaAndJornada(Temporada temporada, Integer jornada);

    @Query("SELECT p FROM Partido p WHERE p.temporada = :temporada AND (p.equipoLocal = :equipoLiga OR p.equipoVisitante = :equipoLiga)")
    List<Partido> findPartidosByTemporadaAndEquipoLiga(@Param("temporada") Temporada temporada
            , @Param("equipoLiga") EquipoLiga equipoLiga);
}
