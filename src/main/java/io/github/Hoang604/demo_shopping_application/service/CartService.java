package io.github.Hoang604.demo_shopping_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import io.github.Hoang604.demo_shopping_application.repository.CartRepository;
import io.github.Hoang604.demo_shopping_application.model.Cart;
import io.github.Hoang604.demo_shopping_application.model.CartItem;
import io.github.Hoang604.demo_shopping_application.repository.CartItemRepository;

public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

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

    public CartItem newCartItem(CartItem item) {
        return cartItemRepository.save(item);
    }

    public CartItem updateCartItem(CartItem item) {
        return cartItemRepository.save(item);
    }

    public void deleteCartItemById(int id) {
        cartItemRepository.deleteById(id);
    }

    public void deleteAllCartItems() {
        cartItemRepository.deleteAll();
    }
}