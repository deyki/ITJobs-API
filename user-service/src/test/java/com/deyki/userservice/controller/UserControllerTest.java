package com.deyki.userservice.controller;

import com.deyki.userservice.model.*;
import com.deyki.userservice.security.JWTUtil;
import com.deyki.userservice.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private JWTUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private AuthRequest authRequest;
    private AuthResponse signInResponse;
    private AuthResponse loggedIn;
    private UserResponse userResponse;
    private UserProfileDetailsRequest userProfileDetailsRequest;
    private UserContactInfo userContactInfo;

    @BeforeEach
    void setUp() {
        this.authRequest = new AuthRequest("deyki", "password");

        this.signInResponse = new AuthResponse(String.format("Welcome %s!", "deyki"));

        this.loggedIn = new AuthResponse("Logged in!");

        this.userResponse = new UserResponse(
                "deyki", "Nikolay", "Nikolaev", "NN@gmail.com", 123456789
        );

        this.userProfileDetailsRequest = new UserProfileDetailsRequest(
                "Nikolay",
                "Nikolaev",
                "NN@gmail.com",
                123456789
        );

        this.userContactInfo = new UserContactInfo("NN@gmail.com", 123456789);
    }

    @Test
    void whenSignUp_thenReturnCorrectResult() throws Exception {
        Mockito.when(userService.signUp(authRequest)).thenReturn(signInResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(MockMvcResultMatchers.content().string(signInResponse.message()))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void whenSignIn_thenReturnCorrectResult() throws Exception {
        Mockito.when(userService.signIn(authRequest)).thenReturn(loggedIn);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(MockMvcResultMatchers.content().string(loggedIn.message()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void whenCreateProfileDetails_thenReturnCorrectResult() throws Exception {
        Mockito.when(userService.createProfileDetails(1L, userProfileDetailsRequest)).thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/create-profile-details/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProfileDetailsRequest)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is("deyki")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Nikolay")))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void whenGetUserById_thenReturnCorrectResult() throws Exception {
        Mockito.when(userService.getUserById(1L)).thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is("deyki")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void whenGetUserContactInfoByUsername_thenReturnCorrectResult() throws Exception {
        Mockito.when(userService.getUserContactInfoByUsername("deyki")).thenReturn(userContactInfo);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/contacts/deyki")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(userContactInfo.email())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is(userContactInfo.phoneNumber())))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}