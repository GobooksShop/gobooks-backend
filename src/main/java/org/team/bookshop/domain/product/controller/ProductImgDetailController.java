package org.team.bookshop.domain.product.controller;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.product.entity.ProductImgDetail;
import org.team.bookshop.domain.product.service.ProductImgDetailService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/detail-img")
public class ProductImgDetailController {

    private final ProductImgDetailService productImgDetailService;

    @GetMapping("/{productId}")
    public ResponseEntity<String> getProductImgDetailByProductId(@PathVariable Long productId) {
        Optional<ProductImgDetail> productImgDetail = productImgDetailService.findByProductId(
            productId);
        return productImgDetail.map(detail -> ResponseEntity.ok(detail.getDetailPageUrl()))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductImgDetail(@PathVariable Long id) {
        productImgDetailService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
