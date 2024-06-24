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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.team.bookshop.domain.product.dto.ProductSaveRequestDto;
import org.team.bookshop.domain.product.dto.SimpleProductResponseDto;
import org.team.bookshop.domain.product.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductAdminController {

    private final ProductService productService;

    // CREATE
    @PostMapping
    public ResponseEntity<SimpleProductResponseDto> createProduct(
        @RequestPart("product") @Valid ProductSaveRequestDto request,
        @RequestPart("pictureFile") MultipartFile pictureFile) throws IOException {

        SimpleProductResponseDto savedProduct = productService.saveOrUpdateProduct(request,
            pictureFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<SimpleProductResponseDto> updateProduct(
        @PathVariable long id,
        @RequestPart("product") @Valid ProductSaveRequestDto request,
        @RequestPart(value = "pictureFile", required = false) MultipartFile pictureFile)
        throws IOException {

        request.setId(id);
        SimpleProductResponseDto updatedProduct = productService.saveOrUpdateProduct(request,
            pictureFile);
        return ResponseEntity.ok(updatedProduct);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
