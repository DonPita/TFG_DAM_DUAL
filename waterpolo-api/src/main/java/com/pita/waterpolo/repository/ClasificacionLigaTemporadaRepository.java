package com.pita.waterpolo.repository;

import com.pita.waterpolo.entity.ClasificacionLigaTemporada;
import com.pita.waterpolo.entity.EquipoLiga;
import com.pita.waterpolo.entity.Temporada;
import com.pita.waterpolo.util.SqlUtil;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClasificacionLigaTemporadaRepository extends JpaRepository<ClasificacionLigaTemporada, Integer> {
    @Query(SqlUtil.FIND_CLASIFICACION_BY_LIGA_TEMPORADA_JORNADA)
    List<ClasificacionLigaTemporada> findByLigaAndTemporadaAndJornada(Integer idLiga, Integer idTemporada, Integer jornada);

    @Query(SqlUtil.FIND_CLASIFICACION_GLOBAL_BY_LIGA_TEMPORADA)
    List<ClasificacionLigaTemporada> findClasificacionGlobalByLigaAndTemporada(Integer idLiga, Integer idTemporada);

    List<ClasificacionLigaTemporada> findByEquipoLigaAndTemporada(@NotNull EquipoLiga equipoLiga, @NotNull Temporada temporada);
}
