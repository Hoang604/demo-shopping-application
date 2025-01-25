package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.service.CategoryService;
import io.github.Hoang604.demo_shopping_application.dto.UpdateProductDTO;
import io.github.Hoang604.demo_shopping_application.dto.CreateProductDTO;
import io.github.Hoang604.demo_shopping_application.model.Category;
import io.github.Hoang604.demo_shopping_application.model.MyUserDetails;
import io.github.Hoang604.demo_shopping_application.model.Product;
import io.github.Hoang604.demo_shopping_application.service.ProductService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import java.util.Collections;
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
    public String getAllProducts(@RequestParam(required = false) Integer categoryId, Model model, Authentication authentication) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        model.addAttribute("products", products);
        if (categoryId != null){
            Category category = categoryService.getCategoryById(categoryId);
            model.addAttribute("category", category);
        }
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        model.addAttribute("userId", userDetails.getId());
        return "product/products";
    }


    @PostMapping(value = "/", consumes = "application/json")
    public String createProduct(@RequestBody CreateProductDTO product, Model model) {
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