package com.victor.garageapi.api.token;

import com.victor.garageapi.entity.RefreshToken;

public interface RefreshTokenService {
    RefreshToken generateRefreshToken();

    void validateRefreshToken(String token);

    void deleteRefreshToken(String token);
}
