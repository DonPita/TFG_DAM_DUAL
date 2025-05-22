package com.pita.auth_service.service;

import com.pita.auth_service.entity.RefreshToken;
import com.pita.auth_service.repository.RefreshTokensRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokensRepository refreshTokensRepository;

    public RefreshTokenServiceImpl(RefreshTokensRepository refreshTokensRepository) {
        this.refreshTokensRepository = refreshTokensRepository;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokensRepository.findByToken(token);
    }

    @Override
    public void deleteByUsuarioId(Integer usuarioId) {
        refreshTokensRepository.deleteByUsuario_Id(usuarioId);
    }
}
