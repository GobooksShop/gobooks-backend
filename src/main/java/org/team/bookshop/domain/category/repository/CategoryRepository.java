package org.team.bookshop.domain.category.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.team.bookshop.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  List<Category> findChildren(Long parentId);
}
