package org.team.bookshop.domain.product.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.team.bookshop.domain.product.dto.ProductDto;
import org.team.bookshop.domain.product.entity.Product;

public interface ProductRepositoryCustom {

  // 상품 조회 페이징
  Page<ProductDto> findByCategoryIds(Long categoryId, Pageable pageable);

  // 페이징 없이 상품 조회
  List<Product> findByCategoryIds(Long categoryId);

  Optional<Product> findByIdWithEntityGraph(Long id, Map<String, Object> hints);

}
