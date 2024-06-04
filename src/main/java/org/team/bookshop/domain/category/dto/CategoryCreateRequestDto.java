package org.team.bookshop.domain.category.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CategoryCreateRequestDto extends CategoryDto {

  public CategoryCreateRequestDto(Long id, String name) {
    super(id, name);
  }
}
