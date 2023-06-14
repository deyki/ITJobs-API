package com.deyki.jobservice.client;

public record UserResponse(String username, String firstName, String lastName, String email, Integer phoneNumber) {
}