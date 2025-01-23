package io.github.Hoang604.demo_shopping_application.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.Hoang604.demo_shopping_application.model.MyUserDetails;
import org.springframework.ui.Model;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        if (authentication == null) {
            return "login";
        }
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        model.addAttribute("userId", userDetails.getId());
        return "index";
    }
}
