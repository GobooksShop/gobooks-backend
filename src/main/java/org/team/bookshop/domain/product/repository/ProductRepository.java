package org.team.bookshop.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.team.bookshop.domain.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  Page<Product> findAll(Pageable pageable); // 모든 상품 페이징 조회

  @Query("SELECT p FROM Product p JOIN p.bookCategories bc WHERE bc.category.id = :categoryId")
  Page<Product> findByCategory(@Param("categoryId") Long categoryId,
      Pageable pageable); // 카테고리별 상품 페이징 조회
}
