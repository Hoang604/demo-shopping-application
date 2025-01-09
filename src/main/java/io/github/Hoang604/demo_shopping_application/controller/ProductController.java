package io.github.Hoang604.demo_shopping_application.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.github.Hoang604.demo_shopping_application.service.ProductService;
import io.github.Hoang604.demo_shopping_application.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct != null) {
            product.setId(id);
            productService.saveProduct(product);
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PostMapping("/add-all")
    public ResponseEntity<List<Product>> createProducts(@RequestBody List<Product> products) {
        List<Product> savedProducts = productService.saveAllProducts(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProducts);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Product>> getProductsByTitle(@PathVariable String title) {
        List<Product> products = productService.getProductsByTitle(title);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<List<Product>> getProductsByPrice(@PathVariable double price) {
        List<Product> products = productService.getProductsByPrice(price);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/priceRange/{minPrice}/{maxPrice}")
    public ResponseEntity<List<Product>> getProductsByPriceRange(@PathVariable double minPrice, @PathVariable double maxPrice) {
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/ratingRate/{ratingRate}")
    public ResponseEntity<List<Product>> getProductsByRatingRate(@PathVariable int ratingRate) {
        List<Product> products = productService.getProductsByRatingRate(ratingRate);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/ratingCount/{ratingCount}")
    public ResponseEntity<List<Product>> getProductsByRatingCount(@PathVariable int ratingCount) {
        List<Product> products = productService.getProductsByRatingCount(ratingCount);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/ratingCountRange/{minRatingCount}/{maxRatingCount}")
    public ResponseEntity<List<Product>> getProductsByRatingCountRange(@PathVariable int minRatingCount, @PathVariable int maxRatingCount) {
        List<Product> products = productService.getProductsByRatingCountRange(minRatingCount, maxRatingCount);
        return ResponseEntity.ok(products);
    }
}
