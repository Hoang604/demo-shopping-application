package io.github.Hoang604.demo_shopping_application.dto;

import io.github.Hoang604.demo_shopping_application.model.Category;

public record UpdateProductDTO(String title, Double price, Category category, Double ratingRate, Integer ratingCount, String image, String description) {
    public void print() {
        System.out.println("title: " + title);
        System.out.println("price: " + price);
        System.out.println("category: " + category);
        System.out.println("ratingRate: " + ratingRate);
        System.out.println("ratingCount: " + ratingCount);
        System.out.println("image: " + image);
        System.out.println("description: " + description);
    }
} 