package com.example.ecommerce.auth;

import com.example.ecommerce.auth.dto.*;
import com.example.ecommerce.user.User;
import com.example.ecommerce.user.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest req) {
        User u = userService.register(req.email, req.password);
        String token = jwtUtil.generateToken(u.getEmail(), u.getRoles().stream().map(Enum::name).toList());
        return new AuthResponse(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        User u = userService.getByEmail(req.email);
        if (!encoder.matches(req.password, u.getPassword())) {
            throw new com.example.ecommerce.exception.BadRequestException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(u.getEmail(), u.getRoles().stream().map(Enum::name).toList());
        return new AuthResponse(token);
    }
}
