package io.github.Hoang604.demo_shopping_application.controller;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.Hoang604.demo_shopping_application.model.CartItem;
import io.github.Hoang604.demo_shopping_application.model.Order;
import io.github.Hoang604.demo_shopping_application.model.Product;
import io.github.Hoang604.demo_shopping_application.service.CartItemService;
import io.github.Hoang604.demo_shopping_application.service.ProductService;
import io.github.Hoang604.demo_shopping_application.service.OrderService;
import io.github.Hoang604.demo_shopping_application.utils.SecurityUtils;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.ui.Model;

@Controller
public class HomeController {
    private final ProductService productService;
    private final CartItemService cartItemService;
    private final OrderService orderService;

    public HomeController(ProductService productService, CartItemService cartItemService, OrderService orderService) {
        this.productService = productService;
        this.cartItemService = cartItemService;
        this.orderService = orderService;
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        if (authentication == null) {
            List<Product> products = productService.getRandomProducts(10);
            model.addAttribute("products", products);
            return "index";
        }

        System.err.println("Current user id: " + SecurityUtils.getCurrentUserId(authentication));

        model.addAttribute("userId", SecurityUtils.getCurrentUserId(authentication));
        List<CartItem> cartItems = cartItemService.getCartItemsByUserId(SecurityUtils.getCurrentUserId(authentication));
        Map<Integer, Long> categoryCount = cartItems.stream()
                .collect(Collectors.groupingBy(cartItem -> cartItem.getProduct().getCategory().getId(),
                        Collectors.counting()));
        List<Order> orders = orderService.getOrdersByUserId(SecurityUtils.getCurrentUserId(authentication));
        orders.forEach(order -> {
            categoryCount.put(order.getProduct().getCategory().getId(),
                    categoryCount.getOrDefault(order.getProduct().getCategory().getId(), 0L) + 1L);
        });
        List<Product> products = productService.getProductsForPropose(categoryCount);
        model.addAttribute("products", products);

        return "index";
    }
}
