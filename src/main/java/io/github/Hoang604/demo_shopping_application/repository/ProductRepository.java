package io.github.Hoang604.demo_shopping_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.Hoang604.demo_shopping_application.model.Category;
import io.github.Hoang604.demo_shopping_application.model.Product;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByTitle(String title);
    
    List<Product> findByPrice(double price);

    List<Product> findByCategory(Category category);

    List<Product> findByRatingRate(int ratingRate);

    List<Product> findByRatingCount(int ratingCount);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findByRatingRateBetween(double minRatingRate, double maxRatingRate);

    List<Product> findByRatingCountBetween(double minRatingCount, double maxRatingCount);

    List<Product> findByCategoryAndPriceBetween(Category category, double minPrice, double maxPrice);

    List<Product> findByCategoryAndRatingRateBetween(Category category,
            double minRatingRate, double maxRatingRate);
             
    List<Product> findByCategoryAndRatingCountBetween(Category category,
            double minRatingCount, double maxRatingCount);
}