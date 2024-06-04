package org.team.bookshop.domain.category.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.category.dto.CategoryCreateRequestDto;
import org.team.bookshop.domain.category.dto.CategoryDto;
import org.team.bookshop.domain.category.dto.CategoryUpdateRequestDto;
import org.team.bookshop.domain.category.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {

  @Autowired
  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  // 전체 사용자 ========

  // 카테고리 목록 조회
  @GetMapping("/categories")
  public ResponseEntity<List<CategoryDto>> getAllCategories() {
    List<CategoryDto> categoryDtos = categoryService.getAllCategoryDtos();
    return ResponseEntity.ok(categoryDtos);
  }

  // 관리자 ============

  // 카테고리 추가
  @PostMapping("/admin/categories")
  public ResponseEntity<CategoryDto> createCategory(
      @RequestBody CategoryCreateRequestDto categoryCreateRequestDto) {
    CategoryDto createdCategoryDto = categoryService.createCategory(categoryCreateRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdCategoryDto);
  }

  // 카테고리 수정
  @PutMapping("/admin/categories/{categoryId}")
  public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long categoryId,
      @RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto) {
    CategoryDto updatedCategoryDto = categoryService.updateCategory(categoryId,
        categoryUpdateRequestDto);
    return ResponseEntity.ok(updatedCategoryDto);
  }

  // 카테고리 삭제
  @DeleteMapping("/admin/categories/{categoryId}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
    categoryService.deleteCategory(categoryId);
    return ResponseEntity.noContent().build();
  }
}