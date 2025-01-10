package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.model.User;
import io.github.Hoang604.demo_shopping_application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;
import io.github.Hoang604.demo_shopping_application.dto.UpdateUserDTO;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping(consumes = "application/json")
    public String createUser(@RequestBody User user, Model model) {
        User newUser = userService.createUser(user);
        model.addAttribute("message", "User created successfully!");
        return "redirect:/api/users";
    }

    @GetMapping("/new")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "create-user";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        if (user == null) {
            return "error/404";
        }
        model.addAttribute("user", user);
        return "user";
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable int id, @RequestBody UpdateUserDTO userDTO) {
        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        userDTO.print();
    
        // Chỉ cập nhật các trường không null từ DTO
        if (userDTO.getUsername() != null) {
            existingUser.setUsername(userDTO.getUsername());
        }
        if (userDTO.getPassword() != null) {
            existingUser.setPassword(userDTO.getPassword());
        }
        if (userDTO.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        }
        if (userDTO.getRole() != null) {
            existingUser.setRole(userDTO.getRole());
        }
    
        userService.updateUser(existingUser);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable int id) {
        if (userService.getUserById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}