package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductRequest;
import com.example.ecommerce.dto.ProductResponse;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public ProductResponse create(ProductRequest request) {
        Product p = new Product(request.getName(), request.getPrice(), request.getStock());
        Product saved = repo.save(p);
        return toResponse(saved);
    }

    public List<ProductResponse> getAll() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public ProductResponse getById(Long id) {
        Product p = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        return toResponse(p);
    }

    public ProductResponse update(Long id, ProductRequest request) {
        Product p = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));

        p.setName(request.getName());
        p.setPrice(request.getPrice());
        p.setStock(request.getStock());

        return toResponse(repo.save(p));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Product not found: " + id);
        }
        repo.deleteById(id);
    }

    private ProductResponse toResponse(Product p) {
        return new ProductResponse(p.getId(), p.getName(), p.getPrice(), p.getStock());
    }
}
