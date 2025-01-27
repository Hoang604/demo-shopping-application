package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.service.CategoryService;
import io.github.Hoang604.demo_shopping_application.dto.UpdateProductDTO;
import io.github.Hoang604.demo_shopping_application.dto.CreateProductDTO;
import io.github.Hoang604.demo_shopping_application.model.Category;
import io.github.Hoang604.demo_shopping_application.model.Product;
import io.github.Hoang604.demo_shopping_application.service.ProductService;
import io.github.Hoang604.demo_shopping_application.utils.SecurityUtils;
import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public String getAllProducts(@RequestParam(required = false) Integer categoryId, Model model, Authentication authentication) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        model.addAttribute("products", products);
        if (categoryId != null){
            Category category = categoryService.getCategoryById(categoryId);
            model.addAttribute("category", category);
        }
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        Integer userId = SecurityUtils.getCurrentUserId(authentication);
        model.addAttribute("userId", userId);
        return "product/products";
    }

    @ResponseBody
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(
        @Valid @RequestBody CreateProductDTO productDTO,
            BindingResult bindingResult, Authentication authentication) {

        // authentication check
        if (!SecurityUtils.isAdmin(authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("error", "Access denied"));
        }
        
        // validation errors
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(
                    FieldError::getField,
                    FieldError::getDefaultMessage,
                    (existing, replacement) -> existing + ", " + replacement
                ));
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            // Kiểm tra category
            Category category = categoryService.getCategoryById(productDTO.categoryId());
            if (category == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Category not found"));
            }
            // Tạo product
            Product newProduct = productService.createProduct(productDTO, category);

            return ResponseEntity.created(URI.create("/products/" + newProduct.getId()))
                .body(newProduct);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", "Failed to create product: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable int id, Model model, Authentication authentication) {
        if (authentication == null) {
            return "error/401"; // Trả về trang lỗi nếu chưa đăng nhập
        }
        Product product = productService.getProductById(id);
        if (product == null) {
            return "error/404"; // Trả về trang lỗi nếu sản phẩm không tồn tại
        }
        model.addAttribute("product", product);
        model.addAttribute("userId", SecurityUtils.getCurrentUserId(authentication));
        return "product/product";
    }

    @ResponseBody
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@PathVariable int id, @Valid @RequestBody UpdateProductDTO productDTO,
            Authentication authentication, BindingResult bindingResult) {
        if (!SecurityUtils.isAdmin(authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            FieldError::getDefaultMessage,
                            (existing, replacement) -> existing + ", " + replacement));
            return ResponseEntity.badRequest().body(errors);
        }
        
        Product updatedProduct = productService.updateProduct(id, productDTO);
        if (updatedProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Product not found"));
        }
        return ResponseEntity.ok(updatedProduct);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable int id, Authentication authentication) {
        if (!SecurityUtils.isAdmin(authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Product not found"));
        }

        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/new")
    public String showCreateProductForm(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "product/create-product";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateProductForm(@PathVariable int id, Model model, Authentication authentication) {
        if (!SecurityUtils.isAdmin(authentication)) {
            return "error/403";
        }
        Product product = productService.getProductById(id);
        if (product == null) {
            return "error/404";
        }
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "product/update-product";
    }

    @GetMapping("/search/")
    public String searchProducts(
        @RequestParam(required = false) String title, Model model) {
    
        List<Product> products = title != null 
            ? productService.findByTitle(title)
            : Collections.emptyList();
        
        model.addAttribute("products", products);
        return "product/products";
    }
}