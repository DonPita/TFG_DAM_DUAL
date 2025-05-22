package com.pita.auth_service.repository;

import com.pita.auth_service.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findByEmail(String email);

    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);
}
