package org.team.bookshop.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.product.service.ProductService;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  //상품 추가(관리자)

  //모든 상품 조회(구매자)

  //controller 분리(관리자, 구매자)
  //삼품 조회(구매자)

  //상품 삭제(관리자)

  //상품 수정(관리자)

//  // 특정 카테고리 조회
//  @GetMapping("/category/{categoryId}")
//  public ResponseEntity<List<ProductResponse>> getProductsByCategory(
//      @PathVariable Long categoryId) {
//    List<ProductResponse> productResponses = productService.getProductsByCategoryId(categoryId);
//    return ResponseEntity.ok(productResponses);
//}

//  @GetMapping("/category/{categoryId}/paged")
//  public ResponseEntity<Page<ProductResponse>> getProductsByCategoryPaged(
//      @PathVariable Long categoryId,
//      @PageableDefault(sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable
//  ) {
//    Page<ProductResponse> productResponses = productService.getProductsByCategoryId(categoryId,
//        pageable);
//    return ResponseEntity.ok(productResponses);
//  }

}

