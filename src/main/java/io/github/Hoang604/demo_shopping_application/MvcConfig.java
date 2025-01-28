package io.github.Hoang604.demo_shopping_application;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(@NonNull ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addRedirectViewController("/", "/home")
			.setKeepQueryParams(true)
				.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		registry.addRedirectViewController("/products", "/products/")
			.setKeepQueryParams(true)
				.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		registry.addRedirectViewController("/users", "/users/")
			.setKeepQueryParams(true)
				.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		registry.addRedirectViewController("/categories", "/categories/")
			.setKeepQueryParams(true)
				.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		registry.addRedirectViewController("/orders", "/orders/")
			.setKeepQueryParams(true)
				.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		registry.addRedirectViewController("/cart", "/cart/")
			.setKeepQueryParams(true)
				.setStatusCode(HttpStatus.MOVED_PERMANENTLY);		
	}
}