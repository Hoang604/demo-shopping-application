package io.github.Hoang604.demo_shopping_application.service;

import io.github.Hoang604.demo_shopping_application.dto.CreateCartItemDTO;
import io.github.Hoang604.demo_shopping_application.model.CartItem;
import io.github.Hoang604.demo_shopping_application.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final UserService userService;

    public CartItemService(CartItemRepository cartItemRepository, UserService userService, ProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    public List<CartItem> getCartItemsByUserId(int userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public CartItem getCartItemById(int id) {
        return cartItemRepository.findById(id).orElse(null);
    }

    public CartItem newCartItem(CreateCartItemDTO item) {
        CartItem newItem = new CartItem();
        newItem.setUser(userService.getUserById(item.userId()));
        newItem.setProduct(productService.getProductById(item.productId()));
        newItem.setQuantity(item.quantity());
        return cartItemRepository.save(newItem);
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
