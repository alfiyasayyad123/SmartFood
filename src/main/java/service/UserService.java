package com.smartfood.backend.service;

import com.smartfood.backend.dto.LoginRequest;
import com.smartfood.backend.dto.LoginResponse;
import com.smartfood.backend.entity.User;
import com.smartfood.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;


    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository =
                userRepository;

        this.passwordEncoder =
                passwordEncoder;

        this.jwtService =
                jwtService;
    }


    // ==========================================
    // REGISTER USER
    // ==========================================

    public User registerUser(User user) {

        if (userRepository.existsByEmail(
                user.getEmail())) {

            throw new RuntimeException(
                    "Email already registered"
            );
        }


        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );


        // ======================================
        // SECURITY:
        // EVERY NEW USER STARTS AS CUSTOMER
        // ======================================

        user.setRole("CUSTOMER");


        return userRepository.save(user);
    }


    // ==========================================
    // LOGIN USER
    // ==========================================

    public LoginResponse loginUser(
            LoginRequest request) {

        User user =
                userRepository
                        .findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid email or password"
                                )
                        );


        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }


        // ======================================
        // GENERATE JWT
        // ======================================

        String token =
                jwtService.generateToken(
                        user.getEmail(),
                        user.getRole()
                );


        return new LoginResponse(
                "Login successful",
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                token
        );
    }
}