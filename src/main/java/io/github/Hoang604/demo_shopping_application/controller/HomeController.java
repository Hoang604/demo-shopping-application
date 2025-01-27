package io.github.Hoang604.demo_shopping_application.controller;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.Hoang604.demo_shopping_application.model.Product;
import io.github.Hoang604.demo_shopping_application.service.ProductService;
import io.github.Hoang604.demo_shopping_application.utils.SecurityUtils;

import org.springframework.ui.Model;

@Controller
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        if (authentication == null) {
            return "index";
        }

        model.addAttribute("userId", SecurityUtils.getCurrentUserId(authentication));

        // Lấy danh sách sản phẩm ngẫu nhiên (ví dụ: 10 sản phẩm)
        List<Product> randomProducts = productService.getRandomProducts(10);
        model.addAttribute("randomProducts", randomProducts);

        return "index";
    }
}
