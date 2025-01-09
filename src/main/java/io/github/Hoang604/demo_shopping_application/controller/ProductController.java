package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.dto.UpdateProductDTO;
import io.github.Hoang604.demo_shopping_application.model.Category;
import io.github.Hoang604.demo_shopping_application.model.Product;
import io.github.Hoang604.demo_shopping_application.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.github.Hoang604.demo_shopping_application.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.createProduct(product);
        return ResponseEntity.ok(newProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody UpdateProductDTO productDTO) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }

        // Kiểm tra xem category_id có tồn tại không
        if (productDTO.getCategory() != null) {
            Category category = categoryService.getCategoryById(productDTO.getCategory().getId());
            if (category == null) {
                return ResponseEntity.badRequest().body(null);
            }
            existingProduct.setCategory(category);
        }

        // Chỉ cập nhật các trường không null từ DTO
        if (productDTO.getTitle() != null) {
            existingProduct.setTitle(productDTO.getTitle());
        }
        if (productDTO.getPrice() != null) {
            existingProduct.setPrice(productDTO.getPrice());
        }
        if (productDTO.getCategory() != null) {
            existingProduct.setCategory(productDTO.getCategory());
        }
        if (productDTO.getRatingRate() != null) {
            existingProduct.setRatingRate(productDTO.getRatingRate());
        }
        if (productDTO.getRatingCount() != null) {
            existingProduct.setRatingCount(productDTO.getRatingCount());
        }

        Product updatedProduct = productService.updateProduct(existingProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable int id) {
        if (productService.getProductById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Integer ratingRate,
            @RequestParam(required = false) Integer ratingCount,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minRatingRate,
            @RequestParam(required = false) Double maxRatingRate,
            @RequestParam(required = false) Double minRatingCount,
            @RequestParam(required = false) Double maxRatingCount) {

        List<Product> products;

        if (title != null) {
            products = productService.findByTitle(title);
        } else if (price != null) {
            products = productService.findByPrice(price);
        } else if (category != null && minPrice != null && maxPrice != null) {
            products = productService.findByCategoryAndPriceBetween(category, minPrice, maxPrice);
        } else if (category != null && minRatingRate != null && maxRatingRate != null) {
            products = productService.findByCategoryAndRatingRateBetween(category, minRatingRate, maxRatingRate);
        } else if (category != null && minRatingCount != null && maxRatingCount != null) {
            products = productService.findByCategoryAndRatingCountBetween(category, minRatingCount, maxRatingCount);
        } else if (minPrice != null && maxPrice != null) {
            products = productService.findByPriceBetween(minPrice, maxPrice);
        } else if (minRatingRate != null && maxRatingRate != null) {
            products = productService.findByRatingRateBetween(minRatingRate, maxRatingRate);
        } else if (minRatingCount != null && maxRatingCount != null) {
            products = productService.findByRatingCountBetween(minRatingCount, maxRatingCount);
        } else {
            products = productService.getAllProducts();
        }

        return ResponseEntity.ok(products);
    }
}