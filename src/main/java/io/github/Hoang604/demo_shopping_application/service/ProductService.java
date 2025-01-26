package io.github.Hoang604.demo_shopping_application.service;

import io.github.Hoang604.demo_shopping_application.dto.CreateProductDTO;
import io.github.Hoang604.demo_shopping_application.dto.UpdateProductDTO;
import io.github.Hoang604.demo_shopping_application.model.Category;
import io.github.Hoang604.demo_shopping_application.model.Product;
import io.github.Hoang604.demo_shopping_application.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public Product createProduct(CreateProductDTO productDTO, Category category) {
        Product product = new Product();
        product.setTitle(productDTO.title());
        product.setPrice(productDTO.price());
        product.setDescription(productDTO.description());
        product.setCategory(category);
        product.setImage(productDTO.image());
        product.setRatingRate(0.0);
        product.setRatingCount(0);
        
        return productRepository.save(product);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product updateProduct(int id, UpdateProductDTO productDTO) {
        Product existingProduct = getProductById(id);
        if (existingProduct == null) {
            return null;
        }

        if (productDTO.category() != null) {
            Category category = categoryService.getCategoryById(productDTO.category().getId());
            if (category == null) {
                category = categoryService.createCategory(productDTO.category());
            }
            existingProduct.setCategory(category);
        }

        existingProduct.setTitle(productDTO.title());
        existingProduct.setPrice(productDTO.price());
        existingProduct.setRatingRate(productDTO.ratingRate());
        existingProduct.setRatingCount(productDTO.ratingCount());

        return productRepository.save(existingProduct);
    }

    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByCategory(Integer categoryId) {
        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            if (category != null) {
                return productRepository.findByCategory(category);
            }
        }
        return productRepository.findAll();
    }
    public List<Product> findByTitle(String title) {
        return productRepository.findByTitleContainingIgnoreCase(title);
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