package org.team.bookshop.domain.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CategoryUpdateRequestDto {

  @NotBlank
  private String name;

  public CategoryUpdateRequestDto(String name) {
    this.name = name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

}
