package org.team.bookshop.domain.product.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.bookshop.domain.category.entity.BookCategory;
import org.team.bookshop.domain.category.entity.BookCategoryId;
import org.team.bookshop.domain.category.entity.Category;
import org.team.bookshop.domain.category.repository.BookCategoryRepository;
import org.team.bookshop.domain.category.repository.CategoryRepository;
import org.team.bookshop.domain.product.dto.AddProductRequest;
import org.team.bookshop.domain.product.dto.ProductDto;
import org.team.bookshop.domain.product.dto.ProductResponse;
import org.team.bookshop.domain.product.dto.SimpleProductResponseDto;
import org.team.bookshop.domain.product.dto.UpdateProductRequest;
import org.team.bookshop.domain.product.entity.Product;
import org.team.bookshop.domain.product.repository.ProductRepository;
import org.team.bookshop.global.error.exception.EntityNotFoundException;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final BookCategoryRepository bookCategoryRepository;

  // CREATE
  public SimpleProductResponseDto createProduct(AddProductRequest requestDto) {

    // 1. 상품 생성
    Product product = productRepository.save(requestDto.toEntity());

    // 2. 카테고리 ID 리스트에서 중복 제거
    Set<Long> uniqueCategoryIds = new HashSet<>(requestDto.getCategoryIds());

    // 3. 각 카테고리 ID에 대해 부모 카테고리까지 매핑
    for (Long categoryId : uniqueCategoryIds) {
      mapParentCategoriesByPath(product, categoryId);
    }

    productRepository.save(product); // 변경된 Product 엔티티 다시 저장

    return new SimpleProductResponseDto(product);

  }

  private void mapParentCategoriesByPath(Product product, Long categoryId) {

    List<Object[]> categoryPath = categoryRepository.findCategoryPath(categoryId);

    for (Object[] categoryInfo : categoryPath) {
      Long id = ((Number) categoryInfo[0]).longValue();
      String name = (String) categoryInfo[1];
      Long parentId = (categoryInfo[2] != null) ? ((Number) categoryInfo[2]).longValue() : null;

      Category category = new Category();
      category.setId(id);
      category.setName(name);
//      if (parentId != null) {
//        category.setParent(new Category(parentId));
//      }

      BookCategory bookCategory = new BookCategory(
          new BookCategoryId(product.getId(), category.getId()),
          product,
          category
      );
      bookCategoryRepository.save(bookCategory); // BookCategory 엔티티 저장

      product.addBookCategory(bookCategory);
    }
  }

  // READ
  // 모든 상품 조회
  public List<ProductResponse> getAllProducts() {
    return productRepository.findAll().stream()
        .map(ProductResponse::new)
        .collect(Collectors.toList());
  }

  // 특정 상품 조회
  public ProductResponse getProduct(long id) {
    return productRepository.findById(id)
        .map(ProductResponse::new)
        .orElseThrow(() -> new EntityNotFoundException("not found: " + id));
  }

  // 카테고리별 상품 조회 paging, querydsl
  public Page<ProductDto> getProductsByCategoryId(Long categoryId, Pageable pageable) {
    return productRepository.findByCategoryIds(categoryId, pageable);
  }

  // 카테고리별 상품 조회
  public List<ProductDto> getProductsByCategoryId(Long categoryId) {
    List<Product> products = productRepository.findByCategoryIds(categoryId);
    return products.stream()
        .map(ProductDto::new)
        .collect(Collectors.toList());
  }

  // UPDATE
  @Transactional
  public Product updateProduct(long id, UpdateProductRequest request) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("not found: " + id));
    product.update(
        request.getTitle(),
        request.getAuthor(),
        request.getIsbn(),
        request.getContent(),
        request.getFixedPrice(),
        request.getPublicationYear(),
        request.getStatus()
    );
    return product;
  }

  // DELETE
  public void deleteProduct(long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("not found: " + id));
    productRepository.delete(product);
  }
}
