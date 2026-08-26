package com.arnav.ecommerce.controller;

import com.arnav.ecommerce.model.User;
import com.arnav.ecommerce.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    public record RegisterRequest(String name, String email, String password) {}
    public record LoginRequest(String email, String password) {}

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        User user = authService.register(req.name(), req.email(), req.password());
        return ResponseEntity.status(201).body(Map.of("id", user.getId(), "email", user.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        String token = authService.login(req.email(), req.password());
        return ResponseEntity.ok(Map.of("token", token));
    }
}