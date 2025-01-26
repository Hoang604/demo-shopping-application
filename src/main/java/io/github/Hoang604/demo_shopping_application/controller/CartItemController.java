package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.dto.CreateCartItemDTO;
import io.github.Hoang604.demo_shopping_application.model.CartItem;
import io.github.Hoang604.demo_shopping_application.service.CartItemService;
import io.github.Hoang604.demo_shopping_application.utils.SecurityUtils;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Integer userId = SecurityUtils.getCurrentUserId(authentication);
        if (SecurityUtils.isAdmin(authentication)) {
            cartItems = cartItemService.getAllCartItems();
        }
        else {
            cartItems = cartItemService.getCartItemByUserId(userId);
        }
        model.addAttribute("userId", userId);
        model.addAttribute("cartItems", cartItems);
        return "cart/cart";
    }

    // add new cart item to cart
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<?> createCartItem(@RequestBody CreateCartItemDTO cartItemDTO,
                Authentication authentication) {
        try {
            // Kiểm tra userId có khớp với current user không (trừ admin)
            if (!SecurityUtils.isAdmin(authentication) && !cartItemDTO.userId()
                    .equals(SecurityUtils.getCurrentUserId(authentication))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            CartItem newCartItem = cartItemService.newCartItem(cartItemDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Item added to cart successfully");
            response.put("cartItem", newCartItem);
            return ResponseEntity.created(URI.create("/users/" +
                    SecurityUtils.getCurrentUserId(authentication) + "/cart/"
                    + newCartItem.getId())).body(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    // get cart item by id from cart (use for display detail of cart item)
    @GetMapping("/{id}")
    public String getCartItemById(@PathVariable int id, Model model, Authentication authentication) {
        CartItem cartItem = cartItemService.getCartItemById(id);
        if (cartItem.getUser().getId() != SecurityUtils.getCurrentUserId(authentication)
                && !SecurityUtils.isAdmin(authentication)) {
            return "error/403";
        }
        model.addAttribute("cartItem", cartItem);
        model.addAttribute("userId", SecurityUtils.getCurrentUserId(authentication));
        return "cart/cart-item";
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItemById( @PathVariable int id, Authentication authentication) {
        
        CartItem cartItem = cartItemService.getCartItemById(id);
        
        if (cartItem == null) {
            return ResponseEntity.notFound().build();
        }
        
        if (cartItem.getUser().getId() != SecurityUtils.getCurrentUserId(authentication)
                && !SecurityUtils.isAdmin(authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        cartItemService.deleteCartItemById(id);
        return ResponseEntity.noContent().build();
    }
}
