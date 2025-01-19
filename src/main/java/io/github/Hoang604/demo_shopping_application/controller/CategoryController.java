package io.github.Hoang604.demo_shopping_application.controller;

import io.github.Hoang604.demo_shopping_application.dto.UpdateCategoryDTO;
import io.github.Hoang604.demo_shopping_application.model.Category;
import io.github.Hoang604.demo_shopping_application.model.Product;
import io.github.Hoang604.demo_shopping_application.service.CategoryService;
import io.github.Hoang604.demo_shopping_application.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products/categories/")
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public String getAllCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category/categories";
    }
    
    @PostMapping("new")
    public String createCategory(@RequestBody Category category, RedirectAttributes redirectAttributes) {
        Category newCategory = categoryService.createCategory(category);
        redirectAttributes.addFlashAttribute("category", newCategory);
        redirectAttributes.addFlashAttribute("message", "Category created successfully");
        return "redirect:/products/categories/" + newCategory.getId();
    }

    @GetMapping("{id}")
    public String getCategoryById(@PathVariable int id, Model model) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return "error/404";
        }
        model.addAttribute("category", category);
        List<Product> products = productService.findByCategory(category);
        model.addAttribute("products", products);
        return "product/products";
    }

    @PutMapping("{id}")
    public String updateCategory(@PathVariable int id, @RequestBody UpdateCategoryDTO category, Model model) {
        Category existingCategory = categoryService.getCategoryById(id);
        if (existingCategory == null) {
            return "error/404";
        }

        // Cập nhật các trường của Category
        existingCategory.setName(category.name());

        model.addAttribute("message", "Category updated successfully");
        Category updatedCategory = categoryService.updateCategory(existingCategory);
        return "redirect:/products/categories/" + updatedCategory.getId();
    }

    @DeleteMapping("{id}")
    public String deleteCategoryById(@PathVariable int id, Model model) {
        if (categoryService.getCategoryById(id) == null) {
            return "error/404";
        }
        categoryService.deleteCategoryById(id);
        model.addAttribute("message", "Category deleted successfully");
        return "redirect:/products/";
    }
}