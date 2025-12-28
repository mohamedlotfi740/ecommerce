package com.example.ecommerce.order;

import com.example.ecommerce.cart.Cart;
import com.example.ecommerce.cart.CartService;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.product.Product;
import com.example.ecommerce.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckoutService {

    private final CartService cartService;
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;

    public CheckoutService(CartService cartService, OrderRepository orderRepo, ProductRepository productRepo) {
        this.cartService = cartService;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    @Transactional
    public Order checkout(String userEmail) {
        Cart cart = cartService.getOrCreate(userEmail);
        if (cart.getItems().isEmpty()) throw new BadRequestException("Cart is empty");

        // تحقق من stock
        cart.getItems().forEach(item -> {
            Product p = item.getProduct();
            if (p.getStock() < item.getQuantity()) {
                throw new BadRequestException("Not enough stock for product: " + p.getName());
            }
        });

        Order order = new Order(userEmail);

        double total = 0.0;

        for (var item : cart.getItems()) {
            Product p = item.getProduct();

            // خصم stock
            p.setStock(p.getStock() - item.getQuantity());
            productRepo.save(p);

            double lineTotal = p.getPrice() * item.getQuantity();
            total += lineTotal;

            order.getItems().add(new OrderItem(p, item.getQuantity(), p.getPrice(), order));
        }

        order.setTotal(total);
        Order saved = orderRepo.save(order);

        // فضي الكارت
        cartService.clear(userEmail);

        return saved;
    }
}
