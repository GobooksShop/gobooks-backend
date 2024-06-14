package org.team.bookshop.domain.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.product.dto.AddProductRequest;
import org.team.bookshop.domain.product.dto.ProductResponse;
import org.team.bookshop.domain.product.dto.SimpleProductResponseDto;
import org.team.bookshop.domain.product.dto.UpdateProductRequest;
import org.team.bookshop.domain.product.entity.Product;
import org.team.bookshop.domain.product.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductAdminController {

  private final ProductService productService;

  // CREATE
  @PostMapping
  public ResponseEntity<SimpleProductResponseDto> createProduct(
      @RequestBody @Valid AddProductRequest request) {
    SimpleProductResponseDto savedProduct = productService.createProduct(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
  }

  // UPDATE
  @PutMapping("/{id}")
  public ResponseEntity<ProductResponse> updateProduct(@PathVariable long id,
      @Valid @RequestBody UpdateProductRequest request) {
    Product updatedProduct = productService.updateProduct(id, request);
    ProductResponse response = new ProductResponse(updatedProduct);
    return ResponseEntity.ok(response);
  }

  // DELETE
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.ok().build();
  }
}
