package org.team.bookshop.domain.category.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.category.dto.CategoryResponseDto;
import org.team.bookshop.domain.category.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryViewController {

  private final CategoryService categoryService;

  public CategoryViewController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  // READ
  @GetMapping("/categories/{categoryId}")
  public ResponseEntity<CategoryResponseDto> getCategory(
      @PathVariable Long categoryId) {
    CategoryResponseDto categoryResponseDto = categoryService.getCategoryWithChildren(
        categoryId);
    return ResponseEntity.ok(categoryResponseDto);
  }
}
