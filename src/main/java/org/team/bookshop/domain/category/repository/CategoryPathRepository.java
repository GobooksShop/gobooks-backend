package org.team.bookshop.domain.category.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.team.bookshop.domain.category.entity.Category;
import org.team.bookshop.domain.category.entity.CategoryPath;

public interface CategoryPathRepository extends JpaRepository<CategoryPath, Long> {

  @Query("SELECT cp FROM CategoryPath cp WHERE cp.id.parent = :parent")
  List<CategoryPath> findByParent(@Param("parent") Category parent);

  @Query("SELECT cp FROM CategoryPath cp WHERE cp.id.children = :children")
  List<CategoryPath> findByChildren(@Param("children") Category children);
}
