package io.github.Hoang604.demo_shopping_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.Hoang604.demo_shopping_application.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
}