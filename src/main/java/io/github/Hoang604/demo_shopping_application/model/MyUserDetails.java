package io.github.Hoang604.demo_shopping_application.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {
    
    private final User user;
    private final String inputUsername;

    public MyUserDetails(User user, String inputUsername) {
        this.user = user;
        this.inputUsername = inputUsername;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        if (inputUsername.matches("\\d+")) {
            return user.getPhoneNumber();
        }
        return user.getUsername();
    }
    
}
