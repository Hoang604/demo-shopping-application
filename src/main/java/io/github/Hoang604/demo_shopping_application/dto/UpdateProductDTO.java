package io.github.Hoang604.demo_shopping_application.dto;

import io.github.Hoang604.demo_shopping_application.model.Category;

public class UpdateProductDTO {
    private String title;

    private Double price;

    private Category category;

    private Double ratingRate;

    private Integer ratingCount;

    private String image;

    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getRatingRate() {
        return ratingRate;
    }

    public void setRatingRate(double ratingRate) {
        this.ratingRate = ratingRate;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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