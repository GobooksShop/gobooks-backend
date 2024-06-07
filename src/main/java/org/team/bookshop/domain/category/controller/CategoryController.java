package org.team.bookshop.domain.category.controller;

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
import org.team.bookshop.domain.category.dto.CategoryResponseDto;
import org.team.bookshop.domain.category.dto.CategoryUpdateRequestDto;
import org.team.bookshop.domain.category.entity.Category;
import org.team.bookshop.domain.category.repository.CategoryRepository;
import org.team.bookshop.domain.category.service.CategoryService;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;

@RestController
@RequestMapping("/api")
public class CategoryController {

  @Autowired
  private final CategoryService categoryService;
  @Autowired
  private CategoryRepository categoryRepository;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  // 전체 사용자 ========

  // READ
  @GetMapping("/categories/{categoryId}")
  public ResponseEntity<CategoryResponseDto> getCategory(
      @PathVariable Long categoryId) {
    CategoryResponseDto categoryResponseDto = categoryService.getCategoryWithChildren(
        categoryId);
    return ResponseEntity.ok(categoryResponseDto);
  }

  // 관리자 ============

  // CREATE
  @PostMapping("/admin/categories")
  public ResponseEntity<CategoryResponseDto> createCategory(
      @RequestBody CategoryCreateRequestDto categoryCreateRequestDto) {
    Long parentId = categoryCreateRequestDto.getParentId();
    Category parent = null;
    if (parentId != null) {
      parent = categoryRepository.findById(parentId)
          .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
    }
    Category category = new Category();
    category.setName(categoryCreateRequestDto.getName());
    category.setParent(parent);
    Category savedCategory = categoryRepository.save(category);
    CategoryResponseDto responseDto = CategoryResponseDto.fromEntity(savedCategory);
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