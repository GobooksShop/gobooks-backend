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
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

//  Page<Product> findAll(Pageable pageable); // 모든 상품 페이징 조회

  @Query("SELECT DISTINCT p FROM Product p "
      + "JOIN FETCH p.bookCategories bc "
      + "WHERE bc.category.id = :categoryId")
  Page<Product> findByCategory(@Param("categoryId") Long categoryId,
      Pageable pageable); // 카테고리별 상품 페이징 조회

//  //카테고리별 상품 페이징 조회
//  @Query(value = "SELECT * FROM (" +
//      "WITH RECURSIVE Category_Hierarchy(id, name, parent_id) AS (" +
//      "SELECT id, name, parent_id " +
//      "FROM Category " +
//      "WHERE id = :categoryId " +
//      "UNION ALL " +
//      "SELECT c.id, c.name, c.parent_id " +
//      "FROM Category c " +
//      "JOIN Category_Hierarchy ch ON c.parent_id = ch.id) " +
//      "SELECT DISTINCT p.* " +
//      "FROM Products p " +
//      "JOIN Book_Category bc ON p.id = bc.product_id " +
//      "JOIN Category_Hierarchy ch ON bc.category_id = ch.id) AS result",
//      countQuery = "SELECT COUNT(*) FROM (" +
//          "WITH RECURSIVE Category_Hierarchy(id, name, parent_id) AS (" +
//          "SELECT id, name, parent_id " +
//          "FROM Category " +
//          "WHERE id = :categoryId " +
//          "UNION ALL " +
//          "SELECT c.id, c.name, c.parent_id " +
//          "FROM Category c " +
//          "JOIN Category_Hierarchy ch ON c.parent_id = ch.id) " +
//          "SELECT DISTINCT p.id " +
//          "FROM Products p " +
//          "JOIN Book_Category bc ON p.id = bc.product_id " +
//          "JOIN Category_Hierarchy ch ON bc.category_id = ch.id) AS count_result",
//      nativeQuery = true)
//  Page<Product> findByCategoryIds(@Param("categoryId") Long categoryId,
//      Pageable pageable);
//
//  //카테고리별 제품 조회
//  @Query(value = "WITH RECURSIVE Category_Hierarchy(id, name, parent_id) AS ("
//      + "SELECT id, name, parent_id "
//      + "FROM Category "
//      + "WHERE id = :categoryId "
//      + "UNION ALL "
//      + "SELECT c.id, c.name, c.parent_id "
//      + "FROM Category c "
//      + "JOIN Category_Hierarchy ch ON c.parent_id = ch.id) "
//      + "SELECT DISTINCT p.* "
//      + "FROM Products p "
//      + "JOIN Book_Category bc ON p.id = bc.product_id "
//      + "JOIN Category_Hierarchy ch ON bc.category_id = ch.id",
//      nativeQuery = true)
//  List<Product> findByCategoryIds(@Param("categoryId") Long categoryId);

  @Query("select p from Product p where p.id in :productIds")
  List<Product> findByProductIds(@Param("productIds") List<Long> productIds);

}
