package com.example.ecommerce.user;

import com.example.ecommerce.exception.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public User register(String email, String rawPassword) {
        if (repo.existsByEmail(email)) {
            throw new BadRequestException("Email already exists");
        }
        User u = new User();
        u.setEmail(email);
        u.setPassword(encoder.encode(rawPassword));
        u.setRoles(Set.of(Role.USER));
        return repo.save(u);
    }

    public User getByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));
    }
}
