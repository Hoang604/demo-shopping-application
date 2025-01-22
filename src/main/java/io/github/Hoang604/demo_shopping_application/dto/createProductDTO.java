package io.github.Hoang604.demo_shopping_application.dto;

public record createProductDTO(String title, Double price,
         Integer categoryId, String image, String description) {
    public void print() {
        System.out.println("title: " + title);
        System.out.println("price: " + price);
        System.out.println("categoryID: " + categoryId);
        System.out.println("image: " + image);
        System.out.println("description: " + description);
    }
}
