package io.github.Hoang604.demo_shopping_application.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column(nullable = false, name = "name")
    String name;

    public Category() {
        name = "";
    }

    public Category(String name) {
        this.name = name;
    }
}
