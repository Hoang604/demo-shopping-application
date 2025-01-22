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
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        // Kiểm tra input là username hay phone number
        User user;
        if (input.matches("\\d+")) { // Nếu input là số → phone number
            user = userRepository.findByPhoneNumber(input);
            if (user == null) {
                throw new UsernameNotFoundException("Phone number not found");
            }
        } else { // Ngược lại → username
            user = userRepository.findByUsername(input);
            if (user == null) {
                throw new UsernameNotFoundException("Username not found");
            }
        }

        return new MyUserDetails(user); // Không cần truyền input
    }
}