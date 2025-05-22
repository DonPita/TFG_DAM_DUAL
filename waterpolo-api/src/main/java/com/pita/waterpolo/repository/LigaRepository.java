package com.pita.waterpolo.repository;

import com.pita.waterpolo.entity.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigaRepository extends JpaRepository<Liga, Integer> {
}
