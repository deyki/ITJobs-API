package com.deyki.userservice.model;

public record UserResponse(String username, String firstName, String lastName, String email, Integer phoneNumber) {
}