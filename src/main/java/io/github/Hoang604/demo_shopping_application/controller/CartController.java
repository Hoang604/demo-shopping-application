package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.model.Cart;
import io.github.Hoang604.demo_shopping_application.model.CartItem;
import io.github.Hoang604.demo_shopping_application.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public Cart getCartById(@RequestBody Integer cartId) {
        if (cartService.isExist(cartId)) {
            return cartService.newCart(new Cart());
        }
        return cartService.getCartById(cartId);
    }

    @PostMapping("add")
    public CartItem addItemToCart(@RequestBody CartItem item) {
        return cartService.newCartItem(item);
    }

    @PutMapping("/update")
    public CartItem updateItemInCart(@RequestBody CartItem item) {
        return cartService.updateCartItem(item);
    }

    @DeleteMapping("/remove/{itemId}")
    public void removeItemFromCart(@PathVariable Integer itemId) {
        cartService.deleteCartItemById(itemId);
    }

    @DeleteMapping("/clear")
    public void clearCart() {
        cartService.deleteAllCartItems();
    }
}