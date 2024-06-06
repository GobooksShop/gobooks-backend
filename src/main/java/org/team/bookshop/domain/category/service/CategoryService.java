package org.team.bookshop.domain.category.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.bookshop.domain.category.dto.CategoryCreateRequestDto;
import org.team.bookshop.domain.category.dto.CategoryResponseDto;
import org.team.bookshop.domain.category.dto.CategoryUpdateRequestDto;
import org.team.bookshop.domain.category.entity.Category;
import org.team.bookshop.domain.category.entity.CategoryPath;
import org.team.bookshop.domain.category.repository.CategoryPathRepository;
import org.team.bookshop.domain.category.repository.CategoryRepository;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;

@Service
@Transactional
public class CategoryService {

  private CategoryRepository categoryRepository;
  private final CategoryPathRepository categoryPathRepository;

  public CategoryService(CategoryRepository categoryRepository,
      CategoryPathRepository categoryPathRepository) {
    this.categoryRepository = categoryRepository;
    this.categoryPathRepository = categoryPathRepository;
  }

  // CREATE
  public CategoryResponseDto createCategory(CategoryCreateRequestDto requestDto) {
    Category category = new Category();
    category.setName(requestDto.getName());

    if (requestDto.getParentId() != null) {
      Category parentCategory = categoryRepository.findById(requestDto.getParentId())
          .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
      category.setParent(parentCategory);
    }
    Category savedCategory = categoryRepository.save(category);
    updateCategoryPath(savedCategory);

    return CategoryResponseDto.builder()
        .id(savedCategory.getId())
        .name(savedCategory.getName())
        .build();
  }

  // READ
  public List<CategoryResponseDto> getChildren(Long categoryId) {
    List<Category> children = categoryRepository.findChildren(categoryId);
    return children.stream()
        .map(child -> CategoryResponseDto.builder()
            .id(child.getId())
            .name(child.getName())
            .build())
        .collect(Collectors.toList());
  }

  // UPDATE
  public CategoryResponseDto updateCategory(Long categoryId, CategoryUpdateRequestDto requestDto) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));

    category.setName(requestDto.getName());

    if (requestDto.getParentId() != null && !requestDto.getParentId()
        .equals(category.getParent().getId())) {
      // 기존 클로저테이블 경로 삭제
      categoryPathRepository.deleteByParentIdOrChildrenId(categoryId, categoryId);
      // 새로운 부모 카테고리 조회
      Category newParent = categoryRepository.findById(requestDto.getParentId())
          .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
      // 부모 설정
      category.setParent(newParent);
      // 클로제테이블 경로 업데이트
      updateCategoryPath(category);
    }

    Category savedCategory = categoryRepository.save(category);
    return CategoryResponseDto.builder()
        .id(savedCategory.getId())
        .name(savedCategory.getName())
        .build();
  }

  // DELETE
  public void deleteCategory(Long categoryId) {
    if (!categoryRepository.findChildren(categoryId).isEmpty()) {
      throw new ApiException(ErrorCode.CATEGORY_HAS_CHILDREN);
    }
    categoryPathRepository.deleteByParentIdOrChildrenId(categoryId, categoryId);
    categoryRepository.deleteById(categoryId);
  }

  // CATEGORY PATH
  public void updateCategoryPath(Category category) {
    CategoryPath selfPath = new CategoryPath(category, category, 0);
    categoryPathRepository.save(selfPath);

    if (category.getParent() == null) {
      return;
    }

    List<CategoryPath> parentPaths = categoryPathRepository.findByChildren(category.getParent());
    for (CategoryPath parentPath : parentPaths) {
      CategoryPath newPath = new CategoryPath(parentPath.getParent(), category,
          parentPath.getDepth() + 1);
      categoryPathRepository.save(newPath);
    }
  }
}
