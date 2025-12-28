package com.example.ecommerce.order;

import com.example.ecommerce.product.Product;
import jakarta.persistence.*;

@Entity
@Table(name="order_items")
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    private Product product;

    @Column(nullable=false)
    private int quantity;

    @Column(nullable=false)
    private double priceAtPurchase;

    @ManyToOne(optional=false)
    private Order order;

    public OrderItem() {}

    public OrderItem(Product product, int quantity, double priceAtPurchase, Order order) {
        this.product = product;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
        this.order = order;
    }

    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getPriceAtPurchase() { return priceAtPurchase; }
    public Order getOrder() { return order; }

    public void setId(Long id) { this.id = id; }
    public void setProduct(Product product) { this.product = product; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPriceAtPurchase(double priceAtPurchase) { this.priceAtPurchase = priceAtPurchase; }
    public void setOrder(Order order) { this.order = order; }
}
