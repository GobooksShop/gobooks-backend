package org.team.bookshop.domain.product.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.team.bookshop.domain.product.dto.AddProductRequest;
import org.team.bookshop.domain.product.entity.Product;
import org.team.bookshop.domain.product.repository.ProductRepository;

@RequiredArgsConstructor
@Service
public class ProductService {

  private final ProductRepository productRepository;

  public Product save(AddProductRequest request, String userName) {
//    return ProductRepository.save(request.toEntity(userName));
    return null;
  }

  public List<Product> findAll() {
    return ProductRepository.findAll();
  }

  public Product findById(long id) {
//    return ProductRepository.findById(id)
//        .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    return null;
  }

  public void delete(long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

    authorizeProductAuthor(product);
    productRepository.delete(product);
  }

  @Transactional
  public Product update(long id,
      org.team.bookshop.domain.product.request.UpdateProductRequest request) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

    authorizeProductAuthor(product);
//    product.update(request.getTitle(), request.getContent());

    return product;
  }

  private static void authorizeProductAuthor(Product product) {
    String userName = SecurityContextHolder.getContext().getAuthentication().getName();
//    if (!article.getAuthor().equals(userName)) {
//      throw new IllegalArgumentException("not authorized");
//    }
  }
}
