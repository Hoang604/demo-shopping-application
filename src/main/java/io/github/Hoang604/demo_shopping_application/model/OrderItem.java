package io.github.Hoang604.demo_shopping_application.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class OrderItem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private double price;

    public OrderItem() {
        this.order = null;
        this.product = null;
        this.quantity = 0;
        this.price = 0;
    }

    public OrderItem(Order order, Product product, int quantity, double price) {
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity cannot be negative");
        }
        if (price < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }

        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return this.price * this.quantity;
    }
}
