package org.team.bookshop.domain.category.dto;

import java.util.List;
import lombok.NoArgsConstructor;
import org.team.bookshop.domain.category.entity.Category;

@NoArgsConstructor
public class CategoryResponseDto extends CategoryDto {

  private List<CategoryResponseDto> children;

  public CategoryResponseDto(Long id, String name, List<CategoryResponseDto> children) {
    super(id, name);
    this.children = children;
  }

  public void setChildren(List<CategoryResponseDto> children) {
    this.children = children;
  }

  public static CategoryResponseDto fromEntity(Category category,
      List<CategoryResponseDto> children) {
    return new CategoryResponseDto(category.getId(), category.getName(), children);
  }
}
