/*
 * This file is the controller for the Product model. It contains the endpoints for the Product model.
 * The ProductController class is annotated with @RestController to indicate that it is a controller class.
 * The @RequestMapping annotation is used to map the endpoints to the controller.
 * The @Autowired annotation is used to inject the ProductService dependency into the controller.
 * The getAllProducts method is a GET endpoint that returns a list of all products.
 * The getProductById method is a GET endpoint that returns a product by its id.
 * The getProductsByCategory method is a GET endpoint that returns a list of products by category.
 * The getProductsByTitle method is a GET endpoint that returns a list of products by title.
 * The getProductsByPrice method is a GET endpoint that returns a list of products by price.
 * The getProductsByRatingRate method is a GET endpoint that returns a list of products by rating rate.
 * The getProductsByRatingCount method is a GET endpoint that returns a list of products by rating count.
 * The createProduct method is a POST endpoint that creates a new product.
 */

package io.github.Hoang604.demo_shopping_application.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import io.github.Hoang604.demo_shopping_application.service.ProductService;
import io.github.Hoang604.demo_shopping_application.model.Product;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProductById(id);
    }

    @PatchMapping("/{id}")
    public void updateProduct(@PathVariable int id, @RequestBody Product product) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct != null) {
            product.updateId(id);
            productService.saveProduct(product);
        }
    }

    @PostMapping("/add")
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PostMapping("/add-all")
    public List<Product> createProducts(@RequestBody List<Product> products) {
        return productService.saveAllProducts(products);
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }

    @GetMapping("/title/{title}")
    public List<Product> getProductsByTitle(@PathVariable String title) {
        return productService.getProductsByTitle(title);
    }

    @GetMapping("/price/{price}")
    public List<Product> getProductsByPrice(@PathVariable double price) {
        return productService.getProductsByPrice(price);
    }

    @GetMapping("/priceRange/{minPrice}/{maxPrice}")
    public List<Product> getProductsByPriceRange(@PathVariable double minPrice, @PathVariable double maxPrice) {
        return productService.getProductsByPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/ratingRate/{ratingRate}")
    public List<Product> getProductsByRatingRate(@PathVariable int ratingRate) {
        return productService.getProductsByRatingRate(ratingRate);
    }


    @GetMapping("/ratingRateRange/{minRatingRate}/{maxRatingRate}")
    public List<Product> getProductsByRatingRateRange(@PathVariable int minRatingRate, @PathVariable int maxRatingRate) {
        return productService.getProductsByRatingRateRange(minRatingRate, maxRatingRate);
    }

    @GetMapping("/ratingCount/{ratingCount}")
    public List<Product> getProductsByRatingCount(@PathVariable int ratingCount) {
        return productService.getProductsByRatingCount(ratingCount);
    }

    @GetMapping("/ratingCountRange/{minRatingCount}/{maxRatingCount}")
    public List<Product> getProductsByRatingCountRange(@PathVariable int minRatingCount, @PathVariable int maxRatingCount) {
        return productService.getProductsByRatingCountRange(minRatingCount, maxRatingCount);
    }

    @GetMapping("/categoryPriceRange/{category}/{minPrice}/{maxPrice}")
    public List<Product> getProductsByCategoryAndPriceRange(@PathVariable String category,
            @PathVariable double minPrice, @PathVariable double maxPrice) {
        return productService.getProductsByCategoryAndPriceRange(category, minPrice, maxPrice);
    }
    
    @GetMapping("/categoryRatingRateRange/{category}/{minRatingRate}/{maxRatingRate}")
    public List<Product> getProductsByCategoryAndRatingRateRange(@PathVariable String category,
            @PathVariable int minRatingRate, @PathVariable int maxRatingRate) {
        return productService.getProductsByCategoryAndRatingRateRange(category, minRatingRate, maxRatingRate);
    }
}
