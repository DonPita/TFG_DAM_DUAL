package com.pita.auth_service.service;

import com.pita.auth_service.entity.RefreshToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RefreshTokenService {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUsuarioId(Integer usuarioId);
}
