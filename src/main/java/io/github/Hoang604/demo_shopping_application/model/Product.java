package io.github.Hoang604.demo_shopping_application.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column(nullable = false, name = "title")
    private String title;
    @Column(name = "description", length = 65535)
    private String description;
    @Column(nullable = false, name = "price")
    private double price;
    @Column(name = "image")
    private String image;
    @Column(name = "rating_rate")
    private double ratingRate;
    @Column(name = "rating_count")
    private double ratingCount;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    public Product() {
        this.title = "";
        this.description = "";
        this.price = 0;
        this.category = null;
        this.image = "";
        this.ratingRate = 0;
        this.ratingCount = 0;
    }

    public Product(String title, double Price) {
        this.title = title;
        this.price = Price;
        this.description = "";
        this.category = null;
        this.image = "";
        this.ratingRate = 0;
        this.ratingCount = 0;
    }
    public Product(String title, String description, double price,
            Category category, String image, int ratingRate, int ratingCount) {
        if (price < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }
        if (ratingRate < 0) {
            throw new IllegalArgumentException("ratingRate cannot be negative");
        }

        if (ratingCount < 0) {
            throw new IllegalArgumentException("ratingCount cannot be negative");
        }

        if (title == null) {
            throw new IllegalArgumentException("title cannot be null");
        }
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image = image;
        this.ratingRate = ratingRate;
        this.ratingCount = ratingCount;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("title cannot be null");
        }
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getRatingRate() {
        return ratingRate;
    }

    public void setRatingRate(double ratingRate) {
        if (ratingRate < 0) {
            throw new IllegalArgumentException("ratingRate cannot be negative");
        }
        this.ratingRate = ratingRate;
    }

    public double getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(double ratingCount) {
        if (ratingCount < 0) {
            throw new IllegalArgumentException("ratingCount cannot be negative");
        }
        this.ratingCount = ratingCount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void printProduct() {
        System.out.println("ID: " + this.id);
        System.out.println("Title: " + this.title);
        System.out.println("Description: " + this.description);
        System.out.println("Price: " + this.price);
        System.out.println("Category: " + this.category);
        System.out.println("Image: " + this.image);
        System.out.println("Rating Rate: " + this.ratingRate);
        System.out.println("Rating Count: " + this.ratingCount);
    }

    public String toString() {
        return "ID: " + this.id + "\n" +
                "Title: " + this.title + "\n" +
                "Description: " + this.description + "\n" +
                "Price: " + this.price + "\n" +
                "Category: " + this.category + "\n" +
                "Image: " + this.image + "\n" +
                "Rating Rate: " + this.ratingRate + "\n" +
                "Rating Count: " + this.ratingCount + "\n";
    }
}
