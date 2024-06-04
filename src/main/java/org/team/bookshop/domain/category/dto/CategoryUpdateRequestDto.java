package org.team.bookshop.domain.category.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CategoryUpdateRequestDto extends CategoryDto {

  public CategoryUpdateRequestDto(String name) {
    super(name);
  }
}
