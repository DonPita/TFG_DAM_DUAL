package com.pita.waterpolo.repository;

import com.pita.waterpolo.entity.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporadaRepository extends JpaRepository<Temporada, Integer> {
}
