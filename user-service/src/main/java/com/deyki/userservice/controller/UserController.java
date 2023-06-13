package com.deyki.userservice.controller;

import com.deyki.userservice.model.AuthRequest;
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
}
