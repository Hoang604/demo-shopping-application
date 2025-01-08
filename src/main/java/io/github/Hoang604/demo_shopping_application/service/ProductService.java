package io.github.Hoang604.demo_shopping_application.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import io.github.Hoang604.demo_shopping_application.model.Product;
import io.github.Hoang604.demo_shopping_application.repository.ProductRepository;

public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> saveAllProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> getProductsByTitle(String title) {
        return productRepository.findByTitle(title);
    }

    public List<Product> getProductsByPrice(double price) {
        return productRepository.findByPrice(price);
    }

    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Product> getProductsByRatingRate(int ratingRate) {
        return productRepository.findByRatingRate(ratingRate);
    }

    public List<Product> getProductsByRatingRateRange(int minRatingRate, int maxRatingRate) {
        return productRepository.findbyRatingRateBetween(minRatingRate, maxRatingRate);
    }

    public List<Product> getProductsByRatingCount(int ratingCount) {
        return productRepository.findByRatingCount(ratingCount);
    }

    public List<Product> getProductsByRatingCountRange(int minRatingCount, int maxRatingCount) {
        return productRepository.findByRatingCountBetween(minRatingCount, maxRatingCount);
    }

    public List<Product> getProductsByCategoryAndPriceRange(String category, double minPrice, double maxPrice) {
        return productRepository.findByCategoryAndPriceBetween(category, minPrice, maxPrice);
    }

    public List<Product> getProductsByCategoryAndRatingRateRange(String category, int minRatingRate, int maxRatingRate) {
        return productRepository.findByCategoryAndRatingRateBetween(category, minRatingRate, maxRatingRate);
    }

    public List<Product> getProductsByCategoryAndRatingRateRange(String category, double minRatingRate, double maxRatingRate) {
        return productRepository.findByCategoryAndRatingRateBetween(category, minRatingRate, maxRatingRate);
    }
}
