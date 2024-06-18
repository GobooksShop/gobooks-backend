package org.team.bookshop.domain.product.controller;

import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.team.bookshop.domain.product.dto.ProductCreateRequestDto;
import org.team.bookshop.domain.product.dto.ProductResponseDto;
import org.team.bookshop.domain.product.dto.ProductUpdateRequestDto;
import org.team.bookshop.domain.product.dto.SimpleProductResponseDto;
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
        @RequestPart("product") @Valid ProductCreateRequestDto request,
        @RequestPart("pictureFile") MultipartFile pictureFile) throws IOException {

        SimpleProductResponseDto savedProduct = productService.createProduct(request, pictureFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable long id,
        @Valid @RequestBody ProductUpdateRequestDto request) {
        Product updatedProduct = productService.updateProduct(id, request);
        ProductResponseDto response = new ProductResponseDto(updatedProduct);
        return ResponseEntity.ok(response);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
