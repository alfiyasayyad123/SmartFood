package com.smartfood.backend.controller;

import com.smartfood.backend.dto.LoginRequest;
import com.smartfood.backend.dto.LoginResponse;
import com.smartfood.backend.entity.User;
import com.smartfood.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {

        User registeredUser = userService.registerUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(
            @RequestBody LoginRequest request) {

        LoginResponse response = userService.loginUser(request);

        return ResponseEntity.ok(response);
    }
}