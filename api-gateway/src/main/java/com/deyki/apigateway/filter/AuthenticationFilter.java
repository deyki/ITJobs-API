package com.deyki.apigateway.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.deyki.apigateway.error.AuthHeaderException;
import com.deyki.apigateway.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private RouteValidator routeValidator;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (routeValidator.isSecured.test(request)) {
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new AuthHeaderException(HttpStatus.BAD_REQUEST, "Missing authorization header!");
                }

                String authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    jwtUtil.validateTokenAndRetrieveSubject(authHeader);
                } catch (JWTVerificationException exception) {
                    throw new AuthHeaderException(HttpStatus.BAD_REQUEST, "Invalid json web token!");
                }

            }
            ServerHttpRequest.Builder builder = request.mutate();
            return chain.filter(exchange.mutate().request(builder.build()).build());
        });
    }

    public static class Config{}
}