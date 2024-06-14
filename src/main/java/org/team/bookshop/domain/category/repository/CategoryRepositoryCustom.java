package org.team.bookshop.domain.category.repository;

import java.util.Collection;
import java.util.List;
import org.team.bookshop.domain.category.dto.CategoryDto;
import org.team.bookshop.domain.category.entity.Category;

public interface CategoryRepositoryCustom {

  List<CategoryDto> findCategoryHierarchy();

  List<Category> findAllByIdAndFetchParentCategories(Collection<Long> categoryIds);
}
