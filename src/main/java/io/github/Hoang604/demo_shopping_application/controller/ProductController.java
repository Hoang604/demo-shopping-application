package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.dto.UpdateProductDTO;
import io.github.Hoang604.demo_shopping_application.model.Category;
import io.github.Hoang604.demo_shopping_application.model.Product;
import io.github.Hoang604.demo_shopping_application.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;
import io.github.Hoang604.demo_shopping_application.service.CategoryService;
import org.springframework.http.HttpStatus;

import java.util.List;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products"; // Trả về tên của file HTML
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.createProduct(product);
        return ResponseEntity.ok(newProduct);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public String getProductById(@PathVariable int id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "error/404"; // Trả về trang lỗi nếu sản phẩm không tồn tại
        }
        model.addAttribute("product", product);
        return "product"; // Trả về tên của file HTML
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody UpdateProductDTO productDTO) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }

        // Kiểm tra xem category_id có tồn tại không
        if (productDTO.category() != null) {
            Category category = categoryService.getCategoryById(productDTO.category().getId());
            if (category == null) {
                return ResponseEntity.badRequest().body(null);
            }
            existingProduct.setCategory(category);
        }

        // Chỉ cập nhật các trường không null từ DTO
        if (productDTO.title() != null) {
            existingProduct.setTitle(productDTO.title());
        }
        if (productDTO.price() != null) {
            existingProduct.setPrice(productDTO.price());
        }
        if (productDTO.category() != null) {
            existingProduct.setCategory(productDTO.category());
        }
        if (productDTO.ratingRate() != null) {
            existingProduct.setRatingRate(productDTO.ratingRate());
        }
        if (productDTO.ratingCount() != null) {
            existingProduct.setRatingCount(productDTO.ratingCount());
        }

        Product updatedProduct = productService.updateProduct(existingProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable int id) {
        if (productService.getProductById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.OK)
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