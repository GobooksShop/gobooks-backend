package org.team.bookshop.domain.category.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.category.dto.CategoryCreateRequestDto;
import org.team.bookshop.domain.category.dto.CategoryResponseDto;
import org.team.bookshop.domain.category.dto.CategoryUpdateRequestDto;
import org.team.bookshop.domain.category.service.CategoryService;

@RestController
@RequestMapping("/api")
//@PreAuthorize("hasAnyRole('ROLE_ADMIN')") // 프론트 accsstoken 인증 문제 해결 전까지는 모든 요청 스킵함.
public class CategoryAdminController {

  private final CategoryService categoryService;

  public CategoryAdminController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  // CREATE
  @PostMapping("/admin/categories")
  public ResponseEntity<CategoryResponseDto> createCategory(
      @RequestBody CategoryCreateRequestDto categoryCreateRequestDto) {
    CategoryResponseDto responseDto = categoryService.createCategory(categoryCreateRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  // UPDATE
  @PutMapping("/admin/categories/{categoryId}")
  public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long categoryId,
      @RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto) {
    CategoryResponseDto updatedCategoryDto = categoryService.updateCategory(categoryId,
        categoryUpdateRequestDto);
    return ResponseEntity.ok(updatedCategoryDto);
  }

  // DELETE
  @DeleteMapping("/admin/categories/{categoryId}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
    categoryService.deleteCategory(categoryId);
    return ResponseEntity.noContent().build();
  }
}
