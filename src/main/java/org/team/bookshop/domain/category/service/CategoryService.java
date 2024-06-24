package org.team.bookshop.domain.category.service;

import java.util.ArrayList;
import java.util.Collections;
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

  // root 카테고리부터 조회
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

  // BreadCrumb 조회
  public List<CategoryDto> getBreadcrumbs(Long categoryId) {
    List<Object[]> result = categoryRepository.findCategoryPath(categoryId);
    if (result.isEmpty()) {
      throw new ApiException(ErrorCode.ENTITY_NOT_FOUND);
    }

    List<CategoryDto> breadcrumbs = result.stream()
        .map(objects -> new CategoryDto(
            ((Number) objects[0]).longValue(),
            (String) objects[1],
            objects[2] != null ? ((Number) objects[2]).longValue() : null
        ))
        .collect(Collectors.toList());

    Collections.reverse(breadcrumbs);
    return breadcrumbs;
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
    // 1. Category 엔티티 조회
    List<Object[]> categoryData = categoryRepository.findByIdWithChildren(id);
    if (categoryData.isEmpty()) {
      throw new ApiException(ErrorCode.ENTITY_NOT_FOUND);
    }

    // 2. 조회된 데이터를 Category 엔티티로 변환
    Category category = convertToCategoryEntity(categoryData.get(0));

    // 3. 이름 업데이트
    category.setName(updateRequestDto.getName());

    // 4. parentId 업데이트: null 체크 후 부모 카테고리 설정
    if (updateRequestDto.getParentId() != null) {
      List<Object[]> parentCategoryData = categoryRepository.findByIdWithChildren(
          updateRequestDto.getParentId());
      if (parentCategoryData.isEmpty()) {
        throw new ApiException(ErrorCode.ENTITY_NOT_FOUND);
      }

      Category parentCategory = convertToCategoryEntity(parentCategoryData.get(0));

      if (isChildCategory(category, parentCategory)) {
        throw new ApiException(ErrorCode.INVALID_PARENT_CATEGORY);
      }

      category.setParent(parentCategory);
    } else {
      category.setParent(null);
    }

    List<Category> childCategories = category.getChildren();
    for (Category childCategory : childCategories) {
      childCategory.setParent(category);
    }

    // 5. 변경 사항 저장
    Category savedCategory = categoryRepository.save(category);

    return CategoryResponseDto.fromEntity(savedCategory);
  }

  // object->entity 변경
  private Category convertToCategoryEntity(Object[] data) {
    Long id = (Long) data[0];
    String name = (String) data[1];
    Long parentId = (Long) data[2];

    Category category = new Category();
    category.setId(id);
    category.setName(name);

    if (parentId != null) {
      Category parent = new Category();
      parent.setId(parentId);
      category.setParent(parent);
    }

    return category;
  }

  // 순환 참조 방지 로직
  private boolean isChildCategory(Category category, Category potentialParent) {
    Category current = potentialParent;
    while (current != null) {
      if (current.getId().equals(category.getId())) {
        return true;
      }
      current = current.getParent();
    }
    return false;
  }

  // DELETE
  public void deleteCategory(Long id) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));

    categoryRepository.delete(category);
  }
}
