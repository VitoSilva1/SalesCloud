package com.example.auth_service.controller;

import com.example.auth_service.model.User;
import com.example.auth_service.service.UserService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/auth")
public class AuthController {
    private final UserService service;
    public AuthController(UserService service) { this.service = service; }
    @GetMapping("/users") public List<User> findAll() { return service.findAll(); }
    @GetMapping("/users/{id}") public User findById(@PathVariable Long id) { return service.findById(id); }
    @PostMapping("/register") public ResponseEntity<User> register(@Valid @RequestBody User user) { User saved = service.register(user); return ResponseEntity.created(URI.create("/auth/users/" + saved.getId())).body(saved); }
    @PutMapping("/users/{id}") public User update(@PathVariable Long id, @Valid @RequestBody User user) { return service.update(id, user); }
    @DeleteMapping("/users/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
    @PostMapping("/login") public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> body) { if (!service.login(body.get("email"), body.get("password"))) return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials")); return ResponseEntity.ok(Map.of("message", "Login successful")); }
}
