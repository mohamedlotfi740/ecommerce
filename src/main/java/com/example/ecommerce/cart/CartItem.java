package com.example.ecommerce.cart;

import com.example.ecommerce.product.Product;
import jakarta.persistence.*;

@Entity
@Table(name="cart_items")
public class CartItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    private Product product;

    @Column(nullable=false)
    private int quantity;

    @ManyToOne(optional=false)
    private Cart cart;

    public CartItem() {}

    public CartItem(Product product, int quantity, Cart cart) {
        this.product = product;
        this.quantity = quantity;
        this.cart = cart;
    }

    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public Cart getCart() { return cart; }

    public void setId(Long id) { this.id = id; }
    public void setProduct(Product product) { this.product = product; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setCart(Cart cart) { this.cart = cart; }
}
