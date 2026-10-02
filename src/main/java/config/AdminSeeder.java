package com.smartfood.backend.config;

import com.smartfood.backend.entity.User;
import com.smartfood.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        String adminEmail = "admin@smartfood.com";

        boolean exists =
                userRepository.existsByEmail(adminEmail);

        if (!exists) {

            User admin = new User();

            admin.setFullName("SmartFood Admin");
            admin.setEmail(adminEmail);

            admin.setPassword(
                    passwordEncoder.encode(
                            "Admin@123"
                    )
            );

            admin.setRole("ADMIN");

            userRepository.save(admin);

            System.out.println(
                    "=========================================="
            );

            System.out.println(
                    "SMARTFOOD ADMIN CREATED"
            );

            System.out.println(
                    "Email: admin@smartfood.com"
            );

            System.out.println(
                    "Password: Admin@123"
            );

            System.out.println(
                    "Role: ADMIN"
            );

            System.out.println(
                    "=========================================="
            );

        } else {

            System.out.println(
                    "SmartFood Admin already exists."
            );
        }
    }
}