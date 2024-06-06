package org.team.bookshop.domain.category.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CategoryUpdateRequestDto extends CategoryDto {

  private String name;
  private Long parentId;

  public CategoryUpdateRequestDto(String name, Long parentId) {
    super(name);
    this.parentId = parentId;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public void setName(String name) {
    this.name = name;
  }

}
