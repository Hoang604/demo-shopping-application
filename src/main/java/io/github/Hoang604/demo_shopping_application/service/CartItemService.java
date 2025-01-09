package io.github.Hoang604.demo_shopping_application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.Hoang604.demo_shopping_application.model.CartItem;
import io.github.Hoang604.demo_shopping_application.repository.CartItemRepository;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem newCartItem(CartItem item) {
        return cartItemRepository.save(item);
    }

    public CartItem getCartItemById(int id) {
        return cartItemRepository.findById(id).orElse(null);
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

    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }
}
