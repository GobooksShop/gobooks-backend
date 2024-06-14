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

  // CREATE
  public SimpleProductResponseDto createProduct(AddProductRequest requestDto) {
    // 1. 상품 생성
    Product product = productRepository.save(requestDto.toEntity());

    // 2. 카테고리 ID 리스트에서 중복 제거
    Set<Long> uniqueCategoryIds = new HashSet<>(requestDto.getCategoryIds());

    return new SimpleProductResponseDto(product);
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
