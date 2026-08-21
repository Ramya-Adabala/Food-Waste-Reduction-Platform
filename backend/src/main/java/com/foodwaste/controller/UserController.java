package com.foodwaste.controller;

import com.foodwaste.model.User;
import com.foodwaste.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // For React frontend
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> payload) {
        boolean success = userService.register(
            payload.get("username"),
            payload.get("password"),
            payload.get("email"),
            payload.get("phone"),
            payload.get("role"),
            payload.get("address")
        );
        if (success) {
            return ResponseEntity.ok().body(Map.of("message", "Registration successful"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Registration failed"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        User user = userService.login(payload.get("email"), payload.get("password"));
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        User user = userService.getUserDetails(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }
}
