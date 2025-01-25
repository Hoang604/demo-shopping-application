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
import org.springframework.http.ResponseEntity;

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
    @ResponseBody
    public ResponseEntity<?> createCartItem(@RequestBody CreateCartItemDTO cartItemDTO,
                                            Authentication authentication) {
        try {
            // Kiểm tra userId có khớp với current user không (trừ admin)
            if (!isAdmin(authentication) && !cartItemDTO.userId().equals(getCurrentUserId(authentication))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            CartItem newCartItem = cartItemService.newCartItem(cartItemDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Item added to cart successfully");
            response.put("cartItem", newCartItem);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
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
