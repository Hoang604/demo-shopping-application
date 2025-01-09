package io.github.Hoang604.demo_shopping_application.service;

import io.github.Hoang604.demo_shopping_application.model.Category;
import io.github.Hoang604.demo_shopping_application.model.Product;
import io.github.Hoang604.demo_shopping_application.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> findByTitle(String title) {
        return productRepository.findByTitle(title);
    }

    public List<Product> findByPrice(double price) {
        return productRepository.findByPrice(price);
    }

    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> findByRatingRate(int ratingRate) {
        return productRepository.findByRatingRate(ratingRate);
    }

    public List<Product> findByRatingCount(int ratingCount) {
        return productRepository.findByRatingCount(ratingCount);
    }

    public List<Product> findByPriceBetween(double minPrice, double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Product> findByRatingRateBetween(double minRatingRate, double maxRatingRate) {
        return productRepository.findByRatingRateBetween(minRatingRate, maxRatingRate);
    }

    public List<Product> findByRatingCountBetween(double minRatingCount, double maxRatingCount) {
        return productRepository.findByRatingCountBetween(minRatingCount, maxRatingCount);
    }

    public List<Product> findByCategoryAndPriceBetween(Category category, double minPrice, double maxPrice) {
        return productRepository.findByCategoryAndPriceBetween(category, minPrice, maxPrice);
    }

    public List<Product> findByCategoryAndRatingRateBetween(Category category, double minRatingRate, double maxRatingRate) {
        return productRepository.findByCategoryAndRatingRateBetween(category, minRatingRate, maxRatingRate);
    }

    public List<Product> findByCategoryAndRatingCountBetween(Category category, double minRatingCount, double maxRatingCount) {
        return productRepository.findByCategoryAndRatingCountBetween(category, minRatingCount, maxRatingCount);
    }
}