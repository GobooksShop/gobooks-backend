package org.team.bookshop.domain.category.dto;

import lombok.Getter;
import lombok.Setter;
import org.team.bookshop.domain.category.entity.Category;

@Getter
@Setter
public class CategoryDto {

  private Long id;
  private String name;

  public CategoryDto(Long id, String name) {
  }

  public Category toEntity() {
    Category category = new Category();
    category.setName(this.name);
    return category;
  }

}
