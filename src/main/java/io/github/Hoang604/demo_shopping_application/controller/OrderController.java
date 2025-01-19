package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.service.OrderService;
import io.github.Hoang604.demo_shopping_application.model.Order;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/users/{userId}/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order/orders";
    }

    @PostMapping
    public String saveOrder(@RequestBody Order order, Model model) {
        orderService.saveOrder(order);
        model.addAttribute("order", order);
        model.addAttribute("message", "Order created successfully");
        return "order/order-confirmation";
    }

    @GetMapping("/{id}")
    public String getOrderById(@PathVariable int id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return "error/404";
        }
        model.addAttribute("order", order);
        return "order/order";
    }

    @DeleteMapping("/{id}")
    public String deleteOrderById(@PathVariable int id) {
        orderService.deleteOrderById(id);
        return "redirect:/users/{userId}/orders";
    }
}
