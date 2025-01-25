package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.service.OrderService;
import io.github.Hoang604.demo_shopping_application.dto.CreateOrderDTO;
import io.github.Hoang604.demo_shopping_application.model.MyUserDetails;
import io.github.Hoang604.demo_shopping_application.model.Order;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("/users/{userId}/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String getAllOrders(Model model, Authentication authentication) {
        if (authentication == null) {
            return "error/401";
        }

        List<Order> orders;
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        int userId = userDetails.getId();
        if (isAdmin(authentication)) {
            orders = orderService.getAllOrders();
        }
        else {
            orders = orderService.getOrdersByUserId(userId);
        }
        model.addAttribute("userId", userId);
        model.addAttribute("orders", orders);
        return "order/orders";
    }

    @PostMapping("/")
    public String saveOrder(@RequestBody CreateOrderDTO order, Model model) {
        Order newOrder = orderService.saveOrder(order);
        model.addAttribute("order", newOrder);
        model.addAttribute("message", "Order created successfully");
        return "order/order-confirmation";
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

        if (!isAdmin(authentication) && !isOrderOwner(order, authentication)) {
            return "error/403";
        }

        model.addAttribute("order", order);
        model.addAttribute("userId", order.getUser().getId());
        return "order/order";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteOrderById(@PathVariable int id) {
        orderService.deleteOrderById(id);
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

    // Kiểm tra User có phải chủ đơn hàng không
    private boolean isOrderOwner(Order order, Authentication authentication) {
        return order.getUser().getId().equals(getCurrentUserId(authentication));
    }
}
