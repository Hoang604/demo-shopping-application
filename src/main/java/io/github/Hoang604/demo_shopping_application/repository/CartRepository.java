package io.github.Hoang604.demo_shopping_application.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import io.github.Hoang604.demo_shopping_application.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {}