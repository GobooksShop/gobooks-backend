package org.team.bookshop.domain.category.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.bookshop.domain.category.dto.CategoryCreateRequestDto;
import org.team.bookshop.domain.category.dto.CategoryResponseDto;
import org.team.bookshop.domain.category.dto.CategoryUpdateRequestDto;
import org.team.bookshop.domain.category.entity.Category;
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

  // READ
  public List<CategoryResponseDto> getChildren(Long parentId) {
    List<Category> children = categoryRepository.findByParentId(parentId);
    return children.stream()
        .map(CategoryResponseDto::fromEntity)
        .collect(Collectors.toList());
  }

  // CREATE
  public CategoryResponseDto createCategory(Long parentId,
      CategoryCreateRequestDto createRequestDto) {
    Category parent = categoryRepository.findById(parentId)
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
    Category category = new Category();
    category.setName(createRequestDto.getName());
    category.setParent(parent);
    if (parent != null) {
      parent.addChild(category);
    }
    return CategoryResponseDto.fromEntity(categoryRepository.save(category));
  }

  // UPDATE
  public CategoryResponseDto updateCategory(Long id, CategoryUpdateRequestDto updateRequestDto) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
    category.setName(updateRequestDto.getName());
    return CategoryResponseDto.fromEntity(categoryRepository.save(category));
  }

  // DELETE
  public void deleteCategory(Long id) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
    if (!categoryRepository.existsByParentId(id)) {
      if (category.getParent() != null) {
        category.getParent().removeChild(category);
      }
      categoryRepository.deleteById(id);
    } else {
      throw new IllegalStateException("Cannot delete category with children");
    }
  }

  public CategoryResponseDto getCategoryWithChildren(Long parentId) {
    Category parent = categoryRepository.findById(parentId)
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));

    List<Category> children = parent.getChildren();

    CategoryResponseDto parentDto = CategoryResponseDto.fromEntity(parent);
    parentDto.setChildren(children.stream()
        .map(CategoryResponseDto::fromEntity)
        .collect(Collectors.toList()));

    return parentDto;
  }

  /* 클로저테이블 고려
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
  */
}
