package com.example.ecommerce.cart;

import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.product.Product;
import com.example.ecommerce.product.ProductService;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepo;
    private final ProductService productService;

    public CartService(CartRepository cartRepo, ProductService productService) {
        this.cartRepo = cartRepo;
        this.productService = productService;
    }

    public Cart getOrCreate(String userEmail) {
        return cartRepo.findByUserEmail(userEmail).orElseGet(() -> cartRepo.save(new Cart(userEmail)));
    }

    public Cart add(String userEmail, Long productId, int qty) {
        if (qty <= 0) throw new BadRequestException("Quantity must be >= 1");

        Cart cart = getOrCreate(userEmail);
        Product p = productService.get(productId);

        CartItem existing = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existing == null) {
            cart.getItems().add(new CartItem(p, qty, cart));
        } else {
            existing.setQuantity(existing.getQuantity() + qty);
        }

        return cartRepo.save(cart);
    }

    public Cart removeItem(String userEmail, Long productId) {
        Cart cart = getOrCreate(userEmail);
        boolean removed = cart.getItems().removeIf(i -> i.getProduct().getId().equals(productId));
        if (!removed) throw new NotFoundException("Item not in cart");
        return cartRepo.save(cart);
    }

    public void clear(String userEmail) {
        Cart cart = getOrCreate(userEmail);
        cart.getItems().clear();
        cartRepo.save(cart);
    }
}
