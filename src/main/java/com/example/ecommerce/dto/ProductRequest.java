package com.example.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ProductRequest {

    @NotBlank
    public String name;

    @Min(0)
    public double price;

    @Min(0)
    public int stock;
}
