package org.team.bookshop.domain.category.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.team.bookshop.domain.category.dto.CategoryCreateRequestDto;
import org.team.bookshop.domain.category.dto.CategoryResponseDto;
import org.team.bookshop.domain.category.dto.CategoryUpdateRequestDto;
import org.team.bookshop.domain.category.entity.Category;
import org.team.bookshop.domain.category.entity.CategoryPath;
import org.team.bookshop.domain.category.entity.CategoryPathId;
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
    Category parentCategory = categoryRepository.findById(categoryCreateRequestDto.getParentId())
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
    Category createdCategory = categoryRepository.save(category);

    createPath(createdCategory, parentCategory);

    return CategoryResponseDto.fromEntity(createdCategory, new ArrayList<>());
  }

  private void createPath(Category category, Category parentCategory) {
    // insert path to the parent
    CategoryPath pathToParent = new CategoryPath(new CategoryPathId(parentCategory, category));
    categoryPathRepository.save(pathToParent);

    // insert paths to the ancestors of the parent
    List<CategoryPath> parentPaths = categoryPathRepository.findByDescendant(parentCategory);
    for (CategoryPath parentPath : parentPaths) {
      CategoryPath path = new CategoryPath(new CategoryPathId(parentPath.getAncestor(), category));
      categoryPathRepository.save(path);
    }

    // insert self-referential path
    CategoryPath selfPath = new CategoryPath(new CategoryPathId(category, category));
    categoryPathRepository.save(selfPath);
  }

  // READ ALL
  public List<CategoryResponseDto> getAllCategories() {
    List<Category> categories = categoryRepository.findAll();
    return categories.stream()
        .map(category -> {
          List<CategoryResponseDto> children = getChildren(category.getId());
          return CategoryResponseDto.fromEntity(category, children);
        })
        .collect(Collectors.toList());
  }

  // READ
  public CategoryResponseDto getCategory(Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
    List<CategoryResponseDto> children = getChildren(categoryId);
    return CategoryResponseDto.fromEntity(category, children);
  }

  private List<CategoryResponseDto> getChildren(Long categoryId) {
    List<Category> children = categoryRepository.findChildren(categoryId);
    return children.stream()
        .map(child -> {
          List<CategoryResponseDto> grandChildren = getChildren(child.getId());
          return CategoryResponseDto.fromEntity(child, grandChildren);
        })
        .collect(Collectors.toList());
  }

  // UPDATE
  public CategoryResponseDto updateCategory(Long categoryId,
      CategoryUpdateRequestDto categoryUpdateRequestDto) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
    category.setName(categoryUpdateRequestDto.getName());
    Category updatedCategory = categoryRepository.save(category);
    return CategoryResponseDto.fromEntity(updatedCategory, getChildren(categoryId));
  }

  // DELETE
  public void deleteCategory(Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
    deletePaths(category);
    categoryRepository.delete(category);
  }

  public void deletePaths(Category category) {
    List<CategoryPath> paths = categoryPathRepository.findByDescendant(category);
    categoryPathRepository.deleteAll(paths);
  }
}
