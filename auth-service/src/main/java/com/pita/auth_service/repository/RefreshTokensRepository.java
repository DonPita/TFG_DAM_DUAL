package com.pita.auth_service.repository;

import com.pita.auth_service.entity.RefreshToken;
import com.pita.auth_service.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokensRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findAllByUsuarioAndValidoTrueAndExpiracionAfter(Usuario usuario, LocalDateTime now);

    List<RefreshToken> findByUsuario(Usuario usuario);

    void deleteByUsuario_Id(Integer usuarioId);
}
