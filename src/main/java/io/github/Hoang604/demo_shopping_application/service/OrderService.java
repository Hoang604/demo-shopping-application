package io.github.Hoang604.demo_shopping_application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.Hoang604.demo_shopping_application.dto.CreateOrderDTO;
import io.github.Hoang604.demo_shopping_application.model.Order;
import io.github.Hoang604.demo_shopping_application.repository.OrderRepository;


@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, UserService userService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public Order saveOrder(CreateOrderDTO order) {
        Order newOrder = new Order();
        newOrder.setUser(userService.getUserById(order.userId()));
        newOrder.setProduct(productService.getProductById(order.productId()));
        newOrder.setQuantity(order.quantity());
        return orderRepository.save(newOrder);
    }

    public void saveAllOrder(List<Order> orders) {
        orderRepository.saveAll(orders);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void deleteOrderById(int id) {
        orderRepository.deleteById(id);
    }

    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

    public List<Order> getOrdersByUserId(int userId) {
        return orderRepository.findByUserId(userId);
    }
}
