package com.example.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ProductRequest {

    @NotBlank(message = "name is required")
    private String name;

    @Min(value = 0, message = "price must be >= 0")
    private double price;

    @Min(value = 0, message = "stock must be >= 0")
    private int stock;

    public ProductRequest() {
    }

    public ProductRequest(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
