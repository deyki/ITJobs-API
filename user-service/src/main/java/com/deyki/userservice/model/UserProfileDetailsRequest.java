package com.deyki.userservice.model;

public record UserProfileDetailsRequest(String firstName, String lastName, String email, Integer phoneNumber) {
}