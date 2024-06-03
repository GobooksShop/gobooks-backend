package org.team.bookshop.domain.product.controller;

import static java.util.Arrays.stream;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.product.entity.Product;
import org.team.bookshop.domain.product.service.ProductService;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;
    @PostMapping("/api/products")
    public ResponseEntity<Product> addProduct(@RequestBody AddProductRequest request, Principal principal) {
        Product savedProduct = productService.save(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedProduct);
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<org.team.bookshop.domain.product.response.ProductResponse>> findAllProducts() {
        List<org.team.bookshop.domain.product.response.ProductResponse> products = productService.findAll();
                .stream()
                .map(ProductResponse::new)
                .toList();
        return ResponseEntity.ok()
                .body(products);
    }

    @GetMapping("/api/products/{id}")
    public ResponseEntity<ProductResponse> findProduct(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok()
                .body(new ProductResponse(product));
    }

    @DeleteMapping("/api/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id,
                                                 @Valid @RequestBody UpdateProductRequest request) {
        Product updatedProduct = productService.update(id, request);
        return ResponseEntity.ok()
                .body(updatedProduct);
    }
}
