package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.service.CategoryService;
import io.github.Hoang604.demo_shopping_application.dto.UpdateProductDTO;
import io.github.Hoang604.demo_shopping_application.dto.createProductDTO;
import io.github.Hoang604.demo_shopping_application.model.Category;
import io.github.Hoang604.demo_shopping_application.model.Product;
import io.github.Hoang604.demo_shopping_application.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }


    @GetMapping("/")
    public String getAllProducts(@RequestParam(required = false) Integer categoryId, Model model) {
        System.out.println("categoryId: " + categoryId);
        if (categoryId != null) {
            System.out.println("categoryId: " + categoryId);
            Category category = categoryService.getCategoryById(categoryId);
            model.addAttribute("category", category);
            if (category == null) {
                return "error/404";
            }
            List<Product> products = productService.findByCategory(category);
            model.addAttribute("products", products);
        } else {
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products);
        }
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "product/products";
    }


    @PostMapping(value = "/", consumes = "application/json")
    public String createProduct(@RequestBody createProductDTO product, Model model) {
        Product newProduct = new Product();
        newProduct.setTitle(product.title());
        newProduct.setPrice(product.price());
        newProduct.setDescription(product.description());
        Category category = categoryService.getCategoryById(product.categoryId());
        newProduct.setCategory(category);
        newProduct.setRatingRate(0.0);
        newProduct.setRatingCount(0);
        newProduct.setImage(product.image());
        System.out.println("newProduct: " + newProduct);
        productService.createProduct(newProduct);
        model.addAttribute("product", newProduct);
        model.addAttribute("message", "Product created successfully");
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
        model.addAttribute("message", "Product updated successfully");
        return "product/product";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProductById(@PathVariable int id, Model model) {
        if (productService.getProductById(id) == null) {
            return "error/404";
        }
        productService.deleteProductById(id);
        model.addAttribute("message", "Product deleted successfully");
        return "redirect:/product/products"; // Redirect to the products list page
    }

    @GetMapping("/new")
    public String showCreateProductForm(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new Product());
        return "product/create-product";
    }

    @GetMapping("/search")
    public String redirectSearch() {
        return "redirect:/products/search/";
    }

    @GetMapping("/search/")
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
        products = new ArrayList<Product>();

        if (title != null) {
            products = productService.findByTitle(title);
        }
        if (price != null) {
            products.addAll(productService.findByPrice(price));
        }
        if (category != null && minPrice != null && maxPrice != null) {
            products.addAll(productService.findByCategoryAndPriceBetween(category, minPrice, maxPrice));
        }
        if (category != null && minRatingRate != null && maxRatingRate != null) {
            products.addAll(productService.findByCategoryAndRatingRateBetween(category, minRatingRate, maxRatingRate));
        }
        if (category != null && minRatingCount != null && maxRatingCount != null) {
            products.addAll(productService.findByCategoryAndRatingCountBetween(category, minRatingCount, maxRatingCount));
        }
        if (minPrice != null && maxPrice != null) {
            products.addAll(productService.findByPriceBetween(minPrice, maxPrice));
        }
        if (minRatingRate != null && maxRatingRate != null) {
            products.addAll(productService.findByRatingRateBetween(minRatingRate, maxRatingRate));
        } 
        if (minRatingCount != null && maxRatingCount != null) {
            products.addAll(productService.findByRatingCountBetween(minRatingCount, maxRatingCount));
        } 

        model.addAttribute("products", products);
        return "product/products";
    }
}