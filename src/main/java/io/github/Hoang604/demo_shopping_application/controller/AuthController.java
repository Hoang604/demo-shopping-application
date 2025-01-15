package io.github.Hoang604.demo_shopping_application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @PostMapping("/register") 
    public String register() {
        return "register"; // Trả về tên của view register.html
    }
}