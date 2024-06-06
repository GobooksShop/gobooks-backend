package org.team.bookshop.domain.category.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.team.bookshop.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query("SELECT c.id.children FROM CategoryPath c WHERE c.id.parent.id = :parentId")
  List<Category> findChildren(@Param("parentId") Long parentId);

//  @Query("SELECT p.id.parent FROM CategoryPath p WHERE p.id.children.id = :childrenId AND p.depth = 1")
//  Optional<Category> findParent(@Param("childrenId") Long childrenId);
//
//  Optional<Category> findByParentId(Long categoryId);
}
