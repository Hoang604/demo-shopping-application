package io.github.Hoang604.demo_shopping_application.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;

public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(nullable = false, name = "username")
    private String username;
    @Column(nullable = false, name = "password")
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(nullable = false, name = "role")
    private String role;

    public User() {
        this.username = "";
        this.password = "";
        this.phoneNumber = "";
        this.role = "";
    }

    public User(String username, String password, String phoneNumber, String role) {
        if (role == null || (!role.equals("admin") && !role.equals("customer"))) {
            throw new IllegalArgumentException("role must be either 'admin' or 'customer'");
        }
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(String role) {
        if (role == null || (!role.equals("admin") && !role.equals("customer"))) {
            throw new IllegalArgumentException("role must be either 'admin' or 'customer'");
        }
        this.role = role;
    }

    public void printUser() {
        System.out.println("User ID: " + this.id);
        System.out.println("Username: " + this.username);
        System.out.println("Password: " + this.password);
        System.out.println("Phone Number: " + this.phoneNumber);
        System.out.println("Role: " + this.role);
    }

    public void printOnlyUser() {
        System.out.println(
                this.id + " " + this.username + " " + this.password + " " + this.phoneNumber + " " + this.role);
    }
    
    public int compareTo(User other) {
        int result = this.username.compareTo(other.username);
        if (result == 0) {
            result = this.password.compareTo(other.password);
            if (result == 0) {
                result = this.phoneNumber.compareTo(other.phoneNumber);
                if (result == 0) {
                    result = this.role.compareTo(other.role);
                }
            }
        }
        return result;
    }
}
