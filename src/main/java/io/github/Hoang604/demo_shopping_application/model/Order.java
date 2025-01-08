package io.github.Hoang604.demo_shopping_application.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Order {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(nullable = true, name = "order_date")
    private java.sql.Timestamp orderDate;
    @Column(nullable = true, name = "status")
    private String status;

    public Order() {
        this.user = null;
        this.orderDate = null;
        this.status = "";
    }

    public Order(User user, java.sql.Timestamp orderDate, String status) {
        this.user = user;
        this.orderDate = orderDate;
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

    public String getStatus() {
        return this.status;
    }

    public void updateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        this.user = user;
    }

    public void updateOrderDate(java.sql.Timestamp orderDate) {
        if (orderDate == null) {
            throw new IllegalArgumentException("orderDate cannot be null");
        }
        this.orderDate = orderDate;
    }

    public void updateStatus(String status) {
        if (status == null) {
            throw new IllegalArgumentException("status cannot be null");
        }
        this.status = status;
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
