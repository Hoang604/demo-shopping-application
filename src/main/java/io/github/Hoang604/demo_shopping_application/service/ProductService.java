package io.github.Hoang604.demo_shopping_application.service;

import io.github.Hoang604.demo_shopping_application.dto.CreateProductDTO;
import io.github.Hoang604.demo_shopping_application.dto.UpdateProductDTO;
import io.github.Hoang604.demo_shopping_application.model.Category;
import io.github.Hoang604.demo_shopping_application.model.Product;
import io.github.Hoang604.demo_shopping_application.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Collections;
import java.util.stream.Collectors;

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
    
    public List<Product> getRandomProducts(int count) {
        return productRepository.findRandomProducts(count);
    }

    public List<Product> getProductsForPropose(Map<Integer, Long> categoryCount) {
        List<Product> result = new ArrayList<>();
        Random random = new Random();

        for (Map.Entry<Integer, Long> entry : categoryCount.entrySet()) {
            Category category = categoryService.getCategoryById(entry.getKey());
            Long count = entry.getValue();

            // Lấy tất cả sản phẩm trong danh mục
            List<Product> productsInCategory = productRepository.findByCategory(category);

            // Lấy ngẫu nhiên các sản phẩm theo tỷ lệ tương ứng với count
            Collections.shuffle(productsInCategory, random);
            List<Product> selectedProducts = productsInCategory.stream()
                    .limit(count < 5 ? 5 : count)
                    .collect(Collectors.toList());

            result.addAll(selectedProducts);
        }

        if (result.size() < 10) {
            List<Product> randomProducts = productRepository.findRandomProducts(10 - result.size());
            result.addAll(randomProducts);
        }
        return result;
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product updateProduct(int id, UpdateProductDTO productDTO) {
        Product existingProduct = getProductById(id);
        if (existingProduct == null) {
            return null;
        }

        if (productDTO.categoryId() != null) {
            Category category = categoryService.getCategoryById(productDTO.categoryId());
            existingProduct.setCategory(category);
        }
        if (productDTO.title() != null)
            existingProduct.setTitle(productDTO.title());
        if (productDTO.price() != null)
            existingProduct.setPrice(productDTO.price());
        if (productDTO.description() != null)
            existingProduct.setDescription(productDTO.description());
        if (productDTO.image() != null)
            existingProduct.setImage(productDTO.image());

        return productRepository.save(existingProduct);
    }

    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByCategory(Integer categoryId) {
        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            if (category != null) {
                List<Product> products = productRepository.findByCategory(category);
                Collections.shuffle(products);
                return products;
            }
        }
        List<Product> products = productRepository.findAll();
        Collections.shuffle(products);
        return products;
    }

    public List<Product> getAllProducts(Integer categoryId) {
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