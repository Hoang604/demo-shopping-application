package io.github.Hoang604.demo_shopping_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.github.Hoang604.demo_shopping_application.model.Product;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByTitle(String title);
    
    List<Product> findByPrice(double price);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findByRatingRate(int ratingRate);

    // List<Product> findbyRatingRateBetween(double minRatingRate, double maxRatingRate);

    List<Product> findByRatingCount(int ratingCount);

    List<Product> findByRatingCountBetween(double minRatingCount, double maxRatingCount);
}