package com.smartfood.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/restaurant")
    public String restaurant() {
        return "restaurant";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/order-confirmation")
    public String orderConfirmation() {
        return "order-confirmation";
    }

    @GetMapping("/order-tracking")
    public String orderTracking() {
        return "order-tracking";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}