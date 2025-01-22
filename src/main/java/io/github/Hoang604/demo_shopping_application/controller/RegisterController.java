package io.github.Hoang604.demo_shopping_application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.Hoang604.demo_shopping_application.dto.CreateUserDTO;
import io.github.Hoang604.demo_shopping_application.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
    UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping 
    public String register() {
        return "register"; // Trả về tên của view register.html
    }

    @PostMapping(consumes = "application/json")
    public String createUser(@RequestBody CreateUserDTO userDTO, Model model) {
        if (userService.exist(userDTO.username())) {
            model.addAttribute("error", "The user with username " + userDTO.username() + " already exists. Please choose another username.");
            return "error/user-already-exists";
        }
        userService.createUser(userDTO);
        return "redirect:/login";
    }
}