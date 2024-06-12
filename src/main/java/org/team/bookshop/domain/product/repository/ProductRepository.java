package org.team.bookshop.domain.product.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.team.bookshop.domain.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//  Page<Product> findAll(Pageable pageable); // 모든 상품 페이징 조회

  @Query("SELECT DISTINCT p FROM Product p "
      + "JOIN FETCH p.bookCategories bc "
      + "WHERE bc.category.id = :categoryId")
  Page<Product> findByCategory(@Param("categoryId") Long categoryId,
      Pageable pageable); // 카테고리별 상품 페이징 조회

  @Query("SELECT DISTINCT p FROM Product p "
      + "JOIN FETCH p.bookCategories bc "
      + "WHERE bc.category.id IN :categoryIds")
  Page<Product> findByCategoryIds(@Param("categoryIds") List<Long> categoryIds,
      Pageable pageable); // 하위 카테고리 포함 페이징 조회

  @Query("SELECT DISTINCT p FROM Product p "
      + "JOIN FETCH p.bookCategories bc "
      + "WHERE bc.category.id IN :categoryIds")
  List<Product> findByCategoryIds(
      @Param("categoryIds") List<Long> categoryIds); // 하위 카테고리 포함 전체 조회
}
