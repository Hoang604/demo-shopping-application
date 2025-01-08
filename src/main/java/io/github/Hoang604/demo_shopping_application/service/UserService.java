package io.github.Hoang604.demo_shopping_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.Hoang604.demo_shopping_application.repository.UserRepository;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
}
