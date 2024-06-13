package org.team.bookshop.domain.category.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.category.dto.CategoryChildrenResponseDto;
import org.team.bookshop.domain.category.dto.CategoryDto;
import org.team.bookshop.domain.category.dto.CategoryResponseDto;
import org.team.bookshop.domain.category.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryViewController {

  private final CategoryService categoryService;

  public CategoryViewController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("/categories")
  public ResponseEntity<List<CategoryResponseDto>> getRootCategories() {
    List<CategoryResponseDto> rootCategories = categoryService.getRootCategories();
    return ResponseEntity.ok(rootCategories);
  }

  @GetMapping("/categories/hierarchy")
  public ResponseEntity<List<CategoryResponseDto>> getCategoryHierarchy() {
    List<CategoryResponseDto> categoryHierarchy = categoryService.getCategoryHierarchy();
    return ResponseEntity.ok(categoryHierarchy);
  }

  // READ
  // 하위 계층을 모두 조회
  @GetMapping("/categories/{categoryId}")
  public ResponseEntity<CategoryResponseDto> getCategoryWithChildren(
      @PathVariable Long categoryId) {
    CategoryResponseDto responseDto = categoryService.getCategoryWithChildren(categoryId);
    return ResponseEntity.ok(responseDto);
  }

  // 바로 아래 depth만 조회
  @GetMapping("/categories/{categoryId}/children")
  public ResponseEntity<List<CategoryChildrenResponseDto>> getCategoryWithDirectChildren(
      @PathVariable Long categoryId) {
    List<CategoryChildrenResponseDto> children = categoryService.getCategoryWithDirectChildren(
        categoryId);
    return ResponseEntity.ok(children);
  }

  // BreadCrumb
  @GetMapping("/categories/{categoryId}/breadcrumbs")
  public ResponseEntity<List<CategoryDto>> getBreadcrumbs(@PathVariable Long categoryId) {
    List<CategoryDto> breadcrumbs = categoryService.getBreadcrumbs(categoryId);
    return ResponseEntity.ok(breadcrumbs);
  }
}
