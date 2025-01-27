package io.github.Hoang604.demo_shopping_application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.Hoang604.demo_shopping_application.dto.CreateUserDTO;
import io.github.Hoang604.demo_shopping_application.model.User;
import io.github.Hoang604.demo_shopping_application.service.UserService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import java.util.Collections;
import java.util.Map;

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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createUser(
        @Valid @RequestBody CreateUserDTO userDTO,
        BindingResult bindingResult
    ) {
        // Validate input
        if (bindingResult.hasErrors()) {
                // bindingResult automaticaly contains the validation errors
                Map<String, String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existingMsg, newMsg) -> existingMsg + ", " + newMsg
                        ));
                String errorMessage = errors.values().stream()
                    .collect(Collectors.joining(", "));
                Map<String, String> error = Collections.singletonMap(
                    "error", errorMessage
                );
                return ResponseEntity.badRequest()
                    .body(error);
            }

        // Check existing user
        if (userService.exist(userDTO.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(
                    "error", 
                    "Username '" + userDTO.username() + "' already exists"
                ));
        }

        try {
            User newUser = userService.createUser(userDTO);
            Map<String, Object> response = Map.of(
                "message", "User created successfully",
                "user", newUser
            );
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Collections.singletonMap(
                    "error", 
                    "Server error: " + e.getMessage()
                ));
        }
    }
}