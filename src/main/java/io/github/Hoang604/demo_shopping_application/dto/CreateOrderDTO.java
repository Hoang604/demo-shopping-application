package io.github.Hoang604.demo_shopping_application.dto;

public record CreateOrderDTO (Integer userId, Integer productId, Integer quantity) {
    public void print() {
        System.out.println("user: " + userId);
        System.out.println("productId: " + productId);
        System.out.println("quantity: " + quantity);
    }
}
