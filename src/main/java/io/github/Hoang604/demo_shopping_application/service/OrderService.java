package io.github.Hoang604.demo_shopping_application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import io.github.Hoang604.demo_shopping_application.model.Order;
import io.github.Hoang604.demo_shopping_application.repository.OrderRepository;

public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

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

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }
}
