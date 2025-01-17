package io.github.Hoang604.demo_shopping_application.dto;

public record UpdateUserDTO(String password, String phoneNumber, String role) {
    public void print() {
        System.out.println("Password: " + password);
        System.out.println("Phone number: " + phoneNumber);
        System.out.println("Role: " + role);
    }
}