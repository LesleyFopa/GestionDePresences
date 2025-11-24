package com.lesley.GestionPresences.auth.service;

import com.lesley.GestionPresences.auth.entities.RefreshToken;
import com.lesley.GestionPresences.auth.repositoty.RefreshTokenRepository;
import com.lesley.GestionPresences.entities.User;
import com.lesley.GestionPresences.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshToken createRefreshToken(String username) {
        User user =  userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

        RefreshToken refreshToken = user.getRefreshToken();

        if (refreshToken == null) {
            long refreshTokenValidity = 1000 * 60 * 60 * 24;
            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expiresAt(Instant.now().plusSeconds(refreshTokenValidity))
                    .user(user)
                    .build();
            refreshTokenRepository.save(refreshToken);
        }
        return refreshToken;
    }

    public RefreshToken verifyRefreshToken(String refreshToken) {
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() ->new RuntimeException("Refresh token not found!"));

        if (refreshTokenEntity.getExpiresAt().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshTokenEntity);
            throw new RuntimeException("Refresh token expired!");
        }
        return refreshTokenEntity;
    }

}
