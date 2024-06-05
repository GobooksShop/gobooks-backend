package org.team.bookshop.domain.category.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
public class CategoryService {

  private final CategoryRepository categoryRepository;

  private final CategoryPathRepository categoryPathRepository;

  public CategoryService(CategoryRepository categoryRepository,
      CategoryPathRepository categoryPathRepository) {
    this.categoryRepository = categoryRepository;
    this.categoryPathRepository = categoryPathRepository;
  }

  // CREATE
  @Transactional
  public CategoryResponseDto createCategory(CategoryCreateRequestDto categoryCreateRequestDto) {
    Category category = categoryCreateRequestDto.toEntity();
    Category parentCategory = null;
    if (categoryCreateRequestDto.getParentId() != null) {
      parentCategory = categoryRepository.findById(categoryCreateRequestDto.getParentId())
          .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
      category.setParent(parentCategory);
    }
    Category createdCategory = categoryRepository.save(category);
    return CategoryResponseDto.fromEntity(createdCategory, new ArrayList<>(), parentCategory);
  }

  private void createPath(Category category, Category parentCategory) {
    List<CategoryPath> paths = new ArrayList<>();
    CategoryPath pathToParent = new CategoryPath(new CategoryPathId(parentCategory, category));
    paths.add(pathToParent);

    List<CategoryPath> parentPaths = categoryPathRepository.findByParent(parentCategory);
    for (CategoryPath parentPath : parentPaths) {
      CategoryPath path = new CategoryPath(new CategoryPathId(parentPath.getParent(), category));
      paths.add(path);
    }

    paths.add(new CategoryPath(new CategoryPathId(category, category)));  // Self-referential path

    categoryPathRepository.saveAll(paths);
  }

  // READ ALL
  public List<CategoryResponseDto> getAllCategories() {
    List<Category> categories = categoryRepository.findAll();

    return buildCategoryTree(categories, null);
  }


  private List<CategoryResponseDto> buildCategoryTree(List<Category> categories, Long parentId) {
    return categories.stream()
        .filter(category -> Objects.equals(categoryRepository.findParent(category.getId())
            .orElse(null), categoryRepository.findById(parentId).orElse(null)))
        .map(category -> CategoryResponseDto.fromEntity(category,
            buildCategoryTree(categories, category.getId()),
            categoryRepository.findParent(category.getId()).orElse(null)))
        .collect(Collectors.toList());
  }

  // READ
  public CategoryResponseDto getCategory(Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND));
    Optional<Category> parent = categoryRepository.findParent(categoryId);
    List<Category> descendants = categoryRepository.findChildren(categoryId);
    return CategoryResponseDto.fromEntity(category, buildCategoryTree(descendants, categoryId),
        parent.orElse(null));
  }

  private List<CategoryResponseDto> getChildren(Long categoryId) {
    List<Category> children = categoryRepository.findChildren(categoryId);
    return children.stream()
        .filter(child -> !child.getId().equals(categoryId))
        .map(child -> {
          List<CategoryResponseDto> grandChildren = getChildren(child.getId());
          Category parent = categoryRepository.findById(categoryId).orElse(null);
          return CategoryResponseDto.fromEntity(child, grandChildren, parent);
        })
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
}
