package org.team.bookshop.domain.category.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CategoryCreateRequestDto extends CategoryDto {

  private Long parentId;

  public CategoryCreateRequestDto(String name, Long parentId) {
    super(name);
    this.parentId = parentId;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }
}
