package org.team.bookshop.domain.category.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.team.bookshop.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

//    boolean existsByParentId(Long id);
//
//    @Query("SELECT DISTINCT p FROM Product p " +
//        "JOIN FETCH p.bookCategories bc " +
//        "JOIN FETCH bc.category c " +
//        "WHERE c.id = :categoryId")
//    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

  @Query(value = "WITH RECURSIVE Category_Hierarchy(id, name, parent_id) AS (" +
      "SELECT id, name, parent_id " +
      "FROM Category " +
      "WHERE id = :categoryId " +  // 시작 카테고리 ID 지정
      "UNION ALL " +
      "SELECT c.id, c.name, c.parent_id " +
      "FROM Category c " +
      "JOIN Category_Hierarchy ch ON c.parent_id = ch.id) " +
      "SELECT * FROM Category_Hierarchy", nativeQuery = true)
  List<Object[]> findByIdWithChildren(@Param("categoryId") Long categoryId);

  @Query(value = "WITH RECURSIVE Category_Hierarchy(id, name, parent_id, level) AS (" +
      "SELECT id, name, parent_id, 1 AS level " +
      "FROM Category " +
      "WHERE parent_id IS NULL " +
      "UNION ALL " +
      "SELECT c.id, c.name, c.parent_id, ch.level + 1 " +
      "FROM Category c " +
      "JOIN Category_Hierarchy ch ON c.parent_id = ch.id) " +
      "SELECT * FROM Category_Hierarchy", nativeQuery = true)
  List<Object[]> findRootCategory();

//    @Query("SELECT c FROM Category c "
//        + "WHERE c.parent.id = :parentId")
//    List<Category> findByParentId(@Param("parentId") Long parentId);

  @Query("SELECT c FROM Category c "
      + "WHERE c.id = :categoryId "
      + "OR c.parent.id = :categoryId "
      + "OR c.parent.parent.id = :categoryId")
  List<Category> findAllSubcategories(@Param("categoryId") Long categoryId);
}
