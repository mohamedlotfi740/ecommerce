package com.example.ecommerce.cart;

import com.example.ecommerce.cart.dto.AddToCartRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping
    public Cart get(Authentication auth) {
        return service.getOrCreate(auth.getName());
    }

    @PostMapping("/add")
    public Cart add(Authentication auth, @Valid @RequestBody AddToCartRequest req) {
        return service.add(auth.getName(), req.productId, req.quantity);
    }

    @DeleteMapping("/remove/{productId}")
    public Cart remove(Authentication auth, @PathVariable Long productId) {
        return service.removeItem(auth.getName(), productId);
    }

    @DeleteMapping("/clear")
    public void clear(Authentication auth) {
        service.clear(auth.getName());
    }
}
