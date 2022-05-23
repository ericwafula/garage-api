package com.victor.garageapi.auth.endpoints;

import com.victor.garageapi.api.token.RefreshTokenService;
import com.victor.garageapi.auth.JWTUtility;
import com.victor.garageapi.dto.*;
import com.victor.garageapi.entity.AppUser;
import com.victor.garageapi.error.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final JWTUtility jwtUtility;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("signup")
    public ResponseEntity<ResponseMessage> signup(@RequestBody AppUser newAppUser) throws EntityAlreadyExistsException, EmptyFieldsException {
        authService.signup(newAppUser);
        ResponseMessage message = ResponseMessage.builder()
                .message("account registration successful")
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserLogin userLogin) throws EntityNotFoundException, EmailNotFoundException {
        try {
            Authentication authenticate =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (BadCredentialsException e) {
            throw new EntityNotFoundException("INVALID_CREDENTIALS");
        }
        UserDetails userDetails = authService.loadUserByUsername(userLogin.getEmail());
        final AppUser appUser = authService.UserSchoolByEmail(userLogin.getEmail());
        final String token;
        token = jwtUtility.generateToken(userDetails);
        final User user = User.builder()
                .id(appUser.getId())
                .email(appUser.getEmail())
                .email_verified_at(appUser.getVerifiedAt())
                .created_at(appUser.getCreatedAt())
                .access_token(token)
                .refresh_token(refreshTokenService.generateRefreshToken().getToken())
                .expires_at(new Date(jwtUtility.getExpirationDateFromToken(token).getTime()).toInstant())
                .build();

        return new ResponseEntity<>(new JwtResponse(user), HttpStatus.OK);
    }

    @PostMapping("refresh/token")
    public ResponseEntity<JwtResponse> refreshTokens(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        final User user = authService.refreshToken(refreshTokenRequest);
        return new ResponseEntity<>(new JwtResponse(user), HttpStatus.OK);
    }

    @PostMapping("logout")
    ResponseEntity<ResponseMessage> logout(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        ResponseMessage message = ResponseMessage.builder()
                .message("Refresh Token Deleted Successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
