package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.model.Cart;
import io.github.Hoang604.demo_shopping_application.service.CartService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Controller for handling cart-related operations.
 * Provides endpoints for retrieving, adding, updating, and removing items in the cart.
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    /**
     * Service for managing cart operations.
     */
    private final CartService cartService;

    /**
     * Constructs a new CartController with the specified CartService.
     *
     * @param cartService the CartService to be injected
     */

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public Cart getCartById(@RequestBody Integer cartId) {
        if (cartService.isExist(cartId)) {
            return cartService.newCart(new Cart());
        }
        return cartService.getCartById(cartId);
    }

    @DeleteMapping("/{id}/items")
    public ResponseEntity<Void> deleteAllCartItems(@PathVariable int id) {
        try {
            cartService.deleteAllCartItems(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}