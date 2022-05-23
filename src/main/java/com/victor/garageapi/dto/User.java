package com.victor.garageapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String email;
    private Instant email_verified_at;
    private Instant created_at;
    private String access_token;
    private String refresh_token;
    private Instant expires_at;
}
