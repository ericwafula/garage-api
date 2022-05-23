package com.victor.garageapi.auth.endpoints;

import com.victor.garageapi.api.token.RefreshTokenService;
import com.victor.garageapi.auth.JWTUtility;
import com.victor.garageapi.dto.RefreshTokenRequest;
import com.victor.garageapi.entity.AppUser;
import com.victor.garageapi.error.exceptions.EmailNotFoundException;
import com.victor.garageapi.error.exceptions.EmptyFieldsException;
import com.victor.garageapi.error.exceptions.EntityAlreadyExistsException;
import com.victor.garageapi.error.exceptions.TokenNotFoundException;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtility jwtUtility;

    private final RefreshTokenService refreshTokenService;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = authRepository.findAppUserByEmail(username);
        user.orElseThrow(() -> new UsernameNotFoundException("USER_WITH " + username + " NOT_FOUND"));
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.get()
                .getRoles()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole())));
        return new User(user.get().getEmail(), user.get().getPassword(), authorities);
    }

    @Transactional
    @Override
    public void signup(AppUser newAppUser) throws EmptyFieldsException, EntityAlreadyExistsException {
        if (newAppUser.getPassword().equals("") || newAppUser.getEmail().equals("")) {
            throw new EmptyFieldsException("REQUIRED_DETAILS_MISSING");
        }

        Optional<AppUser> user = authRepository.findAppUserByEmail(newAppUser.getEmail());
        if (user.isPresent()) {
            throw new EntityAlreadyExistsException("USER_ALREADY_EXISTS_WITH_SAME_EMAIL");
        }

        newAppUser.setCreatedAt(Instant.now());
        newAppUser.setEnabled(false);
        newAppUser.setPassword(passwordEncoder.encode(newAppUser.getPassword()));
        authRepository.save(newAppUser);

    }

    @Override
    public AppUser findUserById(Long schoolId) {
        Optional<AppUser> school = authRepository.findById(schoolId);
        school.orElseThrow(() -> new EntityNotFoundException("USER_ID " + schoolId + "NOT_FOUND"));
        return school.get();
    }

    @Override
    public void updateUser(AppUser appUserUpdate, Long userId) {
        Optional<AppUser> user = authRepository.findById(userId);
        user.orElseThrow(() -> new EntityNotFoundException("USER_ID " + userId + "NOT_FOUND"));
        if (!appUserUpdate.getEmail().equals("")) {
            user.get().setEmail(appUserUpdate.getEmail());
        }
        if (!appUserUpdate.getPassword().equals("")) {
            user.get().setPassword(appUserUpdate.getPassword());
        }
        authRepository.save(user.get());
    }

    @Override
    public com.victor.garageapi.dto.User refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtUtility.generateTokenWithUsername(refreshTokenRequest.getEmail());
        Optional<AppUser> user = authRepository.findAppUserByEmail(refreshTokenRequest.getEmail());
        user.orElseThrow(() -> new UsernameNotFoundException("USER_WITH_EMAIL " + refreshTokenRequest.getEmail() + " DOES_NOT_EXIST"));
        return com.victor.garageapi.dto.User.builder()
                .id(user.get().getId())
                .email(user.get().getEmail())
                .email_verified_at(user.get().getVerifiedAt())
                .created_at(user.get().getCreatedAt())
                .access_token(token)
                .refresh_token(refreshTokenRequest.getRefreshToken())
                .expires_at(new Date(jwtUtility.getExpirationDateFromToken(token).getTime()).toInstant())
                .build();
    }


    @Override
    public AppUser UserSchoolByEmail(String email) throws EmailNotFoundException {
        Optional<AppUser> user = authRepository.findAppUserByEmail(email);
        user.orElseThrow(() -> new EmailNotFoundException("EMAIL_NOT_FOUND"));

        return user.get();
    }



}
