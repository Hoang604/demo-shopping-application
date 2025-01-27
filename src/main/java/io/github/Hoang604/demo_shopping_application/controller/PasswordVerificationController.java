package io.github.Hoang604.demo_shopping_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.Hoang604.demo_shopping_application.dto.PasswordVerificationDTO;
import io.github.Hoang604.demo_shopping_application.model.User;
import io.github.Hoang604.demo_shopping_application.service.UserService;
import io.github.Hoang604.demo_shopping_application.utils.SecurityUtils;
import jakarta.validation.Valid;
import java.util.Collections;

@RestController
public class PasswordVerificationController {

    @Autowired
    private UserService userService;
    
    @Autowired // Inject bean đã config
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/api/verify-password")
    public ResponseEntity<?> verifyPassword(@Valid @RequestBody PasswordVerificationDTO dto,
            Authentication authentication) {
        if (dto.id() != SecurityUtils.getCurrentUserId(authentication)
                && !SecurityUtils.isAdmin(authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("error", "Access denied"));
        }
        
        User user = userService.getUserById(dto.id());

        // Sử dụng phương thức matches() để so sánh
        if (passwordEncoder.matches(dto.password(), user.getPassword())) {
            return ResponseEntity.ok().build();
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("message", "Incorrect password"));
    }
}