/*
 * This class is used to create a UserDetailsService object that will be used to authenticate users
 */

package io.github.Hoang604.demo_shopping_application.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import io.github.Hoang604.demo_shopping_application.model.User;
import io.github.Hoang604.demo_shopping_application.model.MyUserDetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import io.github.Hoang604.demo_shopping_application.repository.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = null;
        if (username.matches("\\d+")) { // Biểu thức chính quy để kiểm tra số
            user = userRepository.findByPhoneNumber(username);
        }

        else {
            user = userRepository.findByUsername(username);
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new MyUserDetails(user, username);

    }
    
}
