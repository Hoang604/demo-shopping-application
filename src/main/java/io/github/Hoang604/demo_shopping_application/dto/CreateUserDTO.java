package io.github.Hoang604.demo_shopping_application.dto;

public record CreateUserDTO(String username, String password, String phoneNumber, String role) {
    public void print() {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Phone number: " + phoneNumber);
        System.out.println("Role: " + role);
    }
}
