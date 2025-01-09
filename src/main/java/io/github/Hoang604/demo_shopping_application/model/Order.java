package io.github.Hoang604.demo_shopping_application.model;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity()
@Table(name = "orders")
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @CreationTimestamp
    @Column(nullable = true, name = "order_date")
    private java.sql.Timestamp orderDate;
    @Column(name = "total_amount")
    private double totalAmount;
    @Column(nullable = true, name = "status")
    private String status;

    public Order() {
        this.user = null;
        this.orderDate = new java.sql.Timestamp(System.currentTimeMillis());
        this.totalAmount = 0;
        this.status = "";
    }

    public Order(User user, double totalAmount, String status) {
        if (totalAmount < 0) {
            throw new IllegalArgumentException("totalAmount cannot be negative");
        }
        this.user = user;
        this.orderDate = new java.sql.Timestamp(System.currentTimeMillis());
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public int getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }
    
    public java.sql.Timestamp getOrderDate() {
        return this.orderDate;
    }

    public double getTotalAmount() {
        return this.totalAmount;
    }

    public String getStatus() {
        return this.status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setOrderDate(java.sql.Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalAmount(double totalAmount) {
        if (totalAmount < 0) {
            throw new IllegalArgumentException("totalAmount cannot be negative");
        }
        this.totalAmount = totalAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.id + " " + this.user.getId() + " " + this.orderDate + " " + this.totalAmount + " " + this.status;
    }

    public void printOrder() {
        System.out.println("Order ID: " + this.id);
        System.out.println("User ID: " + this.user.getId());
        System.out.println("Order Date: " + this.orderDate);
        System.out.println("Status: " + this.status);
    }

    public void printOnlyOrder() {
        System.out.println(this.id + " " + this.user.getId() + " " + this.orderDate + " " + this.status);
    }

    public int compareTo(Order other) {
        int result = this.id - other.id;
        if (result == 0) {
            result = this.user.getId() - other.user.getId();
            if (result == 0) {
                result = this.orderDate.compareTo(other.orderDate);
                if (result == 0) {
                    result = this.status.compareTo(other.status);
                }
            }
        }
        return result;
    }

    // sort orders by id, userId, orderDate, status
    public static void sort(Order[] orders) {
        for (int i = 0; i < orders.length - 1; i++) {
            for (int j = i + 1; j < orders.length; j++) {
                if (orders[i].compareTo(orders[j]) > 0) {
                    Order temp = orders[i];
                    orders[i] = orders[j];
                    orders[j] = temp;
                }
            }
        }
    }
    
}
