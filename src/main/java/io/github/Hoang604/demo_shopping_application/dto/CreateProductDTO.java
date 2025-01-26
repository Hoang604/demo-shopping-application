package io.github.Hoang604.demo_shopping_application.dto;

import jakarta.validation.constraints.*;

/**
 * DTO for creating new product
 * @param title Product title (2-100 characters)
 * @param price Product price (positive value)
 * @param categoryId Valid category ID (positive integer)
 * @param image URL to product image (valid URL format)
 * @param description Product description (up to 500 characters)
 */
public record CreateProductDTO(
    @NotBlank(message = "Product title cannot be blank")
    @Size(min = 2, max = 100, message = "Title must be between 2-100 characters")
    String title,

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    Double price,

    @NotNull(message = "Category ID is required")
    @Positive(message = "Invalid category ID")
    Integer categoryId,

    @NotBlank(message = "Image URL cannot be blank")
    // @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", 
    //          message = "Invalid image URL format")
    String image,

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    String description
) {
    /**
     * Validates business rules beyond basic validation
     * @throws IllegalArgumentException for business rule violations
     */
    public CreateProductDTO {
        if (price != null && price > 1_000_000) {
            throw new IllegalArgumentException("Price exceeds maximum allowed value");
        }
        
        if (description != null && description.trim().isEmpty()) {
            description = null;
        }
    }
}