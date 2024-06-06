package org.team.bookshop.domain.category.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.team.bookshop.domain.category.entity.Category;

@Getter
@NoArgsConstructor
public class CategoryResponseDto extends CategoryDto {

  @JsonIgnore // 순환참조 방지
  private CategoryResponseDto parent;

  private List<CategoryResponseDto> children;

  @Builder
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
      List<CategoryResponseDto> children) {
    return CategoryResponseDto.builder()
        .id(category.getId())
        .name(category.getName())
        .parent(
            category.getParent() != null ? CategoryResponseDto.fromEntity(category.getParent(),
                children) : null
        )
        .children(children)
        .build();
  }

}
