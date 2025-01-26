package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.service.OrderService;
import io.github.Hoang604.demo_shopping_application.utils.SecurityUtils;
import io.github.Hoang604.demo_shopping_application.dto.CreateOrderDTO;
import io.github.Hoang604.demo_shopping_application.model.Order;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/users/{userId}/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String getAllOrders(@PathVariable Integer userId, Model model, Authentication authentication) {
        if (authentication == null) {
            return "error/401";
        }

        List<Order> orders;
        if (SecurityUtils.isAdmin(authentication)) {
            orders = orderService.getAllOrders();
        }
        else if (userId == SecurityUtils.getCurrentUserId(authentication)) {
            orders = orderService.getOrdersByUserId(userId);
        }
        else {
            return "error/403";
        }
        model.addAttribute("userId", userId);
        model.addAttribute("orders", orders);
        return "order/orders";
    }

    @ResponseBody
    @PostMapping("/")
    public ResponseEntity<?> saveOrder(Authentication authentication,
            @PathVariable Integer userId, @RequestBody CreateOrderDTO order) {

        if (userId != SecurityUtils.getCurrentUserId(authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(Map.of("error", "Access denied"));
        }

        try {
            Order newOrder = orderService.saveOrder(order);
            
            Map<String, Object> response = new HashMap<>();
            response.put("orderId", newOrder.getId());
            response.put("message", "Order created successfully");
            
            return ResponseEntity.created(URI.create("/users/" + userId + "/orders/"  + newOrder.getId()))
                                .body(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                                .body(Map.of("error", "Order processing failed: " + e.getMessage()));
        }
    }
    @GetMapping("/{id}")
    public String getOrderById(@PathVariable int id, Model model, Authentication authentication) {
        if (authentication == null) {
            return "error/401";
        }
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return "error/404";
        }

        if (!SecurityUtils.isAdmin(authentication) && SecurityUtils.getCurrentUserId(authentication) != order.getUser().getId()) {
            return "error/403";
        }

        model.addAttribute("order", order);
        model.addAttribute("userId", order.getUser().getId());
        return "order/order";
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteOrderById(
        @PathVariable int id, Authentication authentication) {
        
        try {
            if (orderService.getOrderById(id) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Order not found with id: " + id));
            }
            
            // Kiểm tra quyền sở hữu
            if (orderService.getOrderById(id).getUser().getId() != SecurityUtils.getCurrentUserId(authentication)
                && !SecurityUtils.isAdmin(authentication)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "You are not authorized to delete this order"));
            }

            orderService.deleteOrderById(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", "Failed to delete order: " + e.getMessage()));
        }
    }
}
