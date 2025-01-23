package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.model.MyUserDetails;
import io.github.Hoang604.demo_shopping_application.model.User;
import io.github.Hoang604.demo_shopping_application.service.UserService;
import io.github.Hoang604.demo_shopping_application.dto.CreateUserDTO;
import io.github.Hoang604.demo_shopping_application.dto.UpdateUserDTO;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        // Add users to model (it contain attributes that I want to render in view)
        model.addAttribute("users", users);
        return "user/users";
    }

    @PostMapping(value = "/", consumes = "application/json")
    public String createUser(@RequestBody CreateUserDTO userDTO, Model model) {
        if (userService.exist(userDTO.username())) {
            model.addAttribute("error", "The user with username " + userDTO.username() + " already exists. Please choose another username.");
            return "error/user-already-exists";
        }
        User newUser = userService.createUser(userDTO);
        model.addAttribute("message", "User created successfully");
        return "redirect:user/user/" + newUser.getId();
    }

    @GetMapping("/new")
    public String showCreateUserForm(Model model) {
        // add model attribute to bind form data
        model.addAttribute("user", new User());
        return "user/create-user";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable int id, Model model, Authentication authentication) {
        User user = userService.getUserById(id);
        if (user == null) {
            return "error/404";
        }
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        model.addAttribute("UserId", userDetails.getId());
        model.addAttribute("user", user);
        return "user/user";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public String updateUser(@PathVariable int id, @RequestBody UpdateUserDTO userDTO, Model model) {
        System.out.println("userDTO: " + userDTO);
        User newUser = userService.updateUser(userDTO, id);
        if (newUser == null) {
            return "error/404";
        }
        return "redirect:user/user/" + id;
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable int id, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        
        if (userService.getUserById(id) == null) {
            return "error/404";
        }
        userService.deleteUserById(id);
        return "redirect:/users";
    }
}