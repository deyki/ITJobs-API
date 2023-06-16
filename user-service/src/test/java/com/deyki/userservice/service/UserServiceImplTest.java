package com.deyki.userservice.service;

import com.deyki.userservice.entity.User;
import com.deyki.userservice.model.*;
import com.deyki.userservice.repository.UserRepository;
import com.deyki.userservice.security.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JWTUtil jwtUtil;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private User user;

    @BeforeEach
    void setUp() {
        this.user = User
                .builder()
                .userID(1L)
                .username("deyki")
                .password(bCryptPasswordEncoder.encode("password"))
                .build();
    }

    @Test
    void whenLoadUserByUsername_thenReturnCorrectResult() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

        assertEquals(userDetails.getUsername(), user.getUsername());
        log.info(String.format("%s == %s", userDetails.getUsername(), user.getUsername()));
    }

    @Test
    void whenSignUp_thenReturnCorrectResult() {
        AuthRequest authRequest = new AuthRequest("newUser", "password");
        User newUser = new User();
        newUser.setUsername(authRequest.username());
        newUser.setPassword(bCryptPasswordEncoder.encode(authRequest.password()));

        Mockito.when(userRepository.findByUsername(authRequest.username())).thenReturn(Optional.of(newUser));
        Mockito.when(userRepository.save(newUser)).thenReturn(newUser);

        UserDetails userDetails = userService.loadUserByUsername(authRequest.username());
        assertEquals(userDetails.getUsername(), newUser.getUsername());
        assertTrue(bCryptPasswordEncoder.matches(authRequest.password(), userDetails.getPassword()));
        log.info(String.format("%s == %s", userDetails.getUsername(), newUser.getUsername()));
    }

    @Test
    void whenSignIn_thenReturnCorrectResult() {
        String JWToken = "JWToken";

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        Mockito.when(jwtUtil.generateToken(user.getUsername())).thenReturn(JWToken);

        AuthResponse authResponse = userService.signIn(new AuthRequest(user.getUsername(), "password"));

        assertEquals(authResponse.message(), JWToken);
        log.info(String.format("Password matches -> %s", bCryptPasswordEncoder.matches("password", user.getPassword())));
    }

    @Test
    void whenCreateUserProfileDetails_thenReturnCorrectResult() {
        UserProfileDetailsRequest UPDRequest = new UserProfileDetailsRequest(
                "Nikolay",
                "Nikolaev",
                "NN@gmail.com",
                123456789
        );
        Mockito.when(userRepository.findById(user.getUserID())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByPhoneNumber(user.getPhoneNumber())).thenReturn(Optional.of(user));

        log.info(String.format("Email -> %s\nEmail2 -> %s\n", user.getEmail(), UPDRequest.email()));
        log.info(String.format("PhoneNumber -> %d\nPhoneNumber2 -> %d\n", user.getPhoneNumber(), UPDRequest.phoneNumber()));

        user.setFirstName(UPDRequest.firstName());
        user.setLastName(UPDRequest.lastName());
        user.setEmail(UPDRequest.email());
        user.setPhoneNumber(UPDRequest.phoneNumber());

        Mockito.when(userRepository.save(user)).thenReturn(user);

        userService.createProfileDetails(user.getUserID(), UPDRequest);

        assertEquals(user.getFirstName(), UPDRequest.firstName());
    }

    @Test
    void whenGetUserById_thenReturnCorrectResult() {
        Mockito.when(userRepository.findById(user.getUserID())).thenReturn(Optional.of(user));

        user.setFirstName("Nikolay");
        user.setLastName("Nikolaev");
        user.setEmail("NN@gmail.com");
        user.setPhoneNumber(123456789);

        UserResponse userResponse = userService.getUserById(user.getUserID());

        assertEquals(userResponse.username(), user.getUsername());
        assertEquals(userResponse.firstName(), user.getFirstName());
        log.info(String.format("%s == %s", userResponse.username(), user.getUsername()));
        log.info(String.format("%s == %s", userResponse.firstName(), user.getFirstName()));
    }

    @Test
    void whenGetUserContactInfoByUsername_thenReturnCorrectResult() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        user.setEmail("NN@gmail.com");
        user.setPhoneNumber(123456789);

        UserContactInfo userContactInfo = userService.getUserContactInfoByUsername(user.getUsername());

        assertEquals(userContactInfo.email(), user.getEmail());
        assertEquals(userContactInfo.phoneNumber(), user.getPhoneNumber());
        log.info(String.format("%s == %s", userContactInfo.email(), user.getEmail()));
        log.info(String.format("%s == %s", userContactInfo.phoneNumber(), user.getPhoneNumber()));
    }

    @Test
    void whenCheckBlankCredentials_thenReturnCorrectResult() {
        boolean isBlankTest1 = userService.checkBlankCredentials(new AuthRequest("", ""));
        boolean isBlankTest2 = userService.checkBlankCredentials(new AuthRequest("", "password"));
        boolean isBlankTest3 = userService.checkBlankCredentials(new AuthRequest("username", ""));
        boolean isBlankTest4 = userService.checkBlankCredentials(new AuthRequest("username", "password"));

        assertTrue(isBlankTest1);
        assertTrue(isBlankTest2);
        assertTrue(isBlankTest3);
        assertFalse(isBlankTest4);
    }
}