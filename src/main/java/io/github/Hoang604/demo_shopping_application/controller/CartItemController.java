package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.model.CartItem;
import io.github.Hoang604.demo_shopping_application.service.CartItemService;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/cart/")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    // get all cart items
    @GetMapping
    public String getCart(Model model) {
        List<CartItem> cartItems = cartItemService.getAllCartItems();
        model.addAttribute("cartItems", cartItems);
        return "cart";
    }

    // add new cart item to cart
    @PostMapping
    public String createCartItem(@RequestBody CartItem cartItem, Model model) {
        CartItem newCartItem = cartItemService.newCartItem(cartItem);
        model.addAttribute("cartItem", newCartItem);
        model.addAttribute("message", "Cart item created successfully");
        return "cart-item-created";
    }

    // get cart item by id from cart (use for display detail of cart item)
    @GetMapping("{id}")
    public String getCartItemById(@PathVariable int id, Model model) {
        CartItem cartItem = cartItemService.getCartItemById(id);
        model.addAttribute("cartItem", cartItem);
        return "cart-item";
    }

    @PutMapping("{id}")
    public String updateCartItem(@PathVariable int id, @RequestBody CartItem cartItem, Model model) {
        if (cartItemService.getCartItemById(id) == null) {
            return "error/404";
        }
        cartItem.setId(id);
        cartItemService.updateCartItem(cartItem);
        model.addAttribute("message", "Cart item updated successfully");
        return "redirect:/users/{userId}/cart/" + id;
    }

    @DeleteMapping("{id}")
    public String deleteCartItemById(@PathVariable int id, Model model) {
        if (cartItemService.getCartItemById(id) == null) {
            return "error/404";
        }
        cartItemService.deleteCartItemById(id);
        return "redirect:/users/{userId}/cart";
    }
}
