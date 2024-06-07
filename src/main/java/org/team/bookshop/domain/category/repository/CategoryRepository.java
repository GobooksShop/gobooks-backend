package org.team.bookshop.domain.category.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.team.bookshop.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

//  @Query("SELECT c FROM Category c WHERE c.parent = :parent")
//  List<Category> findChildren(@Param("parent") Long parentId);
//
//  @Query("SELECT p.id.parent FROM CategoryPath p WHERE p.id.children.id = :childrenId AND p.depth = 1")
//  Optional<Category> findParent(@Param("childrenId") Long childrenId);

  @Query("SELECT c FROM Category c LEFT JOIN FETCH c.children WHERE c.parent.id = :parentId")
  List<Category> findByParentId(@Param("parentId") Long parentId);

  boolean existsByParentId(Long id);
}
