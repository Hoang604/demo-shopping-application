package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.service.CategoryService;
import io.github.Hoang604.demo_shopping_application.dto.UpdateProductDTO;
import io.github.Hoang604.demo_shopping_application.dto.CreateProductDTO;
import io.github.Hoang604.demo_shopping_application.model.Category;
import io.github.Hoang604.demo_shopping_application.model.MyUserDetails;
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
        Product product = productService.getProductById(id);
        if (product == null) {
            return "error/404"; // Trả về trang lỗi nếu sản phẩm không tồn tại
        }
        model.addAttribute("product", product);
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        model.addAttribute("userId", userDetails.getId());
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
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