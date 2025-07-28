package com.yourorg.doctrivia.controller;

import com.yourorg.doctrivia.dto.*;
import com.yourorg.doctrivia.model.User;
import com.yourorg.doctrivia.repository.UserRepository;
import com.yourorg.doctrivia.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();
        User saved = userRepository.save(user);
        UserResponse resp = new UserResponse();
        resp.setId(saved.getId());
        resp.setUsername(saved.getUsername());
        resp.setRole(saved.getRole());
        return resp;
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody UserLoginRequest request) {
        User dbUser = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(request.getPassword(), dbUser.getPassword())) {
            JwtResponse jwt = new JwtResponse();
            jwt.setToken(jwtUtil.generateToken(dbUser.getUsername()));
            return jwt;
        }
        throw new RuntimeException("Invalid credentials");
    }
}
