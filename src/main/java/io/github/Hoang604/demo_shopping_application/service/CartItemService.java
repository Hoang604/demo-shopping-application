package io.github.Hoang604.demo_shopping_application.service;

import io.github.Hoang604.demo_shopping_application.model.CartItem;
import io.github.Hoang604.demo_shopping_application.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    public CartItem getCartItemById(int id) {
        return cartItemRepository.findById(id).orElse(null);
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

    public List<CartItem> getCartItemByUserId(int userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public void deleteAllCartItems() {
        cartItemRepository.deleteAll();
    }
}
