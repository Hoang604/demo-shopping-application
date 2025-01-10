package io.github.Hoang604.demo_shopping_application.dto;

public class UpdateUserDTO {
    private String username;
    private String password;
    private String phoneNumber;
    private String role;

    // Getters và setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void print() {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Phone number: " + phoneNumber);
        System.out.println("Role: " + role);
    }
}