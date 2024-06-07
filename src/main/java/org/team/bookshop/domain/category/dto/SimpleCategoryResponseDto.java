package org.team.bookshop.domain.category.dto;

import lombok.Getter;
import org.team.bookshop.domain.category.entity.Category;

@Getter
public class SimpleCategoryResponseDto {

  private Long id;
  private String name;

  public SimpleCategoryResponseDto(Category category) {
    this.id = category.getId();
    this.name = category.getName();
  }
}
