package io.github.Hoang604.demo_shopping_application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.Hoang604.demo_shopping_application.model.Order;
import io.github.Hoang604.demo_shopping_application.repository.OrderRepository;


@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
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
