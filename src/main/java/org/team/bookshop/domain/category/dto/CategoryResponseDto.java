package org.team.bookshop.domain.category.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.team.bookshop.domain.category.entity.Category;

@Getter
@NoArgsConstructor
public class CategoryResponseDto extends CategoryDto {

  @JsonInclude(Include.NON_NULL)
  private CategoryResponseDto parent;

  private List<CategoryResponseDto> children;

  public CategoryResponseDto(Long id, String name, List<CategoryResponseDto> children,
      CategoryResponseDto parent) {
    super(id, name);
    this.children = children;
    this.parent = parent;
  }

  public void setChildren(List<CategoryResponseDto> children) {
    this.children = children;
  }

  public static CategoryResponseDto fromEntity(Category category,
      List<CategoryResponseDto> children, Category parent) {
    return new CategoryResponseDto(category.getId(), category.getName(),
        children, parent != null ? fromEntity(parent, new ArrayList<>(), null) : null);
  }

}
