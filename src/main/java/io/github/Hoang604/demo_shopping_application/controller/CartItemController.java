package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.dto.CreateCartItemDTO;
import io.github.Hoang604.demo_shopping_application.model.CartItem;
import io.github.Hoang604.demo_shopping_application.model.MyUserDetails;
import io.github.Hoang604.demo_shopping_application.service.CartItemService;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;

import org.springframework.http.HttpStatus;
import java.util.List;

@Controller
@RequestMapping("/users/{userId}/cart")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    // get all cart items
    @GetMapping("/")
    public String getCart(Model model, Authentication authentication) {
        List<CartItem> cartItems;
        if (isAdmin(authentication)) {
            cartItems = cartItemService.getAllCartItems();
        }
        else {
            cartItems = cartItemService.getCartItemByUserId(getCurrentUserId(authentication));
        }
        model.addAttribute("userId", getCurrentUserId(authentication));
        model.addAttribute("cartItems", cartItems);
        return "cart/cart";
    }

    // add new cart item to cart
    @PostMapping("/")
    public String createCartItem(@RequestBody CreateCartItemDTO cartItem, Model model) {
        CartItem newCartItem = cartItemService.newCartItem(cartItem);
        model.addAttribute("cartItem", newCartItem);
        model.addAttribute("message", "Cart item created successfully");
        return "cart/cart-item-created";
    }

    // get cart item by id from cart (use for display detail of cart item)
    @GetMapping("/{id}")
    public String getCartItemById(@PathVariable int id, Model model, Authentication authentication) {
        CartItem cartItem = cartItemService.getCartItemById(id);
        model.addAttribute("cartItem", cartItem);
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        model.addAttribute("userId", userDetails.getId());
        return "cart/cart-item";
    }

    @PutMapping("/{id}")
    public String updateCartItem(@PathVariable int id, @RequestBody CartItem cartItem, Model model) {
        if (cartItemService.getCartItemById(id) == null) {
            return "error/404";
        }
        cartItem.setId(id);
        cartItemService.updateCartItem(cartItem);
        model.addAttribute("message", "Cart item updated successfully");
        return "redirect:/users/{userId}/cart/" + id;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public String deleteCartItemById(@PathVariable int id, Model model) {
        if (cartItemService.getCartItemById(id) == null) {
            return "error/404";
        }
        cartItemService.deleteCartItemById(id);
        return "redirect:/users/{userId}/cart";
    }
    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
    // Lấy ID của User hiện tại
    private Integer getCurrentUserId(Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();
        return userDetails.getId();
    }
}
