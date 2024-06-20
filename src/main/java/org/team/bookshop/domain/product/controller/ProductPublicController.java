package org.team.bookshop.domain.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.product.dto.ProductDto;
import org.team.bookshop.domain.product.dto.ProductResponseDto;
import org.team.bookshop.domain.product.dto.ProductResponseMainDto;
import org.team.bookshop.domain.product.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductPublicController {

    private final ProductService productService;

    // 카테고리별 상품 조회
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductDto> products = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    // 카테고리별 상품 조회 - paging (queryDSL 사용)
    @GetMapping("/category/{categoryId}/paged")
    public ResponseEntity<Page<ProductDto>> getProductsByCategoryPaged(
        @PathVariable Long categoryId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "12") int size,
        @RequestParam(defaultValue = "createdAt,desc") String sort) {

        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction sortDirection = Sort.Direction.fromString(sortParams[1]);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));
        Page<ProductDto> products = productService.getProductsByCategoryId(categoryId, pageable);
        return ResponseEntity.ok(products);
    }

    // 특정 상품 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        ProductResponseDto product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    // 모든 상품 조회
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/main")
    public ResponseEntity<Map<String, List<ProductResponseMainDto>>> getMainProducts() {
        List<ProductResponseMainDto> products = productService.getMainProducts();
        Map<String, List<ProductResponseMainDto>> response = new HashMap<>();
        response.put("1", products.subList(0, 8));
        response.put("2", products.subList(8, 16));
        response.put("3", products.subList(16, 24));
        response.put("4", products.subList(24, 32));
        return ResponseEntity.ok(response);
    }

}
