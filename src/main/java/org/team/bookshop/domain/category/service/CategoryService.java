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

  private final CategoryRepository categoryRepository;
  private final CategoryPathRepository categoryPathRepository;

  public CategoryService(CategoryRepository categoryRepository,
      CategoryPathRepository categoryPathRepository) {
    this.categoryRepository = categoryRepository;
    this.categoryPathRepository = categoryPathRepository;
  }

  // READ
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

  // CREATE
  public CategoryResponseDto createCategory(
      CategoryCreateRequestDto createRequestDto) {
    Category parent = null;
    Long parentId = createRequestDto.getParentId();

    if (parentId != null) {
      parent = categoryRepository.findById(parentId)
          .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
    }

    Category category = new Category();
    category.setName(createRequestDto.getName());
    category.setParent(parent);

    Category savedCategory = categoryRepository.save(category);
    return CategoryResponseDto.fromEntity(savedCategory);
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
      throw new ApiException(ErrorCode.CATEGORY_HAS_CHILDREN);
    }
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
  @Transactional
  public CategoryResponseDto updateCategory(Long categoryId,
      CategoryUpdateRequestDto categoryUpdateRequestDto) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
    category.setName(categoryUpdateRequestDto.getName());
    Optional<Category> parent = categoryRepository.findParent(categoryId);
    Category parentCategory = parent.orElse(null);
    Category updatedCategory = categoryRepository.save(category);
    return CategoryResponseDto.fromEntity(updatedCategory, getChildren(categoryId), parentCategory);
  }

  // DELETE
  @Transactional
  public void deleteCategory(Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
    deletePaths(category);
    categoryRepository.delete(category);
  }

  public void deletePaths(Category category) {
    List<CategoryPath> paths = categoryPathRepository.findByChildren(category);
    categoryPathRepository.deleteAll(paths);
  }
  */
}
