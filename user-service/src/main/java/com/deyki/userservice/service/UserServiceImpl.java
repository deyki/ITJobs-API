package com.deyki.userservice.service;

import com.deyki.userservice.entity.User;
import com.deyki.userservice.error.InvalidCredentialsException;
import com.deyki.userservice.error.UserNotFoundException;
import com.deyki.userservice.model.*;
import com.deyki.userservice.repository.UserRepository;
import com.deyki.userservice.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtil jwtUtil;
    private final AuthManagerService authManagerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found!", username)));

        return new org.springframework
                .security
                .core
                .userdetails
                .User(user.getUsername(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("USER")));
    }

    @Override
    public AuthResponse signUp(AuthRequest authRequest) {
        boolean blankCredentials = checkBlankCredentials(authRequest);
        if (blankCredentials) {
            throw new InvalidCredentialsException("Blank credentials not allowed!");
        }
        log.info("Credentials are not blank!");

        userRepository.findByUsername(authRequest.username())
                .ifPresent(user -> {
                    throw new InvalidCredentialsException(String.format("Username %s is already taken!", authRequest.username()));
                });
        log.info("Username is not taken!");

        User user = new User();
        user.setUsername(authRequest.username());
        user.setPassword(bCryptPasswordEncoder.encode(authRequest.password()));

        userRepository.save(user);
        log.info("User is added to database!");

        return new AuthResponse(String.format("Welcome %s!", user.getUsername()));
    }

    @Override
    public AuthResponse signIn(AuthRequest authRequest) {
        boolean blankCredentials = checkBlankCredentials(authRequest);
        if (blankCredentials) {
            throw new InvalidCredentialsException("Blank credentials not allowed!");
        }
        log.info("Credentials are not blank!");

        User user = userRepository.findByUsername(authRequest.username())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password!"));

        if (!bCryptPasswordEncoder.matches(authRequest.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password!");
        }
        log.info("Credentials are valid!");

        final String JWToken = jwtUtil.generateToken(user.getUsername());

        authManagerService.authenticate(authRequest);

        return new AuthResponse(JWToken);
    }

    @Override
    public UserResponse createProfileDetails(Long userID, UserProfileDetailsRequest userProfileDetailsRequest) {
        User user = userRepository
                .findById(userID)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        userRepository
                .findByEmail(userProfileDetailsRequest.email())
                .ifPresent(user1 -> {
                    throw new InvalidCredentialsException(String.format("Email %s is not available!", userProfileDetailsRequest.email()));
                });

        userRepository
                .findByPhoneNumber(userProfileDetailsRequest.phoneNumber())
                .ifPresent(user1 -> {
                    throw new InvalidCredentialsException("This phone number is unavailable!");
                });

        user.setFirstName(userProfileDetailsRequest.firstName());
        user.setLastName(userProfileDetailsRequest.lastName());
        user.setEmail(userProfileDetailsRequest.email());
        user.setPhoneNumber(userProfileDetailsRequest.phoneNumber());

        userRepository.save(user);
        log.info(String.format("User %s added profile details!", user.getUsername()));

        return new UserResponse(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber());
    }

    @Override
    public UserResponse getUserById(Long userID) {
        return userRepository
                .findById(userID)
                .map(user -> new UserResponse(
                        user.getUsername(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPhoneNumber()
                ))
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
    }

    @Override
    public UserContactInfo getUserContactInfoByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .map(user -> new UserContactInfo(user.getEmail(), user.getPhoneNumber()))
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s not found!", username)));
    }

    @Override
    public boolean checkBlankCredentials(AuthRequest authRequest) {
        if (authRequest.username().isBlank()) {
            return true;
        } else if (authRequest.password().isBlank()) {
            return true;
        } else if (authRequest.username().isBlank() && authRequest.password().isBlank()) {
            return true;
        }
        return false;
    }
}