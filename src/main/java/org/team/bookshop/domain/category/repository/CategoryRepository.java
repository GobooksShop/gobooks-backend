package org.team.bookshop.domain.category.repository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.team.bookshop.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>,
    CategoryRepositoryCustom {

  @Query(value = "WITH RECURSIVE Category_Hierarchy(id, name, parent_id) AS (" +
      "SELECT id, name, parent_id " +
      "FROM category " +
      "WHERE id = :categoryId " +  // 시작 카테고리 ID 지정
      "UNION ALL " +
      "SELECT c.id, c.name, c.parent_id " +
      "FROM category c " +
      "JOIN Category_Hierarchy ch ON c.parent_id = ch.id) " +
      "SELECT * FROM Category_Hierarchy", nativeQuery = true)
  List<Object[]> findByIdWithChildren(@Param("categoryId") Long categoryId);

  @Query(value = "WITH RECURSIVE Category_Hierarchy(id, name, parent_id, level) AS (" +
      "SELECT id, name, parent_id, 1 AS level " +
      "FROM category " +
      "WHERE parent_id IS NULL " +
      "UNION ALL " +
      "SELECT c.id, c.name, c.parent_id, ch.level + 1 " +
      "FROM category c " +
      "JOIN Category_Hierarchy ch ON c.parent_id = ch.id) " +
      "SELECT * FROM Category_Hierarchy", nativeQuery = true)
  List<Object[]> findRootCategory();

  @Query(value = """
      WITH RECURSIVE category_path (id, name, parent_id) AS (
        SELECT id, name, parent_id
        FROM category
        WHERE id = :categoryId
        UNION ALL
        SELECT c.id, c.name, c.parent_id
        FROM category c
        INNER JOIN category_path cp ON cp.parent_id = c.id
      )
      SELECT * FROM category_path;
      """, nativeQuery = true)
  List<Object[]> findCategoryPath(@Param("categoryId") Long categoryId);

  @EntityGraph(attributePaths = "bookCategories")
  List<Category> findAllById(Iterable<Long> ids);
}
