package io.github.Hoang604.demo_shopping_application.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column(nullable = false, name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(nullable = false, name = "price")
    private double price;
    @Column(name = "image")
    private String image;
    @Column(name = "rating_rate")
    private int rating_rate;
    @Column(name = "rating_count")
    private int rating_count;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    public Product() {
        this.title = "";
        this.description = "";
        this.price = 0;
        this.category = null;
        this.image = "";
        this.rating_rate = 0;
        this.rating_count = 0;
    }

    public Product(String title, double Price) {
        this.title = title;
        this.price = Price;
        this.description = "";
        this.category = null;
        this.image = "";
        this.rating_rate = 0;
        this.rating_count = 0;
    }
    public Product(String title, String description, double price,
            Category category, String image, int rating_rate, int rating_count) {
        
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image = image;
        this.rating_rate = rating_rate;
        this.rating_count = rating_count;
    }
    
    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public double getPrice() {
        return this.price;
    }

    public Category getCategory() {
        return this.category;
    }

    public String getImage() {
        return this.image;
    }

    public int getRatingRate() {
        return this.rating_rate;
    }

    public int getRatingCount() {
        return this.rating_count;
    }


    public void updateId(int id) {
        this.id = id;
    }
    
    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updatePrice(double price) {
        this.price = price;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }

    public void updateImage(String image) {
        this.image = image;
    }
    
    public void updateRatingRate(int rating_rate) {
        this.rating_rate = (rating_rate + this.rating_rate * this.rating_count) / (this.rating_count + 1);
    }

    public void updateRatingCount(int rating_count) {
        this.rating_count = rating_count;
    }

    public void printProduct() {
        System.out.println("ID: " + this.id);
        System.out.println("Title: " + this.title);
        System.out.println("Description: " + this.description);
        System.out.println("Price: " + this.price);
        System.out.println("Category: " + this.category);
        System.out.println("Image: " + this.image);
        System.out.println("Rating Rate: " + this.rating_rate);
        System.out.println("Rating Count: " + this.rating_count);
    }

    public String toString() {
        return "ID: " + this.id + "\n" +
                "Title: " + this.title + "\n" +
                "Description: " + this.description + "\n" +
                "Price: " + this.price + "\n" +
                "Category: " + this.category + "\n" +
                "Image: " + this.image + "\n" +
                "Rating Rate: " + this.rating_rate + "\n" +
                "Rating Count: " + this.rating_count + "\n";
    }
}
