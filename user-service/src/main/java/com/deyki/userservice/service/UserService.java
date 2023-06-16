package com.deyki.userservice.service;

import com.deyki.userservice.model.*;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AuthResponse signUp(AuthRequest authRequest);

    AuthResponse signIn(AuthRequest authRequest);

    UserResponse createProfileDetails(Long userID, UserProfileDetailsRequest userProfileDetailsRequest) ;

    UserResponse getUserById(Long userID);

    UserContactInfo getUserContactInfoByUsername(String username);

    boolean checkBlankCredentials(AuthRequest authRequest);
}