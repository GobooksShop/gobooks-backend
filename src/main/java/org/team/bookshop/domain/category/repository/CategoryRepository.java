package org.team.bookshop.domain.category.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.team.bookshop.domain.category.entity.Category;
import org.team.bookshop.domain.product.entity.Product;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  boolean existsByParentId(Long id);

  @Query(value = """
      WITH RECURSIVE category_tree (id, parent_id) AS (
          SELECT id, parent_id FROM category WHERE id = :categoryId
          UNION ALL
          SELECT c.id, c.parent_id FROM category c JOIN category_tree ct ON c.parent_id = ct.id
      )
      SELECT p.* FROM products p
      JOIN book_category bc ON p.id = bc.product_id
      JOIN category_tree ct ON bc.category_id = ct.id
      """, nativeQuery = true)
  List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

  @Query("SELECT c FROM Category c LEFT JOIN FETCH c.children WHERE c.id = :parentId")
  Optional<Category> findByIdWithChildren(@Param("parentId") Long parentId);
}
