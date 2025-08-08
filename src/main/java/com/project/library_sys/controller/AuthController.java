package com.project.library_sys.controller;

import com.project.library_sys.model.AppUser;
import com.project.library_sys.repository.AppUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static class SignupRequest {
        @NotBlank public String username;
        @NotBlank public String password;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req) {
        if (userRepository.existsByUsername(req.username)) {
            return ResponseEntity.badRequest().body("Username already taken");
        }
        AppUser u = new AppUser(req.username, passwordEncoder.encode(req.password), "ROLE_USER");
        userRepository.save(u);
        return ResponseEntity.ok("User registered");
    }
}
