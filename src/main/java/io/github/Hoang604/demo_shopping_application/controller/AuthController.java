package io.github.Hoang604.demo_shopping_application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Trả về tên của view login.html
    }
}