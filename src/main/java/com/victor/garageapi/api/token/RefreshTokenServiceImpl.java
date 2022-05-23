package com.victor.garageapi.api.token;

import com.victor.garageapi.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.el.PropertyNotFoundException;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedAt(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(String token) {
        refreshTokenRepository.findRefreshTokenByToken(token)
                .orElseThrow(() -> new PropertyNotFoundException("INVALID_REFRESH_TOKEN"));

    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteRefreshTokenByToken(token);
    }
}
