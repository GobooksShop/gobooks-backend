package org.team.bookshop.domain.category.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.team.bookshop.domain.category.dto.CategoryDto;
import org.team.bookshop.domain.category.dto.QCategoryDto;
import org.team.bookshop.domain.category.entity.QCategory;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<CategoryDto> findCategoryHierarchy() {
    QCategory category = QCategory.category;
    QCategory parentCategory = new QCategory("parentCategory");

    return queryFactory
        .select(new QCategoryDto(
            category.id,
            category.name,
            category.parent.id
        ))
        .from(category)
        .leftJoin(category.parent, parentCategory)
        .fetch();
  }
}
