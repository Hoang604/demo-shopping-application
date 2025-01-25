package io.github.Hoang604.demo_shopping_application.service;

import io.github.Hoang604.demo_shopping_application.dto.CreateUserDTO;
import io.github.Hoang604.demo_shopping_application.dto.UpdateUserDTO;
import io.github.Hoang604.demo_shopping_application.model.User;
import io.github.Hoang604.demo_shopping_application.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public boolean exist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public User createUser(CreateUserDTO userDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        User user = new User(
                userDTO.username(),
                encoder.encode(userDTO.password()),
                "USER",
                userDTO.phoneNumber());
        return userRepository.save(user);
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(UpdateUserDTO userDTO, int userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setPassword(encoder.encode(userDTO.password()));
        user.setRole(userDTO.role());
        user.setPhoneNumber(userDTO.phoneNumber());

        return userRepository.save(user);
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
