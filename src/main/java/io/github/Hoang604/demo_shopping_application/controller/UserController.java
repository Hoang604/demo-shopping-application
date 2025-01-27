package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.model.MyUserDetails;
import io.github.Hoang604.demo_shopping_application.model.User;
import io.github.Hoang604.demo_shopping_application.service.UserService;
import io.github.Hoang604.demo_shopping_application.utils.SecurityUtils;
import jakarta.validation.Valid;
import io.github.Hoang604.demo_shopping_application.dto.CreateUserWithRoleDTO;
import io.github.Hoang604.demo_shopping_application.dto.UpdateUserDTO;

import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getAllUsers(Model model, Authentication authentication) {
        if (!SecurityUtils.isAdmin(authentication)) {
            return "error/403";
        }
        List<User> users = userService.getAllUsers();
        // Add users to model (it contain attributes that I want to render in view)
        model.addAttribute("users", users);
        return "user/users";
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    // @Valid: validate the input (with the constraints defined in the DTO)
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserWithRoleDTO userDTO,
            BindingResult bindingResult, Authentication authentication) {
        try {
            // 1. Authorization check
            if (!SecurityUtils.isAdmin(authentication)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Collections.singletonMap("error", "Access denied"));
            }
            
            // 2. Validation errors
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

            // 3. Check existing user
            if (userService.exist(userDTO.username())) {
                Map<String, String> error = Collections.singletonMap(
                    "error", "Username '" + userDTO.username() + "' already exists"
                );
                System.out.println("Error: " + error);
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(error);
            }
            
            // 4. Create user
            User newUser = userService.createUserWithRole(userDTO);
            
            return ResponseEntity.created(
                URI.create("/users/" + newUser.getId())
            ).body(Collections.singletonMap(
                "message", 
                "User created successfully"
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Collections.singletonMap(
                    "error", 
                    "Server error: " + e.getMessage()
                ));
        }
    }

    @GetMapping("/new")
    public String showCreateUserForm(Model model) {
        return "user/create-user";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable int id, Model model, Authentication authentication) {
        if (!SecurityUtils.isAdmin(authentication) && id != SecurityUtils.getCurrentUserId(authentication)) {
            return "error/403";
        }
        User user = userService.getUserById(id);
        if (user == null) {
            return "error/404";
        }
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        model.addAttribute("UserId", userDetails.getId());
        model.addAttribute("user", user);
        return "user/user";
    }

    @GetMapping("/{id}/update")
    public String showUpdateUserForm(@PathVariable int id, Model model,
             Authentication authentication, @RequestParam(required = false) String password) {
        if (!SecurityUtils.isAdmin(authentication) && id != SecurityUtils.getCurrentUserId(authentication)) {
            return "error/403";
        }
        User user = userService.getUserById(id);
        if (user == null) {
            return "error/404";
        }
        model.addAttribute("password", password);
        model.addAttribute("user", user);
        return "user/update-user";
    }

    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable int id,
            @Valid @RequestBody UpdateUserDTO userDTO,
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

        try {
            User updatedUser = userService.updateUser(userDTO, id);
            if (updatedUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "User not found"));
            }
            
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Collections.singletonMap("message", "Internal server error"));
        }
    }
    
    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(
            @PathVariable int id,
            Authentication authentication
    ) {
        // Check authentication
        if (SecurityUtils.getCurrentUserId(authentication) != id
            && !SecurityUtils.isAdmin(authentication)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Unauthorized access"));
        }

        // Check user existence
        User userToDelete = userService.getUserById(id);
        if (userToDelete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "User not found"));
        }

        try {
            userService.deleteUserById(id);
            System.out.println("User deleted successfully");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Error deleting user"));
        }
    }
}