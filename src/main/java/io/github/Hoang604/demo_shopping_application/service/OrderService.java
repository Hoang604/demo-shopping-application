package io.github.Hoang604.demo_shopping_application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.Hoang604.demo_shopping_application.model.Order;
import io.github.Hoang604.demo_shopping_application.model.OrderItem;
import io.github.Hoang604.demo_shopping_application.repository.OrderRepository;
import io.github.Hoang604.demo_shopping_application.repository.OrderItemRepository;


@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository; 

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemService;
    }

    public void saveOrder(OrderItem order) {
        orderItemRepository.save(order);
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
}
