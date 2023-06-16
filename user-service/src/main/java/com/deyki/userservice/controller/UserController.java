package com.deyki.userservice.controller;

import com.deyki.userservice.model.AuthRequest;
import com.deyki.userservice.model.UserContactInfo;
import com.deyki.userservice.model.UserProfileDetailsRequest;
import com.deyki.userservice.model.UserResponse;
import com.deyki.userservice.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody AuthRequest authRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.signUp(authRequest).message());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody AuthRequest authRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(userService.signIn(authRequest).message()))
                .body("Logged in!");
    }

    @PostMapping("/create-profile-details/{userID}")
    public ResponseEntity<UserResponse> createProfileDetails(@PathVariable Long userID, @RequestBody UserProfileDetailsRequest userProfileDetailsRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createProfileDetails(userID, userProfileDetailsRequest));
    }

    @GetMapping("/{userID}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userID) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserById(userID));
    }

    @GetMapping("/contacts/{username}")
    public ResponseEntity<UserContactInfo> getUserContactInfoByUsername(@PathVariable String username) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserContactInfoByUsername(username));
    }
}
