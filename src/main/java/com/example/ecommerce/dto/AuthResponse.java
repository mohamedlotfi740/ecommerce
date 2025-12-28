package com.example.ecommerce.auth.dto;

public class AuthResponse {
    public String token;

    public AuthResponse() {}
    public AuthResponse(String token) { this.token = token; }
}
