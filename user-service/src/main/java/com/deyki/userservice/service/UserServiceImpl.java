package com.deyki.userservice.service;

import com.deyki.userservice.entity.User;
import com.deyki.userservice.error.InvalidCredentialsException;
import com.deyki.userservice.model.AuthRequest;
import com.deyki.userservice.model.AuthResponse;
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
