package org.team.bookshop.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.team.bookshop.domain.category.entity.Category;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryChildrenResponseDto {

  private Long id;
  private String name;

  public static CategoryChildrenResponseDto fromEntity(Category category) {
    CategoryChildrenResponseDto dto = new CategoryChildrenResponseDto();
    dto.setId(category.getId());
    dto.setName(category.getName());
    return dto;
  }
}
