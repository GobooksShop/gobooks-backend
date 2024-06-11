package org.team.bookshop.domain.category.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.team.bookshop.domain.category.entity.Category;

@Getter
@NoArgsConstructor
public class CategoryDto {

  private Long id;
  private String name;
  private Long parentId;

  public CategoryDto(String name) {
    this.name = name;
  }

  public CategoryDto(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Category toEntity() {
    Category category = new Category();
    category.setId(this.id);
    category.setName(this.name);
    return category;
  }
}
