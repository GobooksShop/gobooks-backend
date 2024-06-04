package org.team.bookshop.domain.category.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.team.bookshop.domain.category.dto.CategoryCreateRequestDto;
import org.team.bookshop.domain.category.dto.CategoryResponseDto;
import org.team.bookshop.domain.category.dto.CategoryUpdateRequestDto;
import org.team.bookshop.domain.category.entity.Category;
import org.team.bookshop.domain.category.repository.CategoryPathRepository;
import org.team.bookshop.domain.category.repository.CategoryRepository;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;

@Service
public class CategoryService {

  @Autowired
  private final CategoryRepository categoryRepository;

  @Autowired
  private final CategoryPathRepository categoryPathRepository;

  public CategoryService(CategoryRepository categoryRepository,
      CategoryPathRepository categoryPathRepository) {
    this.categoryRepository = categoryRepository;
    this.categoryPathRepository = categoryPathRepository;
  }

  // CREATE
  public CategoryResponseDto createCategory(CategoryCreateRequestDto categoryCreateRequestDto) {
    Category category = categoryCreateRequestDto.toEntity();
    Category createdCategory = categoryRepository.save(category);
    return CategoryResponseDto.fromEntity(createdCategory);
  }

  // READ ALL
  public List<CategoryResponseDto> getAllCategories() {
    List<Category> categories = categoryRepository.findAll();
    return categories.stream()
        .map(CategoryResponseDto::fromEntity)
        .collect(Collectors.toList());
  }

  // READ
  public CategoryResponseDto getCategory(Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
    return CategoryResponseDto.fromEntity(category);
  }

  // UPDATE
  public CategoryResponseDto updateCategory(Long categoryId,
      CategoryUpdateRequestDto categoryUpdateRequestDto) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
    category.setName(categoryUpdateRequestDto.getName());
    Category updatedCategory = categoryRepository.save(category);
    return CategoryResponseDto.fromEntity(updatedCategory);
  }

  // DELETE
  public void deleteCategory(Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
    categoryRepository.delete(category);
  }
}
