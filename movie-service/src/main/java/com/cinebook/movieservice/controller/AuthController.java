package com.cinebook.movieservice.controller;

import com.cinebook.movieservice.entity.User;
import com.cinebook.movieservice.service.AuthService;
import com.cinebook.movieservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired 
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> payload) {
        try {
            // Registering user in MongoDB Atlas via AuthService
            User user = authService.register(
                payload.get("name"), 
                payload.get("email"), 
                payload.get("password")
            );
            
            // Note: user.getId() is now a String ObjectId from MongoDB
            return ResponseEntity.ok(Map.of(
                "message", "User registered successfully!", 
                "userId", user.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        try {
            // Authentication against MongoDB Atlas records
            User user = authService.login(payload.get("email"), payload.get("password"));
            
            // Generate JWT using the user's email as the subject
            String token = jwtUtil.generateToken(user.getEmail());

            // The userId sent back is the String-based MongoDB ID
            return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "userId", user.getId(), 
                "name", user.getName(),
                "email", user.getEmail(),
                "token", token 
            ));
        } catch (Exception e) {
            // Generic error message for security (don't reveal if email or password was wrong)
            return ResponseEntity.status(401).body(Map.of("error", "Invalid email or password"));
        }
    }
}