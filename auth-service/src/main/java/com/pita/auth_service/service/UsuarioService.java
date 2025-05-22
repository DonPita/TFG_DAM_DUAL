package com.pita.auth_service.service;

import com.pita.auth_service.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UsuarioService {

    List<Usuario> findAll();

    Optional<Usuario> findById(Integer id);

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    Optional<Usuario> findByEmail(String email);

    boolean existsByNombreUsuario(String nombreUsuario);

    boolean existsByEmail(String email);

    void save(Usuario usuario);

    void deleteById(Integer id);


}
