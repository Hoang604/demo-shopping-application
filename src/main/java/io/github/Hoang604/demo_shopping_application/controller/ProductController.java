package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.dto.UpdateProductDTO;
import io.github.Hoang604.demo_shopping_application.model.Category;
import io.github.Hoang604.demo_shopping_application.model.Product;
import io.github.Hoang604.demo_shopping_application.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        // Thêm danh sách sản phẩm vào model (model chứa các thuộc tính mà bạn muốn render trong view)
        model.addAttribute("products", products);
        return "product/products";
    }

    @PostMapping
    public String createProduct(@RequestBody Product product, Model model) {
        Product newProduct = productService.createProduct(product);
        model.addAttribute("product", newProduct);
        return "product/product";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable int id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "error/404"; // Trả về trang lỗi nếu sản phẩm không tồn tại
        }
        model.addAttribute("product", product);
        return "product/product";
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable int id, @RequestBody UpdateProductDTO productDTO, Model model) {
        Product updatedProduct = productService.updateProduct(id, productDTO);
        if (updatedProduct == null) {
            return "error/product-not-exist";
        }
        model.addAttribute("product", updatedProduct);
        return "product/product";
    }

    @DeleteMapping("/{id}")
    public String deleteProductById(@PathVariable int id, Model model) {
        if (productService.getProductById(id) == null) {
            return "error/404";
        }
        productService.deleteProductById(id);
        model.addAttribute("message", "Product deleted successfully");
        return "redirect:/product/products"; // Redirect to the products list page
    }

    @GetMapping("/search")
    public String searchProducts(
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
            @RequestParam(required = false) Double maxRatingCount,
            Model model) {

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

        model.addAttribute("products", products);
        return "product/products";
    }
}