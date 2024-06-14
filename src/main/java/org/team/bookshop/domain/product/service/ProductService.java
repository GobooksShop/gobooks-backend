package org.team.bookshop.domain.product.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.team.bookshop.domain.category.entity.BookCategory;
import org.team.bookshop.domain.category.entity.BookCategoryId;
import org.team.bookshop.domain.category.entity.Category;
import org.team.bookshop.domain.category.repository.BookCategoryRepository;
import org.team.bookshop.domain.category.repository.CategoryRepository;
import org.team.bookshop.domain.product.dto.ProductCreateRequestDto;
import org.team.bookshop.domain.product.dto.ProductDto;
import org.team.bookshop.domain.product.dto.ProductResponseDto;
import org.team.bookshop.domain.product.dto.ProductUpdateRequestDto;
import org.team.bookshop.domain.product.dto.SimpleProductResponseDto;
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
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public SimpleProductResponseDto createProduct(ProductCreateRequestDto requestDto) {
    // 1. 상품 생성
    Product product = productRepository.save(requestDto.toEntity());

    // 2. 카테고리 ID 리스트에서 중복 제거
    Set<Long> uniqueCategoryIds = new HashSet<>(requestDto.getCategoryIds());

    // 3. 자식 카테고리 ID 추출 (입력값 중 부모-자식 관계인 id가 있을 경우 부모 id를 제외)
    Set<Long> childCategoryIds = filterChildCategoryIds(uniqueCategoryIds);

    // 4. BookCategory 매핑 (엔티티 매핑)
    List<Category> categories = categoryRepository.findAllById(childCategoryIds);
    for (Category category : categories) {
      BookCategory bookCategory = new BookCategory(
          new BookCategoryId(product.getId(), category.getId()),
          product,
          category
      );
      bookCategoryRepository.save(bookCategory); // BookCategory 엔티티 저장
      product.addBookCategory(bookCategory); // Product 엔티티에 추가
    }
    productRepository.save(product);

    return new SimpleProductResponseDto(product);
  }

  // 부모-자식 관계 필터링 메서드 (자식 카테고리 ID만 남김)
  private Set<Long> filterChildCategoryIds(Set<Long> categoryIds) {
    Set<Long> parentCategoryIds = new HashSet<>();
    for (Long categoryId : categoryIds) {
      List<Object[]> categoryPath = categoryRepository.findCategoryPath(categoryId);

      // 마지막 요소 제외하고 부모 카테고리 ID 추출
      for (int i = 0; i < categoryPath.size() - 1; i++) {
        Long parentId =
            (categoryPath.get(i)[2] != null) ? ((Number) categoryPath.get(i)[2]).longValue() : null;
        if (parentId != null) {
          parentCategoryIds.add(parentId);
        }
      }
    }

    // 자식 카테고리 ID 필터링
    return categoryIds.stream()
        .filter(categoryId -> !parentCategoryIds.contains(categoryId))
        .collect(Collectors.toSet());
  }

  // READ
  // 모든 상품 조회
  public List<ProductResponseDto> getAllProducts() {
    return productRepository.findAll().stream()
        .map(ProductResponseDto::new)
        .collect(Collectors.toList());
  }

  // 특정 상품 조회
  public ProductResponseDto getProduct(long id) {
    return productRepository.findById(id)
        .map(ProductResponseDto::new)
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
  public Product updateProduct(long id, ProductUpdateRequestDto request) {
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
  @Transactional
  public void deleteProduct(long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("not found: " + id));
    productRepository.delete(product);
  }
}
