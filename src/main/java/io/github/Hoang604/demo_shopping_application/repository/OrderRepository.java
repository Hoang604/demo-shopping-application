package io.github.Hoang604.demo_shopping_application.repository;

import io.github.Hoang604.demo_shopping_application.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    
}