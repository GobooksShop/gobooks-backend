package org.team.bookshop.domain.category.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.team.bookshop.domain.category.entity.Category;
import org.team.bookshop.domain.category.entity.CategoryPath;

public interface CategoryPathRepository extends JpaRepository<CategoryPath, Long> {

  List<CategoryPath> findByAncestor(Category ancestor);

  List<CategoryPath> findByDescendant(Category descendant);
}
