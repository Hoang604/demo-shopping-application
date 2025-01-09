/**
 * Represents a shopping cart in the application.
 * This entity is mapped to the "carts" table in the database.
 * Each cart is associated with a single user and has a creation timestamp.
 * 
 * Annotations:
 * - @Entity: Specifies that the class is an entity and is mapped to a database table.
 * - @Table: Specifies the name of the database table to be used for mapping.
 * - @Id: Specifies the primary key of an entity.
 * - @GeneratedValue: Provides for the specification of generation strategies for the values of primary keys.
 * - @OneToOne: Defines a one-to-one relationship between two entities.
 * - @JoinColumn: Specifies a column for joining an entity association.
 * - @Column: Specifies the mapped column for a persistent property or field.
 * 
 * Fields:
 * - id: The unique identifier for the cart. It is auto-generated.
 * - user: The user associated with the cart.
 * - createdDate: The timestamp when the cart was created.
 * 
 * Constructors:
 * - Cart(): Default constructor that initializes the cart with the current timestamp and no user.
 * - Cart(User user): Constructor that initializes the cart with the specified user and the current timestamp.
 * 
 * Methods:
 * - getId(): Returns the unique identifier of the cart.
 * - setId(int id): Sets the unique identifier of the cart.
 * - getUser(): Returns the user associated with the cart.
 * - setUser(User user): Sets the user associated with the cart.
 * - getCreatedDate(): Returns the creation timestamp of the cart.
 * - setCreatedDate(Timestamp createdDate): Sets the creation timestamp of the cart.
 */
package io.github.Hoang604.demo_shopping_application.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Timestamp;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "created_at")
    private Timestamp createdDate;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

    public Cart() {
        this.user = null;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Cart(User user) {
        this.user = user;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public double getTotalAmount() {
        return cartItems.size();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
