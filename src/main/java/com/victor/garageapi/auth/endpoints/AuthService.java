package com.victor.garageapi.auth.endpoints;

import com.victor.garageapi.dto.RefreshTokenRequest;
import com.victor.garageapi.dto.User;
import com.victor.garageapi.entity.AppUser;
import com.victor.garageapi.error.exceptions.EmailNotFoundException;
import com.victor.garageapi.error.exceptions.EmptyFieldsException;
import com.victor.garageapi.error.exceptions.EntityAlreadyExistsException;
import com.victor.garageapi.error.exceptions.TokenNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    UserDetails loadUserByUsername(String username);

    void signup(AppUser newAppUser) throws EmptyFieldsException, EntityAlreadyExistsException;

    AppUser UserSchoolByEmail(String email) throws EmailNotFoundException;

    AppUser findUserById(Long schoolId);

    void updateUser(AppUser appUserUpdate, Long schoolId);

    User refreshToken(RefreshTokenRequest refreshTokenRequest);

}
