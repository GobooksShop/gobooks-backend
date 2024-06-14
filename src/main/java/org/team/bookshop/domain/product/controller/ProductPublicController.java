package org.team.bookshop.domain.product.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.product.dto.ProductDto;
import org.team.bookshop.domain.product.dto.ProductResponse;
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
      Pageable pageable) {
    return productService.getProductsByCategoryId(categoryId, pageable);
  }

  // 특정 상품 조회
  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
    ProductResponse product = productService.getProduct(id);
    return ResponseEntity.ok(product);
  }

  // 모든 상품 조회
  @GetMapping
  public ResponseEntity<List<ProductResponse>> getAllProducts() {
    List<ProductResponse> products = productService.getAllProducts();
    return ResponseEntity.ok(products);
  }

}
