package com.deyki.jobservice.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserClient {

    @GetExchange("/api/user/{userID}")
    ResponseEntity<UserResponse> getUserById(@PathVariable Long userID);
}
