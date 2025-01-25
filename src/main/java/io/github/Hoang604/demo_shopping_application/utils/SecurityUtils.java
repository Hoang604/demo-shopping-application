package io.github.Hoang604.demo_shopping_application.utils;

import org.springframework.security.core.Authentication;
import io.github.Hoang604.demo_shopping_application.model.MyUserDetails;


public class SecurityUtils {
    public static boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public static Integer getCurrentUserId(Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return userDetails.getId();
    }
}