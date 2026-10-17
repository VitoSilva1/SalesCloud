package com.example.auth_service.service;

import com.example.auth_service.exception.ResourceNotFoundException;
import com.example.auth_service.model.User;
import com.example.auth_service.repository.UserRepository;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository; private final PasswordEncoder encoder;
    public UserService(UserRepository repository, PasswordEncoder encoder) { this.repository = repository; this.encoder = encoder; }
    public List<User> findAll() { return repository.findAll(); }
    public User findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found: " + id)); }
    public User register(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) throw new IllegalArgumentException("Email already registered");
        user.setPassword(encoder.encode(user.getPassword())); return repository.save(user);
    }
    public User update(Long id, User input) { User user = findById(id); user.setName(input.getName()); user.setEmail(input.getEmail()); user.setRole(input.getRole()); if (input.getPassword() != null && !input.getPassword().isBlank()) user.setPassword(encoder.encode(input.getPassword())); return repository.save(user); }
    public void delete(Long id) { repository.delete(findById(id)); }
    public boolean login(String email, String password) { return repository.findByEmail(email).map(u -> encoder.matches(password, u.getPassword())).orElse(false); }
}
