package io.github.Hoang604.demo_shopping_application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import io.github.Hoang604.demo_shopping_application.repository.CartRepository;
import io.github.Hoang604.demo_shopping_application.model.Cart;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;    }

    public Cart newCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public boolean isExist(int id) {
        return cartRepository.existsById(id);
    }

    public Cart getCartById(int id) {
        return cartRepository.findById(id).orElse(null);
    }

    public void updateCart(Cart cart) {
        cartRepository.save(cart);
    }

    public void deleteAllCartItems(int cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.getCartItems().clear();
            cartRepository.save(cart);
        } else {
            throw new RuntimeException("Cart not found");
        }
    }
}