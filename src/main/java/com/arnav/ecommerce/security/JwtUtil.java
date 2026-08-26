package com.arnav.ecommerce.security;

import com.arnav.ecommerce.model.User;
import com.arnav.ecommerce.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long EXPIRATION_MS = 1000 * 60 * 60 * 24;

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(key)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Service
    public static class AuthService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private JwtUtil jwtUtil;

        public User register(String name, String email, String rawPassword) {
            if (userRepository.existsByEmail(email)) {
                throw new RuntimeException("Email already registered");
            }

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPasswordHash(passwordEncoder.encode(rawPassword));

            return userRepository.save(user);
        }

        public String login(String email, String rawPassword) {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Invalid credentials"));

            if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
                throw new RuntimeException("Invalid credentials");
            }

            return jwtUtil.generateToken(user.getEmail());
        }
    }
}