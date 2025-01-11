package io.github.Hoang604.demo_shopping_application.service;

import io.github.Hoang604.demo_shopping_application.model.User;
import io.github.Hoang604.demo_shopping_application.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println("User found: " + user.getUsername()); // Thêm log để kiểm tra
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
            .password(user.getPassword())
            .roles(user.getRole())
            .build();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
