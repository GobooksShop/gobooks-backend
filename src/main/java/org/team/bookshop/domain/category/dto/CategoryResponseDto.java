package org.team.bookshop.domain.category.dto;

import lombok.NoArgsConstructor;
import org.team.bookshop.domain.category.entity.Category;

@NoArgsConstructor
public class CategoryResponseDto extends CategoryDto {

  public CategoryResponseDto(Long id, String name) {
    super(id, name);
  }

  public static CategoryResponseDto fromEntity(Category category) {
    return new CategoryResponseDto(category.getId(), category.getName());
  }
}
