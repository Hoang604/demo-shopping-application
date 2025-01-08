package io.github.Hoang604.demo_shopping_application.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Timestamp;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(name = "created_at")
    private Timestamp createdDate;


    public Cart() {
        this.user = null;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Cart(User user) {
        this.user = user;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }
}
