package org.team.bookshop.domain.category.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.team.bookshop.domain.category.dto.CategoryCreateRequestDto;
import org.team.bookshop.domain.category.dto.CategoryDto;
import org.team.bookshop.domain.category.dto.CategoryUpdateRequestDto;
import org.team.bookshop.domain.category.entity.Category;
import org.team.bookshop.domain.category.repository.CategoryPathRepository;
import org.team.bookshop.domain.category.repository.CategoryRepository;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private CategoryPathRepository categoryPathRepository;

  // 전체 카테고리 조회
  public List<CategoryDto> getAllCategoryDtos() {
    List<Category> categories = categoryRepository.findAll();
    return categories.stream()
        .map(category -> new CategoryDto(category.getId(), category.getName()))
        .collect(Collectors.toList());
  }

  // 카테고리 생성
  public CategoryDto createCategory(CategoryCreateRequestDto categoryCreateRequestDto) {
    Category category = new Category();
    category.setName(categoryCreateRequestDto.getName());
    Category createdCategory = categoryRepository.save(category);

    return new CategoryDto(createdCategory.getId(), createdCategory.getName());
  }

  // 카테고리 수정
  public CategoryDto updateCategory(Long categoryId,
      CategoryUpdateRequestDto categoryUpdateRequestDto) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));

    category.setName(categoryUpdateRequestDto.getName());
    Category updatedCategory = categoryRepository.save(category);

    return new CategoryDto(updatedCategory.getId(), updatedCategory.getName());
  }

  // 카테고리 삭제
  public void deleteCategory(Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
    categoryRepository.deleteById(categoryId);
  }
}
