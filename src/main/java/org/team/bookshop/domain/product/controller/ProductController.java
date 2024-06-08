package org.team.bookshop.domain.product.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.product.dto.AddProductRequest;
import org.team.bookshop.domain.product.dto.ProductResponse;
import org.team.bookshop.domain.product.dto.UpdateProductRequest;
import org.team.bookshop.domain.product.entity.Product;
import org.team.bookshop.domain.product.service.ProductService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  //상품 추가(관리자)
  @PostMapping
  public ResponseEntity<Product> addProduct(@RequestBody @Valid AddProductRequest request) {
    Product savedProduct = productService.createProduct(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
  }

  //모든 상품 조회(구매자)
  @GetMapping
  public ResponseEntity<List<ProductResponse>> findAllProducts() {
    List<ProductResponse> products = productService.findAll();
    return ResponseEntity.ok(products);
  }

  //controller 분리(관리자, 구매자)
  //삼품 조회(구매자)
  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> findProduct(@PathVariable Long id) {
    ProductResponse product = productService.findById(id);
    return ResponseEntity.ok(product);
  }

  //상품 삭제(관리자)
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.delete(id);
    return ResponseEntity.ok().build();
  }

  //상품 수정(관리자)
  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable long id,
      @Valid @RequestBody UpdateProductRequest request) {
    Product updatedProduct = productService.update(id, request);
    return ResponseEntity.ok(updatedProduct);
  }

  // 특정 카테고리 상품 조회
  @GetMapping("/category/{categoryId}")
  public ResponseEntity<List<ProductResponse>> getProductsByCategory(
      @PathVariable Long categoryId) {
    List<ProductResponse> productResponses = productService.getProductsByCategoryId(categoryId);
    return ResponseEntity.ok(productResponses);
  }

  // 특정 카테고리 상품 페이징 조회
  @GetMapping("/category/{categoryId}/products")
  public ResponseEntity<Page<ProductResponse>> getProductsByCategoryWithPaging(
      @PathVariable Long categoryId,
      @RequestParam(defaultValue = "0") int page, // 페이지 번호
      @RequestParam(defaultValue = "10") int size,  // 페이지 크기
      @RequestParam(defaultValue = "createdAt") String sortBy) {  // 정렬기준

    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
    Page<ProductResponse> productResponses = productService.getProductsByCategoryId(categoryId,
        pageable);
    return ResponseEntity.ok(productResponses);
  }
}

