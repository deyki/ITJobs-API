package com.deyki.userservice.service;

import com.deyki.userservice.model.AuthRequest;
import com.deyki.userservice.model.AuthResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AuthResponse signUp(AuthRequest authRequest);

    AuthResponse signIn(AuthRequest authRequest);

    boolean checkBlankCredentials(AuthRequest authRequest);
}