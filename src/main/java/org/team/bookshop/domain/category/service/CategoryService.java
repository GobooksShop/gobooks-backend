package org.team.bookshop.domain.category.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.bookshop.domain.category.dto.CategoryChildrenResponseDto;
import org.team.bookshop.domain.category.dto.CategoryCreateRequestDto;
import org.team.bookshop.domain.category.dto.CategoryDto;
import org.team.bookshop.domain.category.dto.CategoryResponseDto;
import org.team.bookshop.domain.category.dto.CategoryUpdateRequestDto;
import org.team.bookshop.domain.category.entity.Category;
import org.team.bookshop.domain.category.repository.CategoryRepository;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;

@Service
@Transactional
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<CategoryResponseDto> getRootCategories() {
    List<Object[]> categories = categoryRepository.findRootCategory();
    Map<Long, CategoryResponseDto> categoryMap = new HashMap<>();
    List<CategoryResponseDto> rootCategories = new ArrayList<>();

    for (Object[] category : categories) {
      Long id = ((Number) category[0]).longValue();
      String name = (String) category[1];
      Long parentId = category[2] != null ? ((Number) category[2]).longValue() : null;

      CategoryResponseDto categoryDto = new CategoryResponseDto(id, name);
      categoryMap.put(id, categoryDto);

      if (parentId == null) {
        rootCategories.add(categoryDto);
      } else {
        CategoryResponseDto parentCategory = categoryMap.get(parentId);
        if (parentCategory != null) {
          parentCategory.getChildren().add(categoryDto);
        }
      }
    }

    return rootCategories;
  }

  // READ
  // 하위 계층을 모두 조회
  public CategoryResponseDto getCategoryWithChildren(Long parentId) {
    List<Object[]> categoryHierarchy = categoryRepository.findByIdWithChildren(parentId);

    Map<Long, CategoryResponseDto> categoryMap = new HashMap<>();

    for (Object[] categoryData : categoryHierarchy) {
      Long id = ((Number) categoryData[0]).longValue();
      String name = (String) categoryData[1];
      Long parentCategoryId =
          categoryData[2] != null ? ((Number) categoryData[2]).longValue() : null;

      CategoryResponseDto categoryDto = new CategoryResponseDto(id, name);
      categoryMap.put(id, categoryDto);

      if (parentCategoryId != null) {
        CategoryResponseDto parentDto = categoryMap.get(parentCategoryId);
        parentDto.getChildren().add(categoryDto);
      }
    }

    return categoryMap.get(parentId);
  }

  // 바로 아래 depth만 조회
  public List<CategoryChildrenResponseDto> getCategoryWithDirectChildren(Long parentId) {
    Category parent = categoryRepository.findById(parentId)
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));

    return parent.getChildren().stream()
        .map(CategoryChildrenResponseDto::fromEntity)
        .collect(Collectors.toList());
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

    categoryRepository.delete(category);
  }

    // QueryDSL을 사용하여 카테고리 계층을 조회하는 새로운 메서드
    public List<CategoryResponseDto> getCategoryHierarchy() {
        List<CategoryDto> categories = categoryRepository.findCategoryHierarchy();
        Map<Long, CategoryResponseDto> categoryMap = new HashMap<>();
        List<CategoryResponseDto> rootCategories = new ArrayList<>();

        for (CategoryDto categoryDto : categories) {
            Long id = categoryDto.getId();
            String name = categoryDto.getName();
            Long parentId = categoryDto.getParentId();

            CategoryResponseDto categoryResponseDto = new CategoryResponseDto(id, name);
            categoryMap.put(id, categoryResponseDto);

            if (parentId == null) {
                rootCategories.add(categoryResponseDto);
            } else {
                CategoryResponseDto parentCategory = categoryMap.get(parentId);
                if (parentCategory != null) {
                    parentCategory.getChildren().add(categoryResponseDto);
                }
            }
        }

        return rootCategories;
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
