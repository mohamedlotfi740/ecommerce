package com.example.ecommerce.cart.dto;

import jakarta.validation.constraints.Min;

public class AddToCartRequest {
    public Long productId;

    @Min(1)
    public int quantity;
}
