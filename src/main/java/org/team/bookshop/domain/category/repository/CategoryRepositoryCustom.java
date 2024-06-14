package org.team.bookshop.domain.category.repository;

import java.util.List;
import org.team.bookshop.domain.category.dto.CategoryDto;

public interface CategoryRepositoryCustom {

  List<CategoryDto> findCategoryHierarchy();
}
