package com.deyki.userservice.service;

import com.deyki.userservice.model.AuthRequest;
import com.deyki.userservice.model.AuthResponse;
import com.deyki.userservice.model.UserProfileDetailsRequest;
import com.deyki.userservice.model.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AuthResponse signUp(AuthRequest authRequest);

    AuthResponse signIn(AuthRequest authRequest);

    UserResponse createProfileDetails(Long userID, UserProfileDetailsRequest userProfileDetailsRequest) ;

    UserResponse getUserById(Long userID);

    boolean checkBlankCredentials(AuthRequest authRequest);
}