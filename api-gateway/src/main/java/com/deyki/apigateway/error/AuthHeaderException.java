package com.deyki.apigateway.error;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class AuthHeaderException extends ResponseStatusException {

    public AuthHeaderException(HttpStatusCode status) {
        super(status);
    }

    public AuthHeaderException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}