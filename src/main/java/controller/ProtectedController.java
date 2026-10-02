package com.smartfood.backend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class ProtectedController {

    @GetMapping("/protected")
    public String protectedApi(Authentication authentication) {

        return "Welcome " + authentication.getName()
                + "! JWT authentication is working.";
    }
}