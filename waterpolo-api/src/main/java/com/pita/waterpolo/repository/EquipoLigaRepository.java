package com.pita.waterpolo.repository;

import com.pita.waterpolo.entity.Equipo;
import com.pita.waterpolo.entity.EquipoLiga;
import com.pita.waterpolo.entity.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipoLigaRepository extends JpaRepository<EquipoLiga, Integer> {
    
    List<EquipoLiga> findByEquipoId(Integer idEquipo);
    
    List<EquipoLiga> findByLigaId(Integer idLiga);

//    @Query("SELECT * FROM equipo_liga")
//    List<EquipoLiga> findEquipoLigaByActivoAndLigaId(Boolean activo, Liga liga);
}
