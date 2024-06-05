package org.team.bookshop.domain.product.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team.bookshop.domain.product.dto.AddProductRequest;
import org.team.bookshop.domain.product.dto.ProductResponse;
import org.team.bookshop.domain.product.dto.UpdateProductRequest;
import org.team.bookshop.domain.product.entity.Product;
import org.team.bookshop.domain.product.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;
import org.team.bookshop.global.error.exception.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class ProductService {
  private final ProductRepository productRepository;

  public Product save(AddProductRequest request) {
    return productRepository.save(request.toEntity());
  }

  public List<ProductResponse> findAll() {
    return productRepository.findAll().stream()
            .map(ProductResponse::new)
            .collect(Collectors.toList());
  }

  public ProductResponse findById(long id) {
    return productRepository.findById(id)
            .map(ProductResponse::new)
            .orElseThrow(() -> new EntityNotFoundException("not found: " + id));
  }

  public void delete(long id) {
    Product product = productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("not found: " + id));
    productRepository.delete(product);
  }

  @Transactional
  public Product update(long id, UpdateProductRequest request) {
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
}
