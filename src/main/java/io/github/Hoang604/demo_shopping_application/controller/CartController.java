package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.model.Cart;
import io.github.Hoang604.demo_shopping_application.model.CartItem;
import io.github.Hoang604.demo_shopping_application.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


public class CartController {
    
}
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public Cart addItemToCart(@RequestBody Item item) {
        return cartService.addItemToCart(item);
    }

    @PutMapping("/update")
    public Cart updateItemInCart(@RequestBody Item item) {
        return cartService.updateItemInCart(item);
    }

    @DeleteMapping("/remove/{itemId}")
    public Cart removeItemFromCart(@PathVariable Long itemId) {
        return cartService.removeItemFromCart(itemId);
    }
}