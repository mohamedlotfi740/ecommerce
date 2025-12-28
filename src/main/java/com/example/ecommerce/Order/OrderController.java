package com.example.ecommerce.order;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepo;
    private final CheckoutService checkoutService;

    public OrderController(OrderRepository orderRepo, CheckoutService checkoutService) {
        this.orderRepo = orderRepo;
        this.checkoutService = checkoutService;
    }

    @GetMapping
    public List<Order> myOrders(Authentication auth) {
        return orderRepo.findByUserEmailOrderByCreatedAtDesc(auth.getName());
    }

    @PostMapping("/checkout")
    public Order checkout(Authentication auth) {
        return checkoutService.checkout(auth.getName());
    }
}
