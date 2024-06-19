package org.team.bookshop.domain.product.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.team.bookshop.domain.product.entity.Product;

@Getter
@AllArgsConstructor
public class SimpleProductResponseDto {

  private Long id;
  private String title;
  private String author;
  private String isbn;
  private String content;
  private int fixedPrice;
  private LocalDate publicationYear;
  private Product.Status status;
  private String pictureUrl;
  private LocalDateTime createdAt;
  private boolean discount;
  private int stockQuantity;

  private List<Long> categoryIds;

  public SimpleProductResponseDto(Product product) {
    this.id = product.getId();
    this.title = product.getTitle();
    this.author = product.getAuthor();
    this.isbn = product.getIsbn();
    this.content = product.getContent();
    this.fixedPrice = product.getFixedPrice();
    this.publicationYear = product.getPublicationYear();
    this.status = product.getStatus();
    this.pictureUrl = product.getPictureUrl();
    this.createdAt = product.getCreatedAt();
    this.discount = product.isDiscount();
    this.stockQuantity = product.getStockQuantity();

    this.categoryIds = product.getBookCategories().stream()
        .map(bookCategory -> bookCategory.getCategory().getId())
        .collect(Collectors.toList());
  }

}
