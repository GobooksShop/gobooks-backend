package org.team.bookshop.domain.product.controller;

import java.util.List;
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
  public Page<ProductDto> getProductsByCategoryPaged(@PathVariable Long categoryId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "12") int size,
      @RequestParam(defaultValue = "createdAt,desc") String sort) {

    Pageable pageable = PageRequest.of(page, size,
        Sort.by(Sort.Direction.DESC, "createdAt")); // 기본 정렬 추가
    return productService.getProductsByCategoryId(categoryId, pageable);
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

}
